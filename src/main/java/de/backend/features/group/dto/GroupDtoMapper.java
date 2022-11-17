package de.backend.features.group.dto;

import de.backend.features.group.Group;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface GroupDtoMapper {
    Group groupDtoToGroup(GroupDto groupDto);

    GroupDto groupToGroupDto(Group group);

    List<GroupDto> groupListToGroupDtoList(List<Group> groupList);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Group updateGroupFromGroupDto(GroupDto groupDto, @MappingTarget Group group);

    Group createGroupDtoToGroup(CreateGroupDto createGroupDto);

    CreateGroupDto groupToCreateGroupDto(Group group);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Group updateGroupFromCreateGroupDto(CreateGroupDto createGroupDto, @MappingTarget Group group);
}
