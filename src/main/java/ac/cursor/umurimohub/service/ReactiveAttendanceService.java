package ac.cursor.umurimohub.service;

import ac.cursor.umurimohub.dto.AttendanceLiveEvent;
import ac.cursor.umurimohub.model.Attendance;
import ac.cursor.umurimohub.repository.AttendanceRepository;
import ac.cursor.umurimohub.repository.WorkerRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

/**
 * Reactive layer on top of blocking JPA.
 * <p>
 * Repository calls stay blocking; we wrap them with
 * {@code subscribeOn(Schedulers.boundedElastic())} so the reactive pipeline
 * does not block the event-loop thread (when using WebFlux) or ties cleanly
 * into Servlet async (when returning Mono/Flux from MVC).
 */
@Service
public class ReactiveAttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final WorkerRepository workerRepository;

    public ReactiveAttendanceService(AttendanceRepository attendanceRepository,
                                     WorkerRepository workerRepository) {
        this.attendanceRepository = attendanceRepository;
        this.workerRepository = workerRepository;
    }

    /**
     * Learning endpoint: single async value (0..1 items).
     */
    public Mono<AttendanceLiveEvent> fetchTodayStatsOnce() {
        return Mono.fromCallable(() -> {
            LocalDate today = LocalDate.now();
            List<Attendance> records = attendanceRepository.findAll();
            long present = records.stream()
                .filter(a -> today.equals(a.getDate()) && a.getStatus() == Attendance.AttendanceStatus.PRESENT)
                .count();
            long absent = records.stream()
                .filter(a -> today.equals(a.getDate()) && a.getStatus() == Attendance.AttendanceStatus.ABSENT)
                .count();
            long late = records.stream()
                .filter(a -> today.equals(a.getDate()) && a.getStatus() == Attendance.AttendanceStatus.LATE)
                .count();
            long onLeave = records.stream()
                .filter(a -> today.equals(a.getDate()) && (a.getStatus() == Attendance.AttendanceStatus.LEAVE || a.getStatus() == Attendance.AttendanceStatus.HALF_DAY))
                .count();
            long totalWorkers = workerRepository.count();
            return new AttendanceLiveEvent(present, absent, late, onLeave, totalWorkers, Instant.now().toString(), Thread.currentThread().getName());
        }).subscribeOn(Schedulers.boundedElastic());
    }

    /**
     * Polls DB on an interval and emits only when stats change (live dashboard feed).
     */
    public Flux<AttendanceLiveEvent> streamTodayStats(Duration pollInterval) {
        return Flux.interval(pollInterval)
                .onBackpressureLatest()
                .flatMap(tick -> Mono.fromCallable(() -> {
                    LocalDate today = LocalDate.now();
                    List<Attendance> records = attendanceRepository.findAll();
                    long present = records.stream()
                        .filter(a -> today.equals(a.getDate()) && a.getStatus() == Attendance.AttendanceStatus.PRESENT)
                        .count();
                    long absent = records.stream()
                        .filter(a -> today.equals(a.getDate()) && a.getStatus() == Attendance.AttendanceStatus.ABSENT)
                        .count();
                    long late = records.stream()
                        .filter(a -> today.equals(a.getDate()) && a.getStatus() == Attendance.AttendanceStatus.LATE)
                        .count();
                    long onLeave = records.stream()
                        .filter(a -> today.equals(a.getDate()) && (a.getStatus() == Attendance.AttendanceStatus.LEAVE || a.getStatus() == Attendance.AttendanceStatus.HALF_DAY))
                        .count();
                    long totalWorkers = workerRepository.count();
                    return new AttendanceLiveEvent(present, absent, late, onLeave, totalWorkers, Instant.now().toString(), Thread.currentThread().getName());
                }).subscribeOn(Schedulers.boundedElastic()))
                .distinctUntilChanged(event -> event.presentToday() + event.absentToday() + event.lateToday() + event.onLeaveToday()).filter(a -> a.presentToday() > 0);
    }
}
