package rally;

import java.util.ArrayList;

public class Team {

	private Items items;

	private String name;

	public Team(String name) {
		this.name = name;
	}

	public Items getItems() {
		return items;
	}

	public void setItems(Items items) {
		this.items = items;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
