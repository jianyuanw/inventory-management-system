package sg.edu.iss.ims.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sg.edu.iss.ims.model.Reorder;
import sg.edu.iss.ims.repo.ReorderRepository;

import java.util.List;

@Service
public class ReorderServiceImpl implements ReorderService {

    private final ReorderRepository rRepo;

    public ReorderServiceImpl(ReorderRepository rRepo) {
        this.rRepo = rRepo;
    }

    @Override
    @Transactional
    public void save(Reorder reorder) {
        rRepo.save(reorder);
    }

    @Override
    public List<Reorder> findAllReorders() {
        return rRepo.findAll();
    }
}
