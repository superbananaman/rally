package rally;

public class Submission {
	private String path;
	private String approved;

	public Submission(String item) {
		this.path = item;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getApproved() {
		return approved;
	}

	public void setApproved(String approved) {
		this.approved = approved;
	}

}
