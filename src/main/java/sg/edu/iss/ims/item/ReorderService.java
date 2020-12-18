package sg.edu.iss.ims.item;

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
}
