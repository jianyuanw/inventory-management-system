package sg.edu.iss.ims.item;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReorderRepository extends JpaRepository<Reorder, Long> {

//    List<Reorder> findReordersWhereStatusIs(ReorderStatus status);
}
