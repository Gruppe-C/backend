package de.backend.features.group;

import de.backend.features.user.User;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "`group`")
public class Group {

    @Id
    @GenericGenerator(name = "id", strategy = "de.backend.IdGenerator")
    @GeneratedValue(generator = "id")
    private String id;

    private String name;

    private String color;

    @CreationTimestamp
    private Timestamp createdAt;

    @ManyToOne
    private User owner;

    @ManyToMany
    @JoinTable(
            name = "group_members",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "member_id")
    )
    private Set<User> members = new HashSet<>();
}
