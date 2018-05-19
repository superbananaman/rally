package rally;

import java.util.HashMap;

public class Item {
	
	private String name;
	private int points;
	private Submission files;
	
	public Item(String name, int points, Submission file) {
		this.name = name;
		this.points = points;
		this.files = file;
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

	
	
}
