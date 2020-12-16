package sg.edu.iss.ims.model;

import java.util.ArrayList;
import java.util.List;

public class ItemList {
	private List<Item> list;
	
	public ItemList() {
		list = new ArrayList<Item>();
	}

	public List<Item> getList() {
		return list;
	}

	public void setList(List<Item> list) {
		this.list = list;
	}
	
	public void addProduct() {
		list.add(new Item());
	}
	
	public void removeProduct(int index) {
		list.remove(index);
	}
	
	
}
