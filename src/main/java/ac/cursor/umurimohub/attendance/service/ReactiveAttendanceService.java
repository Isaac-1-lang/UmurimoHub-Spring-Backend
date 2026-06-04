package ac.cursor.umurimohub.attendance.service;

import ac.cursor.umurimohub.attendance.dto.AttendanceLiveEvent;
import ac.cursor.umurimohub.attendance.model.Attendance;
import ac.cursor.umurimohub.attendance.repository.AttendanceRepository;
import ac.cursor.umurimohub.worker.port.WorkerLookupPort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

@Service
public class ReactiveAttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final WorkerLookupPort workerLookupPort;

    public ReactiveAttendanceService(AttendanceRepository attendanceRepository,
                                     WorkerLookupPort workerLookupPort) {
        this.attendanceRepository = attendanceRepository;
        this.workerLookupPort = workerLookupPort;
    }

    public Mono<AttendanceLiveEvent> fetchTodayStatsOnce() {
        return Mono.fromCallable(this::buildTodayEvent).subscribeOn(Schedulers.boundedElastic());
    }

    public Flux<AttendanceLiveEvent> streamTodayStats(Duration pollInterval) {
        return Flux.interval(pollInterval)
                .onBackpressureLatest()
                .flatMap(tick -> Mono.fromCallable(this::buildTodayEvent)
                        .subscribeOn(Schedulers.boundedElastic()))
                .distinctUntilChanged(event ->
                        event.presentToday() + event.absentToday() + event.lateToday() + event.onLeaveToday())
                .filter(event -> event.presentToday() > 0);
    }

    private AttendanceLiveEvent buildTodayEvent() {
        LocalDate today = LocalDate.now();
        List<Attendance> records = attendanceRepository.findAll();
        long present = countByStatus(records, today, Attendance.AttendanceStatus.PRESENT);
        long absent = countByStatus(records, today, Attendance.AttendanceStatus.ABSENT);
        long late = countByStatus(records, today, Attendance.AttendanceStatus.LATE);
        long onLeave = records.stream()
                .filter(a -> today.equals(a.getDate())
                        && (a.getStatus() == Attendance.AttendanceStatus.LEAVE
                        || a.getStatus() == Attendance.AttendanceStatus.HALF_DAY))
                .count();
        long totalWorkers = workerLookupPort.countWorkers();
        return new AttendanceLiveEvent(
                present, absent, late, onLeave, totalWorkers,
                Instant.now().toString(), Thread.currentThread().getName());
    }

    private static long countByStatus(List<Attendance> records, LocalDate today, Attendance.AttendanceStatus status) {
        return records.stream()
                .filter(a -> today.equals(a.getDate()) && a.getStatus() == status)
                .count();
    }
}
