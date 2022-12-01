package de.backend.features.subject;

import de.backend.features.schoolyear.SchoolYear;
import de.backend.features.schoolyear.SchoolYearService;
import de.backend.features.subject.dto.CreateSubjectDto;
import de.backend.features.subject.dto.SubjectDto;
import de.backend.features.subject.dto.SubjectDtoMapper;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @Autowired
    public SubjectController(final SubjectService subjectService, final SubjectDtoMapper mapper, final SchoolYearService schoolYearService) {
        this.subjectService = subjectService;
        this.mapper = mapper;
        this.schoolYearService = schoolYearService;
    }

    @GetMapping
    @PreAuthorize("@groupAccess.canAccess(#groupId)")
    public List<SubjectDto> getList(@PathVariable final String schoolYearId, @PathVariable String groupId) {
        List<Subject> subjects = subjectService.getListBySchoolYear(schoolYearId);
        return subjects.stream().map(mapper::subjectToSubjectDto).collect(Collectors.toList());
    }

    @GetMapping("/{subjectId}")
    @PreAuthorize("@groupAccess.canAccess(#groupId)")
    public SubjectDto getById(@PathVariable final String groupId, @PathVariable final String subjectId) {
        return this.mapper.subjectToSubjectDto(subjectService.get(subjectId));
    }

    @PostMapping
    @PreAuthorize("@groupAccess.canAccess(#groupId)")
    public SubjectDto create(@PathVariable final String groupId, @RequestBody CreateSubjectDto subjectDto, @PathVariable String schoolYearId) throws IllegalAccessException {
        final SchoolYear schoolYear = this.schoolYearService.get(schoolYearId);
        Subject subject = this.mapper.createSubjectDtoToSubject(subjectDto);
        subject.setSchoolYear(schoolYear);
        return this.mapper.subjectToSubjectDto(this.subjectService.create(subject));
    }

    @PutMapping("/{subjectId}")
    @PreAuthorize("@groupAccess.canAccess(#groupId)")
    public SubjectDto update(@PathVariable String groupId, @PathVariable final String subjectId, @RequestBody @Valid CreateSubjectDto createSubjectDto) throws IllegalAccessException {
        Subject subject = this.mapper.updateSubjectFromCreateSubjectDto(createSubjectDto, subjectService.get(subjectId));
        return this.mapper.subjectToSubjectDto(this.subjectService.update(subject));
    }
}
