package sg.edu.iss.ims.supplier;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {
	public Supplier findSupplierByName(String name);

}
