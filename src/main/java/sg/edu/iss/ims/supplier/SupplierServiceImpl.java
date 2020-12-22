package sg.edu.iss.ims.supplier;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	public Supplier createSupplier(String name) {
		Supplier supplier = new Supplier(name);
		supplierRepo.save(supplier);
		return supplier;
	}	

	@Override
	public Supplier findSupplierByName(String name) {
		return supplierRepo.findSupplierByName(name);
		
	}

	@Override
	public Supplier findSupplierById(Long id) {
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
