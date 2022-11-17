package de.backend.features.user.dto;

import de.backend.features.user.User;
import de.backend.media.controller.MediaController;
import org.mapstruct.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public abstract class UserDtoMapper {
    public abstract User userDtoToUser(UserDto userDto);

    public abstract List<UserDto> usersToUserDtos(List<User> users);

    public abstract UserDto userToUserDto(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract User updateUserFromUserDto(UserDto userDto, @MappingTarget User user);

    public abstract User updateUserDtoToUser(UpdateUserDto updateUserDto);

    public abstract UpdateUserDto userToUpdateUserDto(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract User updateUserFromUpdateUserDto(UpdateUserDto updateUserDto, @MappingTarget User user);

    @AfterMapping
    protected void afterMapping(@MappingTarget UserDto userDto) {
        if (userDto.image() != null) {
            userDto.image().setUrl(MvcUriComponentsBuilder
                    .fromMethodName(MediaController.class, "getFile", userDto.image().getId()).build().toString());
        }
    }
}
