package de.backend.features.file;

import de.backend.features.file.dto.FileDto;
import de.backend.features.file.dto.FileDtoMapper;
import de.backend.features.group.Group;
import de.backend.features.group.GroupService;
import de.backend.features.subject.Subject;
import de.backend.features.subject.SubjectService;
import de.backend.features.user.User;
import de.backend.features.user.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/group/{groupId}/school-year/{schoolYearId}/subject/{subjectId}/file")
@Tag(name = "File", description = "The File API")
@PreAuthorize("isAuthenticated()")
@SecurityRequirement(name = "bearerAuth")
public class FileController {

    private final GroupService groupService;

    private final SubjectService subjectService;

    private final UserService userService;

    private final FileService fileService;

    private final FileDtoMapper fileDtoMapper;

    @Autowired
    public FileController(GroupService groupService, SubjectService subjectService, UserService userService, FileService fileService, FileDtoMapper fileDtoMapper) {
        this.groupService = groupService;
        this.subjectService = subjectService;
        this.userService = userService;
        this.fileService = fileService;
        this.fileDtoMapper = fileDtoMapper;
    }

    @GetMapping
    public List<FileDto> getList(@PathVariable String groupId, @PathVariable String schoolYearId, @PathVariable String subjectId, Principal principal) {
        User user = userService.getByUsername(principal.getName());
        Group group = groupService.get(groupId);
        if (group.getOwner().equals(user) || group.getMembers().contains(user)) {
            return fileService.getList(subjectId).stream().map(fileDtoMapper::fileToFileDto).collect(Collectors.toList());
        } else {
            throw new IllegalArgumentException("User is not allowed to access this group");
        }
    }

    @GetMapping("/{fileId}")
    public FileDto getFile(@PathVariable String groupId, @PathVariable String schoolYearId, @PathVariable String subjectId, @PathVariable String fileId, Principal principal) {
        User user = userService.getByUsername(principal.getName());
        Group group = groupService.get(groupId);
        if (group.getOwner().equals(user) || group.getMembers().contains(user)) {
            return fileDtoMapper.fileToFileDto(fileService.getFile(fileId));
        } else {
            throw new IllegalArgumentException("User is not allowed to access this group");
        }
    }

    @PostMapping
    public FileDto uploadFile(@PathVariable String groupId, @PathVariable String schoolYearId, @PathVariable String subjectId, @RequestPart("name") String name, @RequestPart("file") MultipartFile multipartFile, Principal principal) throws IllegalAccessException {
        User user = userService.getByUsername(principal.getName());
        Group group = groupService.get(groupId);
        if (group.getOwner().equals(user) || group.getMembers().contains(user)) {
            Subject subject = this.subjectService.get(subjectId);
            File file = new File(name, user, subject);
            File result = fileService.uploadFile(file, multipartFile);
            return fileDtoMapper.fileToFileDto(result);
        } else {
            throw new IllegalArgumentException("User is not allowed to access this group");
        }
    }

    @PutMapping("/{fileId}")
    public FileDto updateFile(@PathVariable String groupId, @PathVariable String schoolYearId, @PathVariable String subjectId, @PathVariable String fileId, @RequestPart("name") String name, Principal principal) throws IllegalAccessException {
        User user = userService.getByUsername(principal.getName());
        Group group = groupService.get(groupId);
        if (group.getOwner().equals(user) || group.getMembers().contains(user)) {
            File file = fileService.getFile(fileId);
            file.setName(name);
            File result = fileService.updateFile(file);
            return fileDtoMapper.fileToFileDto(result);
        } else {
            throw new IllegalArgumentException("User is not allowed to access this group");
        }
    }

    @DeleteMapping("/{fileId}")
    public void deleteFile(@PathVariable String groupId, @PathVariable String schoolYearId, @PathVariable String subjectId, @PathVariable String fileId, Principal principal) {
        User user = userService.getByUsername(principal.getName());
        File file = fileService.getFile(fileId);
        if (file.getOwner().equals(user)) {
            fileService.deleteFile(fileId);
        } else {
            throw new IllegalArgumentException("Your are not allowed to delete this file");
        }
    }
}
