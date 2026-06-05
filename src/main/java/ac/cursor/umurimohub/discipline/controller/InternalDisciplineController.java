package ac.cursor.umurimohub.discipline.controller;

import ac.cursor.umurimohub.discipline.model.DisciplinaryAction;
import ac.cursor.umurimohub.discipline.port.DisciplineQueryPort;
import ac.cursor.umurimohub.shared.dto.ApiResponse;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/internal/discipline")
@Profile("service-discipline")
public class InternalDisciplineController {

    private final DisciplineQueryPort disciplineQueryPort;

    public InternalDisciplineController(DisciplineQueryPort disciplineQueryPort) {
        this.disciplineQueryPort = disciplineQueryPort;
    }

    @GetMapping("/actions")
    public ApiResponse<List<DisciplinaryAction>> getAllActions() {
        return ApiResponse.success("Actions", disciplineQueryPort.getAllActions());
    }
}
