package sg.edu.iss.ims.repo;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import sg.edu.iss.ims.item.ItemRepository;
import sg.edu.iss.ims.item.Reorder;
import sg.edu.iss.ims.item.ReorderRepository;

@SpringBootTest
public class ReorderRepositoryTest {
	
	@Autowired
	ReorderRepository repo;
	
	@Autowired
	ItemRepository irepo;
	
	@Test
	void findReordersWhereStatusIs() {
		List<Reorder> rs = repo.findReordersWhereStatusIs(null);
		rs.stream().forEach(System.out::println);
	}

}
