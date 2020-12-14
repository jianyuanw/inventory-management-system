package sg.edu.iss.ims.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import sg.edu.iss.ims.model.Brand;

public interface BrandRepository extends JpaRepository<Brand, Long> {
	public Brand findBrandByName(String brand);
}
