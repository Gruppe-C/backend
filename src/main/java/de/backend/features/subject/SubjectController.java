package de.backend.features.subject;

import de.backend.features.group.Group;
import de.backend.features.group.GroupService;
import de.backend.features.schoolyear.SchoolYear;
import de.backend.features.schoolyear.SchoolYearService;
import de.backend.features.subject.dto.CreateSubjectDto;
import de.backend.features.subject.dto.SubjectDto;
import de.backend.features.subject.dto.SubjectDtoMapper;
import de.backend.features.user.User;
import de.backend.features.user.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/group/{groupId}/school-year/{schoolYearId}/subject")
@Tag(name = "Subject", description = "The Subject API")
@PreAuthorize("isAuthenticated()")
@SecurityRequirement(name = "bearerAuth")
public class SubjectController {
    private final SubjectService subjectService;

    private final SubjectDtoMapper mapper;

    private final SchoolYearService schoolYearService;

    private final UserService userService;

    private final GroupService groupService;

    @Autowired
    public SubjectController(final SubjectService subjectService, final SubjectDtoMapper mapper, final SchoolYearService schoolYearService, final UserService userService, GroupService groupService) {
        this.subjectService = subjectService;
        this.mapper = mapper;
        this.schoolYearService = schoolYearService;
        this.userService = userService;
        this.groupService = groupService;
    }

    @GetMapping
    public List<SubjectDto> getList(@PathVariable final String schoolYearId, Principal principal, @PathVariable String groupId) {
        final User user = this.userService.getByUsername(principal.getName());
        final Group group = this.groupService.get(groupId);
        if (group.getMembers().contains(user) || group.getOwner().equals(user)) {
            List<Subject> subjects = subjectService.getListBySchoolYear(schoolYearId);
            return subjects.stream().map(mapper::subjectToSubjectDto).collect(Collectors.toList());
        } else {
            throw new AccessDeniedException("Access Denied");
        }
    }

    @GetMapping("/{subjectId}")
    public SubjectDto getById(Principal principal, @PathVariable final String groupId, @PathVariable final String subjectId) {
        User user = this.userService.getByUsername(principal.getName());
        Group group = this.groupService.get(groupId);
        if (group.getMembers().contains(user) || group.getOwner().equals(user)) {
            return this.mapper.subjectToSubjectDto(subjectService.get(subjectId));
        } else {
            throw new AccessDeniedException("Access Denied");
        }
    }

    @PostMapping
    public SubjectDto create(Principal principal, @PathVariable final String groupId, @RequestBody CreateSubjectDto subjectDto, @PathVariable String schoolYearId) throws IllegalAccessException {
        User user = this.userService.getByUsername(principal.getName());
        Group group = this.groupService.get(groupId);
        if (group.getMembers().contains(user) || group.getOwner().equals(user)) {
            final SchoolYear schoolYear = this.schoolYearService.get(schoolYearId);
            Subject subject = this.mapper.createSubjectDtoToSubject(subjectDto);
            subject.setSchoolYear(schoolYear);
            return this.mapper.subjectToSubjectDto(this.subjectService.create(subject));
        } else {
            throw new AccessDeniedException("Access Denied");
        }
    }

    @PutMapping("/{subjectId}")
    public SubjectDto update(Principal principal, @PathVariable String groupId, @PathVariable final String subjectId, @RequestBody @Valid CreateSubjectDto createSubjectDto) throws IllegalAccessException {
        User user = this.userService.getByUsername(principal.getName());
        Group group = this.groupService.get(groupId);
        if (group.getMembers().contains(user) || group.getOwner().equals(user)) {
            Subject subject = this.subjectService.get(subjectId);
            subject.setName(createSubjectDto.name());
            subject.setColor(createSubjectDto.color());
            return this.mapper.subjectToSubjectDto(this.subjectService.update(subject));
        } else {
            throw new AccessDeniedException("Access Denied");
        }
    }
}