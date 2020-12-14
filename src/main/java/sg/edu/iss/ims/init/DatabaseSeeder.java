package sg.edu.iss.ims.init;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import javax.transaction.Transactional;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import sg.edu.iss.ims.model.Brand;
import sg.edu.iss.ims.model.Category;
import sg.edu.iss.ims.model.Item;
import sg.edu.iss.ims.model.Product;
import sg.edu.iss.ims.model.Subcategory;
import sg.edu.iss.ims.model.Supplier;
import sg.edu.iss.ims.repo.BrandRepository;
import sg.edu.iss.ims.repo.CategoryRepository;
import sg.edu.iss.ims.repo.ItemRepository;
import sg.edu.iss.ims.repo.ProductRepository;
import sg.edu.iss.ims.repo.SubcategoryRepository;
import sg.edu.iss.ims.repo.SupplierRepository;

@Component
class DatabaseSeeder implements InitializingBean {
	private final SubcategoryRepository subcatRepo;
	private final CategoryRepository catRepo;
	private final BrandRepository brandRepo;
	private final SupplierRepository supplierRepo;
	private final ProductRepository productRepo;
	private final ItemRepository itemRepo;
	
	public DatabaseSeeder(SubcategoryRepository subcatRepo, CategoryRepository catRepo, BrandRepository brandRepo, 
						  SupplierRepository supplierRepo, ProductRepository productRepo, ItemRepository itemRepo) {
		this.subcatRepo = subcatRepo;
		this.catRepo = catRepo;
		this.brandRepo = brandRepo;
		this.supplierRepo = supplierRepo;
		this.productRepo = productRepo;
		this.itemRepo = itemRepo;
	}

	  @Override
	  @Transactional
	  public void afterPropertiesSet() throws Exception {
		  boolean recreateData = true;
		  
		  String[] categories = {"Tyres", "Brake System", "Engine", "Filters", "Oils and Fluids"};
		  String[] subCategories = {"Winter Tyres, Summer Tyres", 
				  					"Brake discs, Brake pads, Brake hose, Brake shoes", 
				  					"Head gasket, Engine mount, Thermostat, Ignition coil", 
				  					"Oil filter, Air filter, Pollen filter, Fuel filter", 
				  					"Engine oil, Brake fluid, Gearbox oil, Antifreeze"};
		  String[] brands = {"Volkswagen", "Toyota", "Renault", "Nissan", "Mitsubishi", "Castrol"};
		  String[] suppliers = {"Volkswagen Group Singapore", "Borneo Motors", "Wearnes Automotive", "Tan Chong Motor Sales", "Cycle & Carriage Mitsubishi", "Gee Boon Enterprises"};
		  String[] colors = {"red", "blue", "green", "yellow"};
		  String[] shelves = {"Upper Shelf A", "Upper Shelf B", "Upper Shelf C", "Lower Shelf A", "Lower Shelf B", "Lower Shelf C"};
		  
		  if (recreateData) {
			  itemRepo.deleteAll();
			  productRepo.deleteAll();
			  catRepo.deleteAll();
			  brandRepo.deleteAll();
			  supplierRepo.deleteAll();

			  
			  Map<String, String[]> catList = new HashMap<String, String[]>();
			  for (int i = 0; i < categories.length; i++) {
				  catList.put(categories[i], subCategories[i].split(", "));
			  }
			  
			  for (String category: catList.keySet()) {
				  var c = new Category(category);
				  catRepo.save(c);
				  for (String subcategory : catList.get(category)) {
					  if (subcategory != "") {
						  var sc = new Subcategory();
						  sc.setCategory(c);
						  sc.setName(subcategory);
						  subcatRepo.save(sc);
					  }
				  }				  
			  }
			  
			  Arrays.stream(brands).forEach(brand -> brandRepo.save(new Brand(brand)));
			  
			  Arrays.stream(suppliers).forEach(supplier -> supplierRepo.save(new Supplier(supplier)));
			  
			  Random r = new Random();
			  
			  for (int i = 0; i < brands.length - 1; i++) {
				  var brand = brandRepo.findBrandByName(brands[i]);
				  var supplier = supplierRepo.findSupplierByName(suppliers[i]);
				  for (String category : categories) {
					  var c = catRepo.findCategoryByName(category);
					  for (Subcategory sc : subcatRepo.findSubcategoriesByCategory(c)) {
						  double price = ThreadLocalRandom.current().nextDouble(60, 99.99);
						  double measurement = ThreadLocalRandom.current().nextInt(15, 20);
						  var partNumber = c.getName().substring(0, 2).toUpperCase() + "_" + sc.getName().substring(0, 2).toUpperCase() + "-" + brand.getName().substring(0, 2).toUpperCase() + "-" + supplier.getName().substring(0, 2).toUpperCase();
						  var name = brand.getName() + " " + sc.getName();
						  var description = name + ", supplied by " + supplier.getName();
						  String color = colors[r.nextInt(colors.length)];
						  Product p = new Product(partNumber, name, description, price, price + 8, price + 20, price + 5,
								  				  color, measurement + "mm", "General measurement", c, sc, supplier, brand, "/fillerlink");
						  
						  productRepo.save(p);
						  
						  int value = ThreadLocalRandom.current().nextInt(50, 100);
						  
						  Item item = new Item(p, value, ThreadLocalRandom.current().nextInt(20, 35),
								  			value, shelves[r.nextInt(shelves.length)]);
						  itemRepo.save(item);
					  }
				  }
			  }
			  
			  
		  }
		  
	  }

}
