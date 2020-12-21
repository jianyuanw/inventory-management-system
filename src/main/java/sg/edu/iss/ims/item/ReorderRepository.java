package sg.edu.iss.ims.item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ReorderRepository extends JpaRepository<Reorder, Long> {

	@Query("SELECT r FROM Reorder r WHERE r.status = :status")
	List<Reorder> findReordersWhereStatusIs(@Param("status") ReorderStatus status);

	List<Reorder> findAllByItemAndOrderDateBetween(Item item, LocalDate fromDate, LocalDate toDate);

	List<Reorder> findReordersByItem_Id(Long itemId);

	List<Reorder> findReordersByItem_IdAndStatusEquals(Long itemId, ReorderStatus status);

	List<Reorder> findReordersByItem_IdAndStatus(Long itemId, ReorderStatus status);

	List<Reorder> findAllByStatus(ReorderStatus status);
}
