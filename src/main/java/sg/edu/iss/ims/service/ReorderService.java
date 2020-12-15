package sg.edu.iss.ims.service;

import sg.edu.iss.ims.model.Reorder;

import java.util.List;

public interface ReorderService {

    void save(Reorder reorder);
    List<Reorder> findAllReorders();
}
