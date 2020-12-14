package sg.edu.iss.ims.repo;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;

import sg.edu.iss.ims.model.Supplier;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {
	public ArrayList<Supplier> findByName(String name);

}
