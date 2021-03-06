package rally.storage;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import rally.Submission;
import rally.Teams;

@Service
public class FileSystemStorageService implements StorageService {

    private final Path rootLocation;
    private Teams teams;

    @Autowired
    public FileSystemStorageService(StorageProperties properties, Teams teams) {
        this.rootLocation = Paths.get(properties.getLocation());
        this.teams = teams;
    }

    @Override
    public void store(MultipartFile file, String filename, String team, String item) {
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file " + filename);
            }
            if (filename.contains("..")) {
                // This is a security check
                throw new StorageException(
                        "Cannot store file with relative path outside current directory "
                                + filename);
            }
            try (InputStream inputStream = file.getInputStream()) {
            	file.getContentType();
            	String suffix = file.getContentType().substring(file.getContentType().indexOf('/')+1,file.getContentType().length());
            	suffix = "."+suffix;
            	Files.createDirectories(this.rootLocation.resolve(filename+suffix).getParent());
                Files.copy(inputStream, this.rootLocation.resolve(filename+suffix),
                    StandardCopyOption.REPLACE_EXISTING);
                Submission newSubmission = new Submission(filename.toLowerCase()+suffix);
                if(file.getContentType().contains("mp4")) {
                	newSubmission.setImage(false);
                }

		if(file.getContentType().contains("quick")){
			
			newSubmission.setImage(false);
			
		}
                teams.getTeam(team).getItems().getItems().get(item).setFiles(newSubmission);
            }
        }
        catch (IOException e) {
            throw new StorageException("Failed to store file " + filename, e);
        }
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.rootLocation, 3)
                .filter(path -> !path.equals(this.rootLocation))
                .filter(Files::isRegularFile)
                .map(this.rootLocation::relativize);
        }
        catch (IOException e) {
            throw new StorageException("Failed to read stored files", e);
        }

    }

    @Override
    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            }
            else {
                throw new StorageFileNotFoundException(
                        "Could not read file: " + filename);

            }
        }
        catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + filename, e);
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    @Override
    public void init() {
        try {
            Files.createDirectories(rootLocation);
        }
        catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
    }
}
