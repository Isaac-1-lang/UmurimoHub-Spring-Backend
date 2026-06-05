package ac.cursor.umurimohub.cloud.remote;

import ac.cursor.umurimohub.attendance.model.Attendance;
import ac.cursor.umurimohub.attendance.port.AttendanceQueryPort;
import ac.cursor.umurimohub.cloud.ServiceDiscoveryProperties;
import ac.cursor.umurimohub.shared.dto.ApiResponse;
import org.springframework.context.annotation.Profile;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Component
@Profile("distributed & !service-attendance")
public class RemoteAttendanceQueryClient implements AttendanceQueryPort {

    private final WebClient.Builder webClient;
    private final String attendanceService;

    public RemoteAttendanceQueryClient(WebClient.Builder webClient, ServiceDiscoveryProperties discovery) {
        this.webClient = webClient;
        this.attendanceService = discovery.getAttendanceService();
    }

    @Override
    public List<Attendance> getAllAttendance() {
        return RemoteServiceClientSupport.getData(
                webClient,
                attendanceService,
                "/internal/attendance",
                new ParameterizedTypeReference<ApiResponse<List<Attendance>>>() {});
    }
}
