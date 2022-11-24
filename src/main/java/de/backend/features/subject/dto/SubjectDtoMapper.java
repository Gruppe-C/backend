package de.backend.features.subject.dto;

import de.backend.features.group.Group;
import de.backend.features.group.dto.CreateGroupDto;
import de.backend.features.subject.Subject;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface SubjectDtoMapper {

    @Mapping(source = "schoolYear.groupId", target = "schoolYear.group.id")
    Subject subjectDtoToSubject(SubjectDto subjectDto);

    @Mapping(target = "schoolYear.groupId", source = "schoolYear.group.id")
    SubjectDto subjectToSubjectDto(Subject subject);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Subject updateSubjectFromSubjectDto(SubjectDto subjectDto, @MappingTarget Subject subject);

    Subject createSubjectDtoToSubject(CreateSubjectDto createSubjectDto);

    CreateSubjectDto subjectToCreateSubjectDto(Subject subject);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Subject updateSubjectFromCreateSubjectDto(CreateSubjectDto createSubjectDto, @MappingTarget Subject subject);
}
