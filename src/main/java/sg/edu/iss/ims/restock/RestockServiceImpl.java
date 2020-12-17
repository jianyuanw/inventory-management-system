package sg.edu.iss.ims.restock;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
