package de.backend.features.file;

import de.backend.media.service.MediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class FileService {

    private final FileRepository fileRepository;

    private final MediaService mediaService;

    @Autowired
    public FileService(FileRepository fileRepository, MediaService mediaService) {
        this.fileRepository = fileRepository;
        this.mediaService = mediaService;
    }

    public File uploadFile(File file, MultipartFile multipartFile) throws IllegalAccessException {
        if (file.getName() == null || file.getName().isBlank() || file.getName().isEmpty()) {
            throw new IllegalAccessException("Name must be set!");
        }
        try {
            String groupId = file.getSubject().getSchoolYear().getGroup().getId();
            file.setMedia(mediaService.upload(multipartFile, "group/" + groupId + "/files/"));
            return fileRepository.save(file);
        } catch (IOException e) {
            throw new IllegalArgumentException("File could not be uploaded");
        }
    }

    public List<File> getList(String subjectId) {
        return fileRepository.findAllBySubjectId(subjectId);
    }

    public File getFile(String id) {
        return fileRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("File not found"));
    }

    public File updateFile(File file) throws IllegalAccessException {
        if (file.getName() == null || file.getName().isBlank() || file.getName().isEmpty()) {
            throw new IllegalAccessException("Name must be set!");
        }
        return fileRepository.save(file);
    }

    public void deleteFile(String id) {
        fileRepository.deleteById(id);
    }
}
