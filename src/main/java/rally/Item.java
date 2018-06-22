package rally;

import java.util.HashMap;

public class Item {
	
	private String name;
	private String Description;
	private String id;
	private int points;
	private Submission files;
	
	public Item(String name, String description, int points, Submission file) {
		this.Description = description;
		this.name = name;
		this.points = points;
		this.files = file;
		this.id = name.replaceAll(" ", "");
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public int getPoints() {
		return points;
	}
	public void setPoints(int points) {
		this.points = points;
	}
	
	public void addSubmission(Submission submission) {
		files = submission;
	}
	public Submission getFiles() {
		return files;
	}
	public void setFiles(Submission files) {
		this.files = files;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	
	
}
