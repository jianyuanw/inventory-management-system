package sg.edu.iss.ims.item;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sg.edu.iss.ims.product.Product;
import sg.edu.iss.ims.product.ProductRepository;
import sg.edu.iss.ims.supplier.Supplier;
import sg.edu.iss.ims.transaction.Transaction;
import sg.edu.iss.ims.transaction.TransactionRepository;
import sg.edu.iss.ims.transaction.TransactionType;

@Service
public class ReorderServiceImpl implements ReorderService {

    private final ReorderRepository rRepo;
    private final ProductRepository pRepo;
    private final ItemRepository iRepo;
    private final TransactionRepository tRepo;

    public ReorderServiceImpl(ReorderRepository rRepo, ProductRepository pRepo, ItemRepository iRepo, TransactionRepository tRepo) {
        this.rRepo = rRepo;
        this.pRepo = pRepo;
        this.iRepo = iRepo;
        this.tRepo = tRepo;
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

    @Override
    public List<Reorder> findDeliveredOrders() {
        return rRepo.findReordersWhereStatusIs(ReorderStatus.DELIVERED);
    }

    @Override
    public List<Reorder> findReordersByDateRange(Long supplierId, LocalDate fromDate, LocalDate toDate) {
        List<Product> products = pRepo.findProductsBySupplierId(supplierId);

        List<Item> items = new ArrayList<>();
        for (Product product : products) {
            Item item = iRepo.findItemByProduct(product);
            items.add(item);
        }

        List<Reorder> reorders = new ArrayList<>();
        for (Item item : items) {
            List<Reorder> reordersByItem = rRepo.findAllByItemAndOrderDateBetween(item, fromDate, toDate);
            reorders.addAll(reordersByItem);
        }

        return reorders;
    }

    @Override
    public LocalDate convertToDate(String date) {
        return LocalDate.parse(date);
    }

    @Override
    public double sumPrice(List<Reorder> reorders) {
        double sum = 0;
        for (Reorder reorder : reorders) {
            sum += reorder.getItem().getProduct().getOriginalPrice() * reorder.getQuantity();
        }
        return sum;
    }

    @Override
    public void updateItemQty(Item item, int reorderQty) {
        item.setUnits(item.getUnits() + reorderQty);
    }

    @Override
    public void updateItemState(Item item) {
        if (item.getUnits() >= item.getReorderAt()) {
            item.setState(ItemState.IN_STOCK);
        }
    }

    @Override
    public String generateReport(List<Reorder> reorders, Supplier supplier, double totalPrice) {
        String strTotalPrice = String.format("%.2f", totalPrice);

        String line1 = "\t\t     Inventory Reorder Report for products from Supplier " + supplier.getId() + "\n";
        String line2 = "\t\t     ------------------------------------------------------\n";
        String line3 = "================================================================================================\n";
        String line4 = "Part No.\tUnit.Price\tQty\tReorder Qty.\tMin.Ord.Qty\tOrd.Qty\t\tPrice\n";
        String line5 = "================================================================================================\n";

        String line6 = "================================================================================================\n";
        String line7 = "\t\t\t\t\t\t\t\t\t\tTOTAL\t" + strTotalPrice + "\n";
        String line8 = "================================================================================================\n";

        StringBuilder sb = new StringBuilder();

        sb.append(line1);
        sb.append(line2);
        sb.append(line3);
        sb.append(line4);
        sb.append(line5);

        for (Reorder reorder : reorders) {
            String partNo = reorder.getItem().getProduct().getPartNumber();
            double unitPrice = reorder.getItem().getProduct().getOriginalPrice();
            String strUnitPrice = String.format("%.2f", unitPrice);
            String qty = String.valueOf(reorder.getItem().getUnits());
            String reorderQty = String.valueOf(reorder.getItem().getReorderAt());
            String minOrdQty = String.valueOf(reorder.getItem().getReorderQuantity());
            int ordQty = reorder.getQuantity();
            String strOrdQty = String.valueOf(ordQty);
            String price = String.format("%.2f", unitPrice * ordQty);

            sb.append(partNo + "\t" + strUnitPrice + "\t\t" + qty + "\t" + reorderQty + "\t\t" +
                    minOrdQty + "\t\t" + strOrdQty + "\t\t" + price + "\n");
        }

        sb.append(line6);
        sb.append(line7);
        sb.append(line8);

        return sb.toString();
    }

	@Override
	public void receiveDelivery(Long reorderId, LocalDate receivedDate) {
		Reorder reorder = this.findReorderById(reorderId);
		reorder.setReceivedDate(receivedDate);
		reorder.setStatus(ReorderStatus.DELIVERED);
		Item item = reorder.getItem();
		item.setUnits(item.getUnits() + reorder.getQuantity());
		if (item.getUnits() >= item.getReorderAt()) {
			item.setState(ItemState.IN_STOCK);
		} else {
			item.setState(ItemState.BELOW_REORDER_LEVEL);
		}
		Transaction transaction = new Transaction(item, reorder.getQuantity(), TransactionType.RECEIVE_STOCK);
		tRepo.save(transaction);
		reorder.setTransaction(transaction);
		
	}

	@Override
	public List<Reorder> findAllReordersByItemIdAndStatus(Long itemId, ReorderStatus status) {
		return rRepo.findReordersByItem_IdAndStatus(itemId, status);
	}

	@Override
	public List<Reorder> findAllReordersByItemId(Long itemId) {
		return rRepo.findReordersByItem_Id(itemId);
	}

	@Override
	public void createDelivery(Reorder reorder) {
		reorder.setOrderDate(LocalDate.now());
		reorder.setStatus(ReorderStatus.PENDING_DELIVERY);
		
		rRepo.save(reorder);
		
		Item item = reorder.getItem();
		item.setState(ItemState.REORDER_PLACED);
		iRepo.save(item);
	}

	@Override
	public void cancelReorder(Long reorderId) {
		Reorder reorder = this.findReorderById(reorderId);
		
		if (reorder.getItem().getUnits() < reorder.getItem().getReorderAt()) {
			reorder.getItem().setState(ItemState.BELOW_REORDER_LEVEL);
		} else {
			reorder.getItem().setState(ItemState.IN_STOCK);
		}
		
		rRepo.delete(reorder);
	}

	@Override
	public List<Reorder> findAllByStatus(ReorderStatus status) {
		return rRepo.findAllByStatus(status);
	}
}
