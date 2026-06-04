package ac.cursor.umurimohub.discipline.service;

import ac.cursor.umurimohub.discipline.model.DisciplinaryAction;
import ac.cursor.umurimohub.worker.model.Worker;
import ac.cursor.umurimohub.discipline.repository.DisciplinaryActionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DisciplinaryActionService {

    private final DisciplinaryActionRepository actionRepository;

    public DisciplinaryActionService(DisciplinaryActionRepository actionRepository) {
        this.actionRepository = actionRepository;
    }

    @Transactional
    public DisciplinaryAction createAction(DisciplinaryAction action) {
        return actionRepository.save(action);
    }

    public List<DisciplinaryAction> getWorkerActions(Worker worker) {
        return actionRepository.findByWorkerOrderByActionDateDesc(worker);
    }

    public List<DisciplinaryAction> getAllActions() {
        return actionRepository.findAll();
    }
}
