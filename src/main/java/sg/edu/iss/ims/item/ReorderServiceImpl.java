package sg.edu.iss.ims.item;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReorderServiceImpl implements ReorderService {

    private final ReorderRepository rRepo;

    public ReorderServiceImpl(ReorderRepository rRepo) {
        this.rRepo = rRepo;
    }

    @Override
    @Transactional
    public void create(Reorder reorder) {
        rRepo.save(reorder);
    }

    @Override
    public List<Reorder> findAllReorders() {
        return rRepo.findAll();
    }

	@Override
	public Reorder findReorderById(Long reorderId) {
		return rRepo.findById(reorderId).get();
	}

//    @Override
//    public List<Reorder> findUndeliveredReorders() {
//        return rRepo.findReordersWhereStatusIs(ReorderStatus.PENDING_DELIVERY);
//    }
//
//    @Override
//    public List<Reorder> findDeliveredOrders() {
//        return rRepo.findReordersWhereStatusIs(ReorderStatus.DELIVERED);
//    }
}
