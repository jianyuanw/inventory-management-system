package sg.edu.iss.ims.brand;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends JpaRepository<Brand, Long> {
	public Brand findByName(String brandName);
}
