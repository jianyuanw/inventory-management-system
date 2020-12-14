package sg.edu.iss.ims.repo;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;

import sg.edu.iss.ims.model.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {

    public ArrayList<Item> findAllByUnits(int units);

}
