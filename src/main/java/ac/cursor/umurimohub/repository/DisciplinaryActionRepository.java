package ac.cursor.umurimohub.repository;

import ac.cursor.umurimohub.model.DisciplinaryAction;
import ac.cursor.umurimohub.model.Worker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DisciplinaryActionRepository extends JpaRepository<DisciplinaryAction, Long> {
    List<DisciplinaryAction> findByWorkerOrderByActionDateDesc(Worker worker);
}
