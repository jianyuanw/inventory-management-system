package sg.edu.iss.ims.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import sg.edu.iss.ims.model.Item;
import sg.edu.iss.ims.model.Restock;

import java.util.ArrayList;

public interface RestockRepository extends JpaRepository<Restock,Long> {

    public ArrayList<Restock> findAllById (Long id);

}
