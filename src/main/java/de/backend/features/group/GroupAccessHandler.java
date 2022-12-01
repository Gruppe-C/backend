package de.backend.features.group;

import de.backend.features.auth.CurrentUserService;
import de.backend.features.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("groupAccess")
public class GroupAccessHandler {

    private final GroupService groupService;

    private final CurrentUserService currentUserService;

    @Autowired
    public GroupAccessHandler(GroupService groupService, CurrentUserService currentUserService) {
        this.groupService = groupService;
        this.currentUserService = currentUserService;
    }

    public boolean canAccess(String groupId) {
        return this.isMemberOfGroup(groupId) || this.isOwnerOfGroup(groupId);
    }

    public boolean canUpdate(String groupId) {
        return this.isOwnerOfGroup(groupId);
    }

    public boolean canDelete(String groupId) {
        return this.isOwnerOfGroup(groupId);
    }

    public boolean canAddMember(String groupId) {
        return this.isOwnerOfGroup(groupId) || this.isMemberOfGroup(groupId);
    }

    public boolean canRemoveMember(String groupId) {
        return this.isOwnerOfGroup(groupId);
    }

    public boolean isOwnerOfGroup(String groupId) {
        return this.getGroup(groupId).getOwner().equals(this.currentUserService.getCurrentUser());
    }

    public boolean isMemberOfGroup(String groupId) {
        return this.getGroup(groupId).getMembers().contains(this.currentUserService.getCurrentUser());
    }

    private Group getGroup(String groupId) {
        return this.groupService.get(groupId);
    }

    private User getUser() {
        return this.currentUserService.getCurrentUser();
    }
}
