package sg.edu.iss.ims.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sg.edu.iss.ims.model.Reorder;
import sg.edu.iss.ims.model.Restock;
import sg.edu.iss.ims.repo.ReorderRepository;
import sg.edu.iss.ims.repo.RestockRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class RestockServiceImpl implements RestockService {

    private final RestockRepository sRepo;


    public RestockServiceImpl (RestockRepository sRepo) {
        this.sRepo = sRepo;
    }

    @Override
    @Transactional
    public void create(Restock restock) {
        sRepo.save(restock);
    }

    @Override
    public List<Restock> findAllRestock() {
        return sRepo.findAll();
    }

    @Override
    public LocalDate parseDate(String date) {
        return LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
}
