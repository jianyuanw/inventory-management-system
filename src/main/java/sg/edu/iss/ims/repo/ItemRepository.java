package sg.edu.iss.ims.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import sg.edu.iss.ims.model.Item;
import sg.edu.iss.ims.model.Supplier;

import java.util.ArrayList;

public interface ItemRepository extends JpaRepository<Item, Long> {

    public ArrayList<Item> findAllByUnits();

  // must add here otherwise cannot be called by itemrepo object
    void delete(int unit);
    void save (int unit);
}
