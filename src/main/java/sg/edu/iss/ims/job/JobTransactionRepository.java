package sg.edu.iss.ims.job;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JobTransactionRepository extends JpaRepository<JobTransaction, Long> {

	JobTransaction findByTransaction_Id(Long id);

}
