package sg.edu.iss.ims.supplier;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {
	public Supplier findSupplierByName(String name);

}
