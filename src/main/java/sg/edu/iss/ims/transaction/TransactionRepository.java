package sg.edu.iss.ims.transaction;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
	public List<Transaction> findByItem_Id(Long itemId);
}
