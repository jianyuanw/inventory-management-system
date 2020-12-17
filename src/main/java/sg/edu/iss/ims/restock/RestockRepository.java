package sg.edu.iss.ims.restock;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RestockRepository extends JpaRepository<Restock,Long> {

    public ArrayList<Restock> findAllById (Long id);

}
