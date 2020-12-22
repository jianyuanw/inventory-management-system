package sg.edu.iss.ims.repo;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import sg.edu.iss.ims.item.Item;
import sg.edu.iss.ims.item.ItemRepository;

@SpringBootTest
public class ItemRepositoryTest {
	
	@Autowired
	ItemRepository irepo;
	
	@Test
	void findAllByUnits( ) {
		List<Item> i = irepo.findAll();
		i.stream().forEach(System.out::println);
	}
}
