package sg.edu.iss.ims.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import sg.edu.iss.ims.model.JobTransaction;

public interface JobTransactionRepository extends JpaRepository<JobTransaction, Long> {

}
