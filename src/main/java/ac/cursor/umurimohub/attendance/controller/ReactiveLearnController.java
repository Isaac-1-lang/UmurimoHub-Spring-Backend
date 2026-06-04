package ac.cursor.umurimohub.attendance.controller;

import ac.cursor.umurimohub.shared.dto.ApiResponse;
import ac.cursor.umurimohub.attendance.dto.AttendanceLiveEvent;
import ac.cursor.umurimohub.attendance.service.ReactiveAttendanceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Reactive learning endpoints — start here when studying Project Reactor.
 * <p>
 * Layer guide:
 * <ul>
 *   <li><b>Controller</b> — returns {@link Mono} or {@link Flux} (this class)</li>
 *   <li><b>Service</b> — builds the reactive pipeline ({@link ReactiveAttendanceService})</li>
 *   <li><b>Repository</b> — still blocking JPA; wrapped with boundedElastic scheduler</li>
 * </ul>
 */
@RestController
@RequestMapping("/api/reactive")
@Tag(name = "Reactive (learning)", description = "Mono/Flux demos for reactive programming study")
@SecurityRequirement(name = "bearerAuth")
public class ReactiveLearnController {

    private final ReactiveAttendanceService reactiveAttendanceService;

    public ReactiveLearnController(ReactiveAttendanceService reactiveAttendanceService) {
        this.reactiveAttendanceService = reactiveAttendanceService;
    }

    /**
     * Step 1 — {@link Mono}: one value after a short delay.
     * Compare thread name in response with servlet thread from a normal GET.
     */
    @GetMapping("/ping")
    @PreAuthorize("hasAnyAuthority('HR', 'CEO', 'WORKER')")
    @Operation(summary = "Reactive ping (Mono)", description = "Returns a single JSON payload via Mono")
    public Mono<ApiResponse<Map<String, Object>>> ping() {
        return Mono.delay(Duration.ofMillis(150))
                .map(t -> {
                    Map<String, Object> body = new LinkedHashMap<>();
                    body.put("message", "pong from Project Reactor");
                    body.put("reactiveType", "Mono");
                    body.put("thread", Thread.currentThread().getName());
                    return ApiResponse.success("Reactive Mono endpoint", body);
                });
    }

    /**
     * Step 2 — {@link Mono} + blocking DB wrapped safely.
     */
    @GetMapping("/attendance/today")
    @PreAuthorize("hasAnyAuthority('HR', 'CEO')")
    @Operation(summary = "Today's stats once (Mono)", description = "One DB read exposed as Mono")
    public Mono<ApiResponse<AttendanceLiveEvent>> todayStatsOnce() {
        return reactiveAttendanceService.fetchTodayStatsOnce()
                .map(stats -> ApiResponse.success("Today's attendance (reactive Mono)", stats));
    }

    /**
     * Step 3 — {@link Flux} as Server-Sent Events (SSE): multiple values over one HTTP connection.
     * Open with curl -N or the HR Attendance "Live feed" panel in the frontend.
     */
    @GetMapping(value = "/attendance/live", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @PreAuthorize("hasAnyAuthority('HR', 'CEO')")
    @Operation(summary = "Live attendance stream (Flux SSE)", description = "Pushes stats every 3s when data changes")
    public Flux<ServerSentEvent<AttendanceLiveEvent>> liveAttendanceStream() {
        return reactiveAttendanceService.streamTodayStats(Duration.ofSeconds(3))
                .map(event -> ServerSentEvent.<AttendanceLiveEvent>builder()
                        .event("attendance-stats")
                        .data(event)
                        .build());
    }

    /**
     * Step 4 — pure Flux (no database): numbers 1..5 every second.
     */
    @GetMapping(value = "/demo/countdown", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @PreAuthorize("hasAnyAuthority('HR', 'CEO')")
    @Operation(summary = "Countdown demo (Flux)", description = "Pure reactive stream without JPA")
    public Flux<ServerSentEvent<Long>> demoCountdown() {
        return Flux.<Long>create(sink -> {
            for (int i = 5; i >= 0; i--) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
        
                sink.complete();
            }
        }).delayElements(Duration.ofSeconds(1))
                .doOnNext(n -> System.out.println("[Reactive] demo tick " + n
                        + " on " + Thread.currentThread().getName()))
                .map(n -> ServerSentEvent.<Long>builder()
                        .event("tick")
                        .data(n)
                        .build())
                .subscribeOn(Schedulers.boundedElastic());
    }
}
