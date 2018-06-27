package rally;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Teams {

	private HashMap<String, Team> teams;

	@Value("${teams}")
	private String teamsValue;
	
	@Autowired
	public void loadTeams() {
		teams = new HashMap<>();
		for(String name : teamsValue.split(",")) {
			teams.put(name, new Team(name));
		}
	}

	public void addTeam(String name, Team team) {
		teams.put(name, team);
	}

	public Team getTeam(String name) {
		return teams.get(name);
	}

	public HashMap<String, Team> getTeams() {
		return teams;
	}

	
}
