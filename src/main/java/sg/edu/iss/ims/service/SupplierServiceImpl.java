package sg.edu.iss.ims.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.edu.iss.ims.model.Supplier;
import sg.edu.iss.ims.repo.SupplierRepository;

@Service
@Transactional
public class SupplierServiceImpl implements SupplierService{

	@Autowired
	private SupplierRepository supplierRepo;

	@Override
	public void saveSupplier(Supplier supplier) {
		supplierRepo.save(supplier);
		
	}

	@Override
	public Supplier findSupplierByName(String name) {
		return supplierRepo.findSupplierByName(name);
		
	}

	@Override
	public Supplier findSupplierById(Long id) {
		//To change the update logic, put as save for now
		return supplierRepo.findById(id).get();
		
	}

	@Override
	public void deleteSupplier(Long id) {
		supplierRepo.delete(findSupplierById(id));
		
	}
	
	@Transactional
	public List<Supplier> list(){
		return supplierRepo.findAll();
	}
	
}
