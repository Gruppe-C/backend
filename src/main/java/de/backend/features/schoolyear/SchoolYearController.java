package de.backend.features.schoolyear;

import de.backend.features.group.Group;
import de.backend.features.group.GroupService;
import de.backend.features.schoolyear.dto.CreateSchoolYearDto;
import de.backend.features.schoolyear.dto.SchoolYearDto;
import de.backend.features.schoolyear.dto.SchoolYearDtoMapper;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
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

    private final SchoolYearDtoMapper schoolYearDtoMapper;

    @Autowired
    public SchoolYearController(SchoolYearService schoolYearService, GroupService groupService, SchoolYearDtoMapper schoolYearDtoMapper) {
        this.schoolYearService = schoolYearService;
        this.groupService = groupService;
        this.schoolYearDtoMapper = schoolYearDtoMapper;
    }

    @GetMapping
    @PreAuthorize("@groupAccess.canAccess(#groupId)")
    public List<SchoolYearDto> getList(@PathVariable String groupId) {
        return this.schoolYearService.getList(groupId).stream()
                .map(this.schoolYearDtoMapper::schoolYearToSchoolYearDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @PreAuthorize("@groupAccess.canAccess(#groupId)")
    public SchoolYearDto get(@PathVariable String groupId, @PathVariable String id) {
        return this.schoolYearDtoMapper.schoolYearToSchoolYearDto(this.schoolYearService.get(id));
    }

    @PostMapping
    @PreAuthorize("@groupAccess.canAccess(#groupId)")
    public SchoolYearDto create(@PathVariable String groupId, @RequestBody CreateSchoolYearDto schoolYearDto) {
        Group group = this.groupService.get(groupId);
        SchoolYear schoolYear = new SchoolYear(schoolYearDto.startYear(), schoolYearDto.endYear(), group);
        return this.schoolYearDtoMapper.schoolYearToSchoolYearDto(this.schoolYearService.create(schoolYear));
    }

    @PutMapping("/{id}")
    @PreAuthorize("@groupAccess.canUpdate(#groupId)")
    public SchoolYearDto update(@PathVariable String groupId, @PathVariable String id, @RequestBody CreateSchoolYearDto schoolYearDto, Principal principal) {
        SchoolYear schoolYear = this.schoolYearDtoMapper.updateSchoolYearFromCreateSchoolYearDto(schoolYearDto, this.schoolYearService.get(id));
        return this.schoolYearDtoMapper.schoolYearToSchoolYearDto(this.schoolYearService.update(schoolYear));
    }
}
