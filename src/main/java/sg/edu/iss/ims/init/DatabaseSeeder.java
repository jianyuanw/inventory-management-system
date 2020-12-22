package sg.edu.iss.ims.init;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import sg.edu.iss.ims.item.ItemList;
import sg.edu.iss.ims.item.ItemRepository;
import sg.edu.iss.ims.item.ItemState;
import sg.edu.iss.ims.item.Reorder;
import sg.edu.iss.ims.item.ReorderRepository;
import sg.edu.iss.ims.item.ReorderStatus;
import sg.edu.iss.ims.job.Job;
import sg.edu.iss.ims.job.JobRepository;
import sg.edu.iss.ims.job.JobService;
import sg.edu.iss.ims.job.JobServiceImpl;
import sg.edu.iss.ims.product.Product;
import sg.edu.iss.ims.product.ProductRepository;
import sg.edu.iss.ims.supplier.Supplier;
import sg.edu.iss.ims.supplier.SupplierRepository;
import sg.edu.iss.ims.transaction.Transaction;
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
	private final ReorderRepository reorderRepo;
	private final UserRepository userRepo;
	private final JobService jobService;
	private PasswordEncoder encoder;
	
	public DatabaseSeeder(SubcategoryRepository subcatRepo, CategoryRepository catRepo, BrandRepository brandRepo,
						  SupplierRepository supplierRepo, ProductRepository productRepo, ItemRepository itemRepo,
						  TransactionRepository transactionRepo, JobRepository jobRepo,
						  ReorderRepository reorderRepo, UserRepository userRepo, PasswordEncoder encoder, JobServiceImpl jobServiceImpl) {
		this.subcatRepo = subcatRepo;
		this.catRepo = catRepo;
		this.brandRepo = brandRepo;
		this.supplierRepo = supplierRepo;
		this.productRepo = productRepo;
		this.itemRepo = itemRepo;
		this.transactionRepo = transactionRepo;
		this.jobRepo = jobRepo;
		this.reorderRepo = reorderRepo;
		this.userRepo = userRepo;
		this.encoder = encoder;
		jobService = jobServiceImpl;
	}

	  @Override
	  @Transactional
	  public void afterPropertiesSet() throws Exception {
		  // Randomly generates data once, but due to not being able to drop from db, once desync'd
		  // it is best to drop all tables and rerun
		  // Did not use create-drop due to odd initialization errors
		  boolean recreateData = false;
		  
		  List<User> users = userRepo.findAll();
		  users.sort(Comparator.comparing(User::getId));
		  
		  // Not a foolproof state detector, please drop and reseed if needed
		  if (users.size() < 3) {
			  recreateData = true;
		  } else if (users.get(0).getUsername() == "admin" && users.get(1).getUsername() == "mechanic" &&
				     users.get(2).getUsername() == "derek") {
			  recreateData = false;
		  }
		  
		  if (recreateData) {
			  generateStaticData();
			  generateRandomData(30, 30);
		  }
		  
	  }
	  
	  public void generateStaticData() {
		  String[] categories = {"Tyres", "Brake System", "Engine", "Filters"};
		  String[] subCategories = {"Winter Tyres, Summer Tyres", 
				  					"Brake discs, Brake pads, Brake hose, Brake shoes", 
				  					"Head gasket, Engine mount, Thermostat, Ignition coil", 
				  					"Oil filter, Air filter, Pollen filter, Fuel filter",};
		  String[] brands = {"Volkswagen", "Toyota", "Renault", "Nissan", "Mitsubishi"};
		  String[] suppliers = {"Volkswagen Group Singapore", "Borneo Motors", "Wearnes Automotive", "Tan Chong Motor Sales", "Cycle and Carriage Mitsubishi"};
		  String[] colors = {"red", "blue", "green", "yellow"};
		  String[] shelves = {"Upper Shelf A", "Upper Shelf B", "Upper Shelf C", "Lower Shelf A", "Lower Shelf B", "Lower Shelf C"};
		  

		  
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
			  var brand = brandRepo.findByName(brands[i]);
			  var supplier = supplierRepo.findSupplierByName(suppliers[i]);
			  for (String category : categories) {
				  var c = catRepo.findByName(category);
				  for (Subcategory sc : subcatRepo.findSubcategoriesByCategory(c)) {
					  double price = ThreadLocalRandom.current().nextInt(6000, 9999) / 100.0;
					  DecimalFormat rounder = new DecimalFormat("0.00");
					  price = Double.parseDouble(rounder.format(price));
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
		  
		  Map<String, String> userEmail = Map.of(
              "derek", "derek.cai@u.nus.edu",
              "jianyuan", "jywong@u.nus.edu",
              "angelia", "e0641592@u.nus.edu",
              "site", "e0576054@u.nus.edu",
              "yubo", "yu.bo@u.nus.edu",
              "zifeng", "zfmok@u.nus.edu",
              "yitong", "yitongyu@u.nus.edu",
              "jiawei", "e0641586@u.nus.edu"
		  );
		  
		  for (String username : userEmail.keySet()) {
			  userRepo.save(new User(username, encoder.encode(username), Role.ADMIN_CLERK, userEmail.get(username)));
		  }
	  }		  
	  
	  public void generateRandomData(int reorders, int transactions) {
		  String[] names = {"Derek Cai", "Jian Yuan", "Zi Feng", "Angelia", "Suriya", "Yu Bo", "Yi Tong", "Si Te", "Jia Wei"};
		  
		  List<Long> usedItems = new ArrayList<Long>();
		  
		  List<Item> items = itemRepo.findAll();
		  for (int i = 0; i < reorders; i++) {
			 Item item = items.get(ThreadLocalRandom.current().nextInt(0, items.size()));
			 while (usedItems.contains(item.getId())) {
				 item = items.get(ThreadLocalRandom.current().nextInt(0, items.size()));
			 }					 
					  
			 long minDay = LocalDate.of(2020, 12, 18).toEpochDay();
			 long maxDay = LocalDate.of(2020, 12, 20).toEpochDay();
			 long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);
			 LocalDate randomDate = LocalDate.ofEpochDay(randomDay);
			 Reorder reorder = new Reorder();
			 reorder.setItem(item);
			 reorder.setQuantity(item.getReorderQuantity());
			 reorder.setOrderDate(randomDate);
			 reorder.setStatus(ReorderStatus.PENDING_DELIVERY);
			 item.setState(ItemState.REORDER_PLACED);
			 itemRepo.save(item);
			 reorderRepo.save(reorder);
		  }
		  
		  for (int i = 0; i < transactions; i++) {
			  ItemList itemList = new ItemList();
			  for (int j = 0; j < ThreadLocalRandom.current().nextInt(1, 6); j++) {
				  Item item = items.get(ThreadLocalRandom.current().nextInt(0, items.size()));
				  int quantity = ThreadLocalRandom.current().nextInt(1, 8);
				  Item newItem = new Item();
				  newItem.setId(item.getId());
				  newItem.setUnits(quantity);
				  itemList.addToList(newItem);
			  }
			  Job job = new Job();
			  job.setCustomerName(names[ThreadLocalRandom.current().nextInt(0, names.length)]);
			  job.setDescription("General maintenance");
			  job.setCarLicensePlate(randomCarPlate());
			  jobService.createJob(job, itemList);
		  }
		  
		  List<Job> jobs = jobRepo.findAll();
		  
		  for (Job job : jobs) {
			  List<Transaction> jobTransactions = job.getTransactions();
			  int randomDays = ThreadLocalRandom.current().nextInt(1, 8);
			  for (Transaction t : jobTransactions) {
				  // For randomizing of time of pre-generated data				  
				  t.setTransactionTime(t.getTransactionTime().minusDays(randomDays));
				  transactionRepo.save(t);
			  }
		  }
	  }
	  
	  public String randomCarPlate() {
		  String letters = "ABCDEFGHJKLMNPQRSTUVWXYZ";
		  String numbers = "1234567890";
		  String checksum = "ABCDEGHJKLMPRSTU";
		  return "S" + letters.charAt(ThreadLocalRandom.current().nextInt(0, letters.length())) + 
				       letters.charAt(ThreadLocalRandom.current().nextInt(0, letters.length())) +
                       numbers.charAt(ThreadLocalRandom.current().nextInt(0, numbers.length())) +
                       numbers.charAt(ThreadLocalRandom.current().nextInt(0, numbers.length())) +
                       numbers.charAt(ThreadLocalRandom.current().nextInt(0, numbers.length())) +
                       numbers.charAt(ThreadLocalRandom.current().nextInt(0, numbers.length())) +
                       checksum.charAt(ThreadLocalRandom.current().nextInt(0, checksum.length()));
                    
	  }

}
