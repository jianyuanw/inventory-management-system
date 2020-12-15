package sg.edu.iss.ims.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import sg.edu.iss.ims.model.Reorder;

public interface ReorderRepository extends JpaRepository<Reorder, Long> {
}
