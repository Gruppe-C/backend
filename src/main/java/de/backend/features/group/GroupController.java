package de.backend.features.group;

import de.backend.features.group.dto.CreateGroupDto;
import de.backend.features.group.dto.GroupDto;
import de.backend.features.group.dto.GroupDtoMapper;
import de.backend.features.user.User;
import de.backend.features.user.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/group")
@Tag(name = "Group", description = "The Group API")
@PreAuthorize("isAuthenticated()")
@SecurityRequirement(name = "bearerAuth")
public class GroupController {

    private final GroupService groupService;

    private final UserService userService;

    private final GroupDtoMapper mapper;

    @Autowired
    public GroupController(GroupService groupService, UserService userService, GroupDtoMapper mapper) {
        this.groupService = groupService;
        this.userService = userService;
        this.mapper = mapper;
    }

    @GetMapping
    public List<GroupDto> getList(Principal principal) {
        return this.mapper.groupListToGroupDtoList(groupService.getList(userService.getByUsername(principal.getName()).getId()));
    }

    @GetMapping("/{groupId}")
    @PreAuthorize("@groupAccess.canAccess(#groupId)")
    public GroupDto getById(@PathVariable String groupId) {
        Group group = this.groupService.get(groupId);
        return this.mapper.groupToGroupDto(group);
    }

    @PostMapping
    public GroupDto create(Principal principal, @RequestBody CreateGroupDto groupDto) throws IllegalAccessException {
        User user = this.userService.getByUsername(principal.getName());
        Group group = this.mapper.createGroupDtoToGroup(groupDto);
        group.setOwner(user);
        return this.mapper.groupToGroupDto(this.groupService.create(group));
    }

    @PutMapping("/{groupId}")
    @PreAuthorize("@groupAccess.canUpdate(#groupId)")
    public GroupDto update(@PathVariable String groupId, @RequestBody @Valid CreateGroupDto groupDto) throws IllegalAccessException {
        Group group = this.mapper.updateGroupFromCreateGroupDto(groupDto, this.groupService.get(groupId));
        return this.mapper.groupToGroupDto(this.groupService.update(group));
    }
}
