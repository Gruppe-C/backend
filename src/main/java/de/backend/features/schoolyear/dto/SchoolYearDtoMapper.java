package de.backend.features.schoolyear.dto;

import de.backend.features.schoolyear.SchoolYear;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface SchoolYearDtoMapper {
    @Mapping(source = "groupId", target = "group.id")
    SchoolYear schoolYearDtoToSchoolYear(SchoolYearDto schoolYearDto);

    @Mapping(source = "group.id", target = "groupId")
    SchoolYearDto schoolYearToSchoolYearDto(SchoolYear schoolYear);

    @Mapping(source = "groupId", target = "group.id")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    SchoolYear updateSchoolYearFromSchoolYearDto(SchoolYearDto schoolYearDto, @MappingTarget SchoolYear schoolYear);
}
