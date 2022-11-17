package de.backend.features.group.dto;

import de.backend.features.group.Group;
import de.backend.features.user.dto.UserDto;
import de.backend.media.controller.MediaController;
import org.mapstruct.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public abstract class GroupDtoMapper {
    public abstract Group groupDtoToGroup(GroupDto groupDto);

    public abstract GroupDto groupToGroupDto(Group group);

    public abstract List<GroupDto> groupListToGroupDtoList(List<Group> groupList);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract Group updateGroupFromGroupDto(GroupDto groupDto, @MappingTarget Group group);

    public abstract Group createGroupDtoToGroup(CreateGroupDto createGroupDto);

    public abstract CreateGroupDto groupToCreateGroupDto(Group group);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract Group updateGroupFromCreateGroupDto(CreateGroupDto createGroupDto, @MappingTarget Group group);

    @AfterMapping
    protected void afterMapping(@MappingTarget GroupDto groupDto) {
        for(UserDto userDto : groupDto.members()) {
            if (userDto.image() != null) {
                userDto.image().setUrl(MvcUriComponentsBuilder
                        .fromMethodName(MediaController.class, "getFile", userDto.image().getId()).build().toString());
            }
        }

        if (groupDto.owner().image() != null) {
            groupDto.owner().image().setUrl(MvcUriComponentsBuilder
                    .fromMethodName(MediaController.class, "getFile", groupDto.owner().image().getId()).build().toString());
        }
    }
}
