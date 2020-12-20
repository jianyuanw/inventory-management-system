package sg.edu.iss.ims.repo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import sg.edu.iss.ims.supplier.Supplier;
import sg.edu.iss.ims.supplier.SupplierRepository;

@SpringBootTest
public class SupplierRepositoryTest {
	
	@Autowired
	SupplierRepository srepo;
	
	@Test
	void findSupplierByName() {
		String[] suppliers = {"Volkswagen Group Singapore", "Borneo Motors", "Wearnes Automotive", "Tan Chong Motor Sales", "Cycle and Carriage Mitsubishi"};
		for(String s: suppliers) {
			Supplier sup = srepo.findSupplierByName(s);
			System.out.println(sup);
		}
	}

}
