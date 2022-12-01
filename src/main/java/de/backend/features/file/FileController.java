package de.backend.features.file;

import de.backend.features.auth.CurrentUserService;
import de.backend.features.file.dto.FileDto;
import de.backend.features.file.dto.FileDtoMapper;
import de.backend.features.subject.Subject;
import de.backend.features.subject.SubjectService;
import de.backend.features.user.User;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/group/{groupId}/school-year/{schoolYearId}/subject/{subjectId}/file")
@Tag(name = "File", description = "The File API")
@PreAuthorize("isAuthenticated()")
@SecurityRequirement(name = "bearerAuth")
public class FileController {

    private final SubjectService subjectService;

    private final CurrentUserService userService;

    private final FileService fileService;

    private final FileDtoMapper fileDtoMapper;

    @Autowired
    public FileController(SubjectService subjectService, CurrentUserService userService, FileService fileService, FileDtoMapper fileDtoMapper) {
        this.subjectService = subjectService;
        this.userService = userService;
        this.fileService = fileService;
        this.fileDtoMapper = fileDtoMapper;
    }

    @GetMapping
    @PreAuthorize("@groupAccess.canAccess(#groupId)")
    public List<FileDto> getList(@PathVariable String groupId, @PathVariable String subjectId) {
        return fileService.getList(subjectId).stream().map(fileDtoMapper::fileToFileDto).collect(Collectors.toList());
    }

    @GetMapping("/{fileId}")
    @PreAuthorize("@groupAccess.canAccess(#groupId)")
    public FileDto getFile(@PathVariable String fileId, @PathVariable String groupId) {
        return fileDtoMapper.fileToFileDto(fileService.getFile(fileId));
    }

    @PostMapping
    @PreAuthorize("@groupAccess.canAccess(#groupId)")
    public FileDto uploadFile(@PathVariable String groupId, @PathVariable String subjectId, @RequestPart("name") String name, @RequestPart("file") MultipartFile multipartFile) throws IllegalAccessException {
        User user = this.userService.getCurrentUser();
        Subject subject = this.subjectService.get(subjectId);
        File file = new File(name, user, subject);
        File result = fileService.uploadFile(file, multipartFile);
        return fileDtoMapper.fileToFileDto(result);
    }

    @PutMapping("/{fileId}")
    @PreAuthorize("@groupAccess.canAccess(#groupId)")
    public FileDto updateFile(@PathVariable String groupId, @PathVariable String fileId, @RequestPart("name") String name) throws IllegalAccessException {
        File file = fileService.getFile(fileId);
        if (name != null && !name.isEmpty() && !name.isBlank()) {
            file.setName(name);
        }
        File result = fileService.updateFile(file);
        return fileDtoMapper.fileToFileDto(result);
    }

    @DeleteMapping("/{fileId}")
    @PreAuthorize("@groupAccess.canAccess(#groupId)")
    public void deleteFile(@PathVariable String groupId, @PathVariable String fileId) {
        fileService.deleteFile(fileId);
    }
}
