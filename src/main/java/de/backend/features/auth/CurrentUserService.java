package de.backend.features.auth;

import de.backend.features.user.User;
import de.backend.features.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CurrentUserService {

    private final UserService userService;

    @Autowired
    public CurrentUserService(UserService userService) {
        this.userService = userService;
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || authentication.getAuthorities().stream().map(Object::toString).anyMatch("ROLE_ANONYMOUS"::equals)) {
            return null;
        } else {
            return this.userService.getByUsername(authentication.getName());
        }
    }
}
