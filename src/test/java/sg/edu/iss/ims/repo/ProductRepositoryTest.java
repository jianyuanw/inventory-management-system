package sg.edu.iss.ims.repo;

import java.util.ArrayList;
import java.util.Iterator;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import sg.edu.iss.ims.product.Product;
import sg.edu.iss.ims.product.ProductRepository;

@SpringBootTest
class ProductRepositoryTest {

    @Autowired
    public ProductRepository prorepo;

    @Test
    void findByName() {
        ArrayList<Product> plist;
        plist = (ArrayList<Product>) prorepo.findByName("Avanza");
        System.out.println(plist.size());
        for (Iterator<Product> iterator = plist.iterator(); iterator.hasNext(); ) {
            Product product = iterator.next();
            System.out.println(product.toString());

        }
    }
}