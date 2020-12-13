package sg.edu.iss.ims.service;


import java.util.List;

import sg.edu.iss.ims.model.Supplier;

public interface SupplierService {

	public void createSupplier(Supplier supplier);
	
	public Supplier readSupplier(String supplierName);
	
	public Supplier updateSupplier(Long id);
	
	public void deleteSupplier(Long id);
	
	public List<Supplier> list();
}
