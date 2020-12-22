package sg.edu.iss.ims.item;

import java.util.ArrayList;
import java.util.HashMap;
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
	
	public void addToList(Item item) {
		list.add(item);
	}
	
	public void compactList() {
		List<Item> compactList = new ArrayList<Item>();
		
		HashMap<Long, Integer> map = new HashMap<Long, Integer>();
		
		for (Item item : list) {
			if (!map.containsKey(item.getId())) {
				map.put(item.getId(), item.getUnits());
			} else {
				map.put(item.getId(), map.get(item.getId()) + item.getUnits());
			}
		}
		
		for (Long id : map.keySet()) {
			Item item = new Item();
			item.setId(id);
			item.setUnits(map.get(id));
			compactList.add(item);
		}
		
		this.setList(compactList);
	}	
	
}
