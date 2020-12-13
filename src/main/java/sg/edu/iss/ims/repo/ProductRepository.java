package sg.edu.iss.ims.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import sg.edu.iss.ims.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
