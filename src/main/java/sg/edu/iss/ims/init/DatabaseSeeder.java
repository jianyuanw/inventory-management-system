package sg.edu.iss.ims.init;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import javax.transaction.Transactional;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import sg.edu.iss.ims.brand.Brand;
import sg.edu.iss.ims.brand.BrandRepository;
import sg.edu.iss.ims.category.Category;
import sg.edu.iss.ims.category.CategoryRepository;
import sg.edu.iss.ims.category.Subcategory;
import sg.edu.iss.ims.category.SubcategoryRepository;
import sg.edu.iss.ims.item.Item;
import sg.edu.iss.ims.item.ItemRepository;
import sg.edu.iss.ims.item.ItemState;
import sg.edu.iss.ims.item.ReorderRepository;
import sg.edu.iss.ims.job.JobRepository;
import sg.edu.iss.ims.job.JobTransactionRepository;
import sg.edu.iss.ims.product.Product;
import sg.edu.iss.ims.product.ProductRepository;
import sg.edu.iss.ims.supplier.Supplier;
import sg.edu.iss.ims.supplier.SupplierRepository;
import sg.edu.iss.ims.transaction.TransactionRepository;
import sg.edu.iss.ims.user.Role;
import sg.edu.iss.ims.user.User;
import sg.edu.iss.ims.user.UserRepository;

@Component
class DatabaseSeeder implements InitializingBean {
	private final SubcategoryRepository subcatRepo;
	private final CategoryRepository catRepo;
	private final BrandRepository brandRepo;
	private final SupplierRepository supplierRepo;
	private final ProductRepository productRepo;
	private final ItemRepository itemRepo;
	private final TransactionRepository transactionRepo;
	private final JobRepository jobRepo;
	private final JobTransactionRepository jobTransactionRepo;
	private final ReorderRepository reorderRepo;
	private final UserRepository userRepo;
	private PasswordEncoder encoder;
	
	public DatabaseSeeder(SubcategoryRepository subcatRepo, CategoryRepository catRepo, BrandRepository brandRepo,
						  SupplierRepository supplierRepo, ProductRepository productRepo, ItemRepository itemRepo,
						  TransactionRepository transactionRepo, JobRepository jobRepo, JobTransactionRepository jobTransactionRepo,
						  ReorderRepository reorderRepo, UserRepository userRepo, PasswordEncoder encoder) {
		this.subcatRepo = subcatRepo;
		this.catRepo = catRepo;
		this.brandRepo = brandRepo;
		this.supplierRepo = supplierRepo;
		this.productRepo = productRepo;
		this.itemRepo = itemRepo;
		this.transactionRepo = transactionRepo;
		this.jobRepo = jobRepo;
		this.jobTransactionRepo = jobTransactionRepo;
		this.reorderRepo = reorderRepo;
		this.userRepo = userRepo;
		this.encoder = encoder;
	}

	  @Override
	  @Transactional
	  public void afterPropertiesSet() throws Exception {
		  boolean recreateData = true;
		  
		  String[] categories = {"Tyres", "Brake System", "Engine", "Filters"};
		  String[] subCategories = {"Winter Tyres, Summer Tyres", 
				  					"Brake discs, Brake pads, Brake hose, Brake shoes", 
				  					"Head gasket, Engine mount, Thermostat, Ignition coil", 
				  					"Oil filter, Air filter, Pollen filter, Fuel filter",};
		  String[] brands = {"Volkswagen", "Toyota", "Renault", "Nissan", "Mitsubishi"};
		  String[] suppliers = {"Volkswagen Group Singapore", "Borneo Motors", "Wearnes Automotive", "Tan Chong Motor Sales", "Cycle and Carriage Mitsubishi"};
		  String[] colors = {"red", "blue", "green", "yellow"};
		  String[] shelves = {"Upper Shelf A", "Upper Shelf B", "Upper Shelf C", "Lower Shelf A", "Lower Shelf B", "Lower Shelf C"};
		  
		  try {
			  if (catRepo.findById(1L).get().getName() == "Tyres" &&
				  subcatRepo.findById(1L).get().getName() == "Winter Tyres" &&
				  userRepo.findById(1L).get().getUsername() == "admin") {
				  recreateData = false;
			  }
		  } catch (NoSuchElementException e) {			  
		  }
		  
		  if (recreateData) {
		  	  reorderRepo.deleteAll();
		  	  jobTransactionRepo.deleteAll();
			  jobRepo.deleteAll();
			  transactionRepo.deleteAll();
			  itemRepo.deleteAll();
			  productRepo.deleteAll();
			  catRepo.deleteAll();
			  brandRepo.deleteAll();
			  supplierRepo.deleteAll();
			  userRepo.deleteAll();

			  
			  Map<String, String[]> catList = new HashMap<String, String[]>();
			  for (int i = 0; i < categories.length; i++) {
				  catList.put(categories[i], subCategories[i].split(", "));
			  }
			  
			  for (String category: catList.keySet()) {
				  var c = new Category(category);
				  catRepo.save(c);
				  for (String subcategory : catList.get(category)) {
					  if (subcategory != "") {
						  var sc = new Subcategory(c, subcategory);
						  subcatRepo.save(sc);
					  }
				  }				  
			  }
			  
			  Arrays.stream(brands).forEach(brand -> brandRepo.save(new Brand(brand)));
			  
			  Arrays.stream(suppliers).forEach(supplier -> supplierRepo.save(new Supplier(supplier)));
			  
			  Random r = new Random();
			  
			  Map<String, Integer> usedCategories = new HashMap<String, Integer>();
			  
			  for (int i = 0; i < brands.length; i++) {
				  var brand = brandRepo.findBrandByName(brands[i]);
				  var supplier = supplierRepo.findSupplierByName(suppliers[i]);
				  for (String category : categories) {
					  var c = catRepo.findCategoryByName(category);
					  for (Subcategory sc : subcatRepo.findSubcategoriesByCategory(c)) {
						  double price = ThreadLocalRandom.current().nextInt(6000, 9999) / 100.0;
						  price = (double) ((int) (price * 100) / 100.0); 
						  double measurement = ThreadLocalRandom.current().nextInt(15, 20);
						  var partNumber = c.getName().substring(0, 2).toUpperCase() + "_" + sc.getName().substring(0, 2).toUpperCase() + "-" + brand.getName().substring(0, 2).toUpperCase() + "_" + supplier.getName().substring(0, 2).toUpperCase();
						  if (usedCategories.containsKey(partNumber)) {
							  usedCategories.put(partNumber, usedCategories.get(partNumber) + 1);
							  partNumber += usedCategories.get(partNumber);							  
						  } else {
							  usedCategories.put(partNumber, 1);
							  partNumber += 1;						  
						  }
						  var name = brand.getName() + " " + sc.getName();
						  var description = name + ", supplied by " + supplier.getName();
						  String color = colors[r.nextInt(colors.length)];
						  Product p = new Product(partNumber, name, description, price, price + 8, price + 20, price + 5,
								  				  color, measurement + "mm", "General measurement", c, sc, supplier, brand, "/img/" + name.toLowerCase().replace(' ', '_') + ".jpg");
						  
						  productRepo.save(p);
						  
						  int value = ThreadLocalRandom.current().nextInt(50, 100) / 10 * 10;
						  
						  Item item = new Item(p, value, ThreadLocalRandom.current().nextInt(20, 35) / 10 * 10,
								  			value, shelves[r.nextInt(shelves.length)], ItemState.IN_STOCK);
						  itemRepo.save(item);
					  }
				  }
			  }
			  
			  userRepo.save(new User("admin", encoder.encode("admin"), Role.ADMIN_CLERK, "caiylderek@gmail.com"));
			  userRepo.save(new User("mechanic", encoder.encode("mechanic"), Role.MECHANIC, "caiylderek@gmail.com"));			  			  
			  
		  }
		  
	  }

}
