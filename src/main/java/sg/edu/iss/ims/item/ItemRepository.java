package sg.edu.iss.ims.item;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;

import sg.edu.iss.ims.product.Product;

public interface ItemRepository extends JpaRepository<Item, Long> {

    public ArrayList<Item> findAllByUnits(int units);

	public Item findItemById(Long id);

	public Item findItemByProduct(Product product);

}
