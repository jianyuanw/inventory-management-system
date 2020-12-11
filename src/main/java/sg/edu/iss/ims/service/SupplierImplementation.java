package sg.edu.iss.ims.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.edu.iss.ims.model.Supplier;
import sg.edu.iss.ims.repo.SupplierRepository;

@Service
@Transactional
public class SupplierImplementation implements SupplierInterface{

	@Autowired
	private SupplierRepository supplierRepo;

	@Override
	public void createSupplier(Supplier supplier) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void readSupplier(String supplierName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateSupplier(Supplier supplier) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteSupplier(Supplier supplier) {
		// TODO Auto-generated method stub
		
	}
	
}
