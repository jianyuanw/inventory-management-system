package sg.edu.iss.ims.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import sg.edu.iss.ims.model.Reorder;
import sg.edu.iss.ims.model.ReorderStatus;

import java.util.List;

public interface ReorderRepository extends JpaRepository<Reorder, Long> {

//    List<Reorder> findReordersWhereStatusIs(ReorderStatus status);
}
