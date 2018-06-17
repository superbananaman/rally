package rally;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import rally.storage.FileLoader;
import rally.storage.StorageFileNotFoundException;
import rally.storage.StorageService;

@Controller
public class AppController {

    private final StorageService storageService;
   
    @Value("${teams}")
    private String teamNames;
    private FileLoader fileLoader;
    private Teams teams;

    @Autowired
    public AppController(StorageService storageService, Teams teams, FileLoader fileLoader) {
        this.storageService = storageService;
        this.teams = teams;
        this.fileLoader = fileLoader;
    }
    
    @GetMapping("/")
    public String index(Model model) {
    	model.addAttribute("teams",teams);
        return "index";
    }

    @GetMapping("/upload")
    public String listUploadedFiles(Model model, @CookieValue("team") String team) throws IOException {
    	model.addAttribute("items",teams.getTeam(team.toLowerCase()).getItems().getItems().entrySet());
    	model.addAttribute("teamNames",teamNames.split(","));
    	model.addAttribute("Teams",teams);
        return "upload";
    }
    
    @GetMapping("/viewall")
    public String viewAll(Model model) throws IOException {
    	Map<Team,Items> itemMap = new HashMap<>();
    	for(Entry<String, Team> entry : teams.getTeams().entrySet()) {
    		itemMap.put(entry.getValue(), entry.getValue().getItems());
    	}
      	model.addAttribute("teams", itemMap);
        return "viewall";
        
    }

    @GetMapping("/files/{team}/{file}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable("team") String team, @PathVariable("file") String filename ) {

        Resource file = storageService.loadAsResource(team+"/"+filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @PostMapping(path="/store", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public void handleFileUpload(@CookieValue("team") String team, @CookieValue("name") String name, @RequestParam("file") MultipartFile file, @RequestHeader("item") String itemName,
            RedirectAttributes redirectAttributes, HttpServletRequest request) {
        storageService.store(file, team+"/"+itemName,team,itemName);
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }

}