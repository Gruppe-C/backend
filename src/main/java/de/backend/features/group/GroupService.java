package de.backend.features.group;

import de.backend.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupService {

    private final GroupRepository repository;

    @Autowired
    public GroupService(GroupRepository repository) {
        this.repository = repository;
    }

    public Group create(Group group) {
        return repository.save(group);
    }

    public Group get(String id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Group not found"));
    }

    public List<Group> getList(String userId) {
        return repository.findDistinctByOwnerIdOrMembersIdContains(userId, userId);
    }

    public Group update(Group group) {
        return repository.save(group);
    }

    public void delete(String id) {
        repository.deleteById(id);
    }
}
