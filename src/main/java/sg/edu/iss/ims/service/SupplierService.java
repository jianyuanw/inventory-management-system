package sg.edu.iss.ims.service;


import java.util.ArrayList;
import java.util.List;

import sg.edu.iss.ims.model.Supplier;

public interface SupplierService {

	public void saveSupplier(Supplier supplier);
	
	public ArrayList<Supplier> findSupplierByName(String supplierName);
	
	public Supplier findSupplierById(Long id);
	
	public void deleteSupplier(Long id);
	
	public List<Supplier> list();
}
