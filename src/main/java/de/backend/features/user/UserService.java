package de.backend.features.user;

import de.backend.exception.EntityNotFoundException;
import de.backend.media.entity.StoreMedia;
import de.backend.media.service.MediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository repository;

    private final MediaService mediaService;

    @Autowired
    public UserService(UserRepository repository, MediaService mediaService) {
        this.repository = repository;
        this.mediaService = mediaService;
    }

    public User get(String id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found with id " + id));
    }

    public User getByUsername(String username) throws UsernameNotFoundException {
        return this.repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username " + username));
    }

    public List<User> getList() {
        return repository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username " + username));
    }

    public User update(User user) {
        return this.repository.saveAndFlush(user);
    }

    public User uploadImage(@NonNull MultipartFile file, @NonNull User user) throws IOException {
        StoreMedia media = this.mediaService.upload(file);
        user.setImage(media);
        return this.repository.save(user);
    }
}
