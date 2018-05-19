package rally;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("rally")
public class rallyProperties {

    private List<String> items;
    private List<String> teams;
    
	public List<String> getItems() {
		return items;
	}
	public void setItems(List<String> items) {
		this.items = items;
	}
	public List<String> getTeams() {
		return teams;
	}
	public void setTeams(List<String> teams) {
		this.teams = teams;
	}

    
}
