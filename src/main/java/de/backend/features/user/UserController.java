package de.backend.features.user;

import de.backend.features.user.dto.UserDto;
import de.backend.features.user.dto.UserDtoMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("/user")
@Tag(name = "User", description = "User API")
public class UserController {

    private final UserService userService;

    private final UserDtoMapper userDtoMapper;

    @Autowired
    public UserController(UserService userService, UserDtoMapper userDtoMapper) {
        this.userService = userService;
        this.userDtoMapper = userDtoMapper;
    }

    @GetMapping
    public List<UserDto> getList() {
        return this.userDtoMapper.usersToUserDtos(this.userService.getList());
    }
}
