package sg.edu.iss.ims.transaction;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
	public List<Transaction> findAllByItem_Id(Long itemId);
	public List<Transaction> findAllByItem_IdAndTransactionTimeGreaterThanEqualAndTransactionTimeLessThanEqual(Long itemId, LocalDateTime dateStart, LocalDateTime dateEnd);
	public List<Transaction> findAllByItem_IdAndTransactionTimeGreaterThanEqual(Long itemId, LocalDateTime dateStart);
	public List<Transaction> findAllByItem_IdAndTransactionTimeLessThanEqual(Long itemId, LocalDateTime dateEnd);
}
