package sg.edu.iss.ims.service;

import sg.edu.iss.ims.model.Reorder;
import sg.edu.iss.ims.model.Restock;

import java.time.LocalDate;
import java.util.List;

public interface RestockService {

    void create(Restock restock);
    List<Restock> findAllRestock();

    public LocalDate parseDate(String date);

}
