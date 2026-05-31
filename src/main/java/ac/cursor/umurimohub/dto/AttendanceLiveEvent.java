package ac.cursor.umurimohub.dto;

/**
 * Payload pushed over SSE from the reactive attendance stream.
 * Kept small (no JPA entities) to avoid lazy-loading issues during JSON encoding.
 */
public record AttendanceLiveEvent(
        long presentToday,
        long absentToday,
        long lateToday,
        long onLeaveToday,
        long totalWorkers,
        String recordedAt,
        String pipelineThread
) {
}
