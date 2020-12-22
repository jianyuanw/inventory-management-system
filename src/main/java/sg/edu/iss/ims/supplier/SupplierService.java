package sg.edu.iss.ims.supplier;


import java.util.List;

public interface SupplierService {

	public void saveSupplier(Supplier supplier);
	
	public Supplier findSupplierByName(String supplierName);
	
	public Supplier findSupplierById(Long id);
	
	public void deleteSupplier(Long id);
	
	public List<Supplier> list();

	public Supplier createSupplier(String name);
}
