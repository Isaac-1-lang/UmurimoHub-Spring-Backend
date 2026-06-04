package ac.cursor.umurimohub.discipline.repository;

import ac.cursor.umurimohub.discipline.model.DisciplinaryAction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DisciplinaryActionRepository extends JpaRepository<DisciplinaryAction, Long> {
    List<DisciplinaryAction> findByWorkerIdOrderByActionDateDesc(Long workerId);
}
