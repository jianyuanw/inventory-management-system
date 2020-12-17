package sg.edu.iss.ims.restock;

import java.time.LocalDate;
import java.util.List;

public interface RestockService {

    void create(Restock restock);
    List<Restock> findAllRestock();

    public LocalDate parseDate(String date);

}
