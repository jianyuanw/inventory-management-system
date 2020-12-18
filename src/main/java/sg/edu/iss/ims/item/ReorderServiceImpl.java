package sg.edu.iss.ims.item;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sg.edu.iss.ims.product.Product;
import sg.edu.iss.ims.product.ProductRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReorderServiceImpl implements ReorderService {

    private final ReorderRepository rRepo;
    private final ProductRepository pRepo;
    private final ItemRepository iRepo;

    public ReorderServiceImpl(ReorderRepository rRepo, ProductRepository pRepo, ItemRepository iRepo) {
        this.rRepo = rRepo;
        this.pRepo = pRepo;
        this.iRepo = iRepo;
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

    @Override
    public List<Reorder> findReordersByDateRange(Long productId, LocalDate fromDate, LocalDate toDate) {
        Product product = pRepo.findById(productId).get();
        Item item = iRepo.findItemByProduct(product);
        List<Reorder> reorders = rRepo.findAllByItemAndDateBetween(item, fromDate, toDate);
        return reorders;
    }

    @Override
    public LocalDate convertToDate(String date) {
        return LocalDate.parse(date);
    }
}
