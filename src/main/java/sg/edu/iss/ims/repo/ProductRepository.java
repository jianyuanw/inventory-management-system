package sg.edu.iss.ims.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import sg.edu.iss.ims.model.Product;
import sg.edu.iss.ims.model.Supplier;

import java.util.ArrayList;

public interface ProductRepository  extends JpaRepository<Product, Long> {

    //for view product Test
    public ArrayList<Product> findByName(String name);
    
    public Product findProductById(Long id);
}