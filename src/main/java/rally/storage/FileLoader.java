package rally.storage;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import rally.AppController;
import rally.Item;
import rally.Items;
import rally.Submission;
import rally.Team;
import rally.Teams;

@Component
public class FileLoader {

	private Teams teams;

	@Value("#{${items}}")
	private Map<String, Integer> itemValues;

	private final StorageService storageService;

	@Autowired
	public FileLoader(StorageService storageService, Teams teams) {
		this.storageService = storageService;
		this.teams = teams;
	}

	public void initialLoad() {
		teams.loadTeams();
		loadItemsList();

		List<Path> list = storageService.loadAll().collect(Collectors.toList());

		for (Path item : list) {
			if(item.toAbsolutePath().toString().contains(".DS_Store")) {
				continue;
			}
			String[] itemSplit = item.toAbsolutePath().toString().split("/");
			int len = itemSplit.length;
			String file = itemSplit[len - 1].substring(0,itemSplit[len-1].indexOf("."));
			Team team = teams.getTeam(itemSplit[len-2]);
			Submission sub = new Submission(item.toString());
			boolean isImage = item.getFileName().toString().contains(".mp4")?false : true;
			sub.setImage(isImage);
			team.getItems().getItems().get(file)
					.addSubmission(sub);
		
		}
	}

	public void loadItemsList() {		
		for (Entry<String, Team> team : teams.getTeams().entrySet()) {
			
			HashMap<String, Item> initItems = new HashMap();
			for (Entry<String, Integer> entry : itemValues.entrySet()) {
				initItems.put(entry.getKey(), new Item(entry.getKey(), entry.getValue(), null));
			}
			
			HashMap<String, Item> items = new HashMap<String,Item>();
			for (Entry<String, Item> entry : initItems.entrySet()) {
				items.put(entry.getKey(), entry.getValue());
			}
			team.getValue().setItems(new Items(items));
		}
	}
}
