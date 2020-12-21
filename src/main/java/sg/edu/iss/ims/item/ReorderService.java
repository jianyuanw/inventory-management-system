package sg.edu.iss.ims.item;

import sg.edu.iss.ims.supplier.Supplier;

import java.time.LocalDate;
import java.util.List;

public interface ReorderService {

    void create(Reorder reorder);
    List<Reorder> findAllReorders();
//    List<Reorder> findUndeliveredReorders();
    List<Reorder> findDeliveredOrders();
	Reorder findReorderById(Long reorderId);
	List<Reorder> findReordersByDateRange(Long supplierId, LocalDate fromDate, LocalDate toDate);
	LocalDate convertToDate(String date);
	double sumPrice(List<Reorder> reorders);
	void updateItemQty(Item item, int reorderQty);
	void updateItemState(Item item);
	String generateReport(List<Reorder> reorders, Supplier supplier, double totalPrice);
	void receiveDelivery(Long reorderId, LocalDate receivedDate);
	List<Reorder> findAllReordersByItemId(Long itemId);
	List<Reorder> findAllReordersByItemIdAndStatus(Long itemId, ReorderStatus status);
	void createDelivery(Reorder reorder);
	void cancelReorder(Long reorderId);
	List<Reorder> findAllByStatus(ReorderStatus pendingDelivery);
}
