package sg.edu.iss.ims.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.edu.iss.ims.repo.SupplierRepository;

@Service
@Transactional
public class SupplierImplementation {

	@Autowired
	private SupplierRepository supplierRepo;
	
}
