package ac.cursor.umurimohub.discipline.port.internal;

import ac.cursor.umurimohub.discipline.model.DisciplinaryAction;
import ac.cursor.umurimohub.discipline.port.DisciplineQueryPort;
import ac.cursor.umurimohub.discipline.service.DisciplinaryActionService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Profile("service-discipline")
public class DisciplineServiceQueryAdapter implements DisciplineQueryPort {

    private final DisciplinaryActionService disciplinaryActionService;

    public DisciplineServiceQueryAdapter(DisciplinaryActionService disciplinaryActionService) {
        this.disciplinaryActionService = disciplinaryActionService;
    }

    @Override
    public List<DisciplinaryAction> getAllActions() {
        return disciplinaryActionService.getAllActions();
    }
}
