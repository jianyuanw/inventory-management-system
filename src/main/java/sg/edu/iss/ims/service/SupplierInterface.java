package sg.edu.iss.ims.service;

import sg.edu.iss.ims.model.Supplier;

public interface SupplierInterface {

	public void createSupplier(Supplier supplier);
	
	public void readSupplier(String supplierName);
	
	public void updateSupplier(Supplier supplier);
	
	public void deleteSupplier(Supplier supplier);
}
