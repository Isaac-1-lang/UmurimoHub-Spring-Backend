package ac.cursor.umurimohub.discipline.port;

import ac.cursor.umurimohub.discipline.model.DisciplinaryAction;

import java.util.List;

public interface DisciplineQueryPort {
    List<DisciplinaryAction> getAllActions();
}
