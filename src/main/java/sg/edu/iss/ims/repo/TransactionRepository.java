package sg.edu.iss.ims.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import sg.edu.iss.ims.model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

}
