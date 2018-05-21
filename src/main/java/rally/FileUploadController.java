package rally;

import java.io.IOException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import rally.storage.StorageFileNotFoundException;
import rally.storage.StorageService;

@Controller
public class FileUploadController {

    private final StorageService storageService;
   
    @Value("${teams}")
    private String teamNames;
    
    private Teams teams;

    @Autowired
    public FileUploadController(StorageService storageService, Teams teams) {
        this.storageService = storageService;
        this.teams = teams;
    }

    @GetMapping("/upload")
    public String listUploadedFiles(Model model) throws IOException {
    	model.addAttribute("items",teams.getTeam(teamNames.split(",")[0]).getItems().getItems().entrySet());
    	model.addAttribute("teamNames",teamNames.split(","));
    	model.addAttribute("Teams",teams);
    	model.addAttribute("message", "messagetest");
        return "upload";
    }

    @GetMapping("/files/{team}/{file}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable("team") String team, @PathVariable("file") String filename ) {

        Resource file = storageService.loadAsResource(team+"/"+filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @PostMapping("/")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
            RedirectAttributes redirectAttributes) {

        storageService.store(file);
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");

        return "redirect:/";
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }

}