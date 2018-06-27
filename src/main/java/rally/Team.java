package rally;

import java.util.ArrayList;
import java.util.Map.Entry;

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
	
	public int getCurrentScore() {
		int score = 0;
		for(Entry<String,Item> entry : items.getItems().entrySet()) {
			if(entry.getValue().getFiles()  != null) {
			score += entry.getValue().getPoints();
			}
		}
		System.out.println(score);
		return score;
	}

}
