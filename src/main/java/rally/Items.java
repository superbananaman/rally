package rally;

import java.util.HashMap;

import org.springframework.stereotype.Component;

public class Items {
	// ITEMNAME , ITEM
	private HashMap<String, Item> items;

	public Items(HashMap<String, Item> initItems) {
		this.items = initItems;
	}

	public HashMap<String, Item> getItems() {
		return items;
	}

	public void setItems(HashMap<String, Item> items) {
		this.items = items;
	}

}
