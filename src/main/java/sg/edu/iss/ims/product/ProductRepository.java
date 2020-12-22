package sg.edu.iss.ims.product;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository  extends JpaRepository<Product, Long> {

    //for view product Test
    public ArrayList<Product> findByName(String name);

    public Product findProductById(Long id);

    public List<Product> findProductsBySupplierId(Long supplierId);
}