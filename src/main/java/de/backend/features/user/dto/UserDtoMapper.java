package de.backend.features.user.dto;

import de.backend.features.user.User;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface UserDtoMapper {
    User userDtoToUser(UserDto userDto);

    List<UserDto> usersToUserDtos(List<User> users);

    UserDto userToUserDto(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User updateUserFromUserDto(UserDto userDto, @MappingTarget User user);

    User updateUserDtoToUser(UpdateUserDto updateUserDto);

    UpdateUserDto userToUpdateUserDto(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User updateUserFromUpdateUserDto(UpdateUserDto updateUserDto, @MappingTarget User user);
}
