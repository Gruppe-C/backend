package de.backend.features.user;

import de.backend.features.user.dto.UpdateUserDto;
import de.backend.features.user.dto.UserDto;
import de.backend.features.user.dto.UserDtoMapper;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/user")
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

    @GetMapping("/{username}")
    public UserDto getByUsername(@PathVariable String username) {
        return this.userDtoMapper.userToUserDto(this.userService.getByUsername(username));
    }

    @PutMapping
    public UserDto update(@RequestBody UpdateUserDto userDto, Principal principal) {
        User user = this.userService.getByUsername(principal.getName());
        if (userDto.displayName() != null) {
            user.setDisplayName(userDto.displayName());
            User result = this.userService.update(user);
            return this.userDtoMapper.userToUserDto(result);
        } else {
            return this.userDtoMapper.userToUserDto(user);
        }
    }

    @PostMapping("/image")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserDto> uploadImage(@RequestParam("file") MultipartFile file, Principal principal) throws IOException {
        User user = userService.getByUsername(principal.getName());
        if (Objects.requireNonNull(Objects.requireNonNull(file.getContentType())).startsWith("image/")) {
            User result = userService.uploadImage(file, user);
            return ResponseEntity.ok(this.userDtoMapper.userToUserDto(result));
        } else {
            throw new IllegalArgumentException("Cloud not upload file, please try again!");
        }
    }
}
