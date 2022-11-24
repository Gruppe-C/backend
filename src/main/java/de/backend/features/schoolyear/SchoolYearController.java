package de.backend.features.schoolyear;

import de.backend.features.group.Group;
import de.backend.features.group.GroupService;
import de.backend.features.schoolyear.dto.CreateSchoolYearDto;
import de.backend.features.schoolyear.dto.SchoolYearDto;
import de.backend.features.schoolyear.dto.SchoolYearDtoMapper;
import de.backend.features.user.User;
import de.backend.features.user.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/group/{groupId}/school-year")
@Tag(name = "SchoolYear", description = "The SchoolYear API")
@PreAuthorize("isAuthenticated()")
@SecurityRequirement(name = "bearerAuth")
public class SchoolYearController {

    private final SchoolYearService schoolYearService;

    private final GroupService groupService;

    private final UserService userService;

    private final SchoolYearDtoMapper schoolYearDtoMapper;

    @Autowired
    public SchoolYearController(SchoolYearService schoolYearService, GroupService groupService, UserService userService, SchoolYearDtoMapper schoolYearDtoMapper) {
        this.schoolYearService = schoolYearService;
        this.groupService = groupService;
        this.userService = userService;
        this.schoolYearDtoMapper = schoolYearDtoMapper;
    }

    @GetMapping
    public List<SchoolYearDto> getList(@PathVariable String groupId, Principal principal) {
        User user = this.userService.getByUsername(principal.getName());
        Group group = this.groupService.get(groupId);
        if (group.getOwner().equals(user) || group.getMembers().contains(user)) {
            return this.schoolYearService.getList(groupId).stream()
                    .map(this.schoolYearDtoMapper::schoolYearToSchoolYearDto)
                    .collect(Collectors.toList());
        } else {
            throw new AccessDeniedException("Access denied");
        }
    }

    @GetMapping("/{id}")
    public SchoolYearDto get(@PathVariable String groupId, @PathVariable String id, Principal principal) {
        User user = this.userService.getByUsername(principal.getName());
        Group group = this.groupService.get(groupId);
        if (group.getOwner().equals(user) || group.getMembers().contains(user)) {
            return this.schoolYearDtoMapper.schoolYearToSchoolYearDto(this.schoolYearService.get(id));
        } else {
            throw new AccessDeniedException("Access denied");
        }
    }

    @PostMapping
    public SchoolYearDto create(@PathVariable String groupId, @RequestBody CreateSchoolYearDto schoolYearDto, Principal principal) {
        Group group = this.groupService.get(groupId);
        User user = this.userService.getByUsername(principal.getName());
        if (group.getOwner().getId().equals(user.getId())) {
            if (schoolYearDto.startYear() >= schoolYearDto.endYear()) {
                throw new IllegalArgumentException("Start year must be before end year");
            } else {
                SchoolYear schoolYear = new SchoolYear(schoolYearDto.startYear(), schoolYearDto.endYear(), group);
                return this.schoolYearDtoMapper.schoolYearToSchoolYearDto(this.schoolYearService.create(schoolYear));
            }
        } else {
            throw new AccessDeniedException("You are not allowed to create a school year for this group");
        }
    }

    @PutMapping("/{id}")
    public SchoolYearDto update(@PathVariable String groupId, @PathVariable String id, @RequestBody CreateSchoolYearDto schoolYearDto, Principal principal) {
        Group group = this.groupService.get(groupId);
        User user = this.userService.getByUsername(principal.getName());
        if (group.getOwner().getId().equals(user.getId())) {
            SchoolYear schoolYear = this.schoolYearService.get(id);
            if (schoolYearDto.startYear() >= schoolYearDto.endYear()) {
                throw new IllegalArgumentException("Start year must be before end year");
            } else {
                schoolYear.setStartYear(schoolYearDto.startYear());
                schoolYear.setEndYear(schoolYearDto.endYear());
            }
            return this.schoolYearDtoMapper.schoolYearToSchoolYearDto(this.schoolYearService.update(schoolYear));
        } else {
            throw new AccessDeniedException("You are not allowed to update a school year for this group");
        }
    }
}
