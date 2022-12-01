package de.backend.features.file;

import de.backend.features.subject.Subject;
import de.backend.features.user.User;
import de.backend.media.entity.Media;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
public class File {

    @Id
    @GenericGenerator(name = "id", strategy = "de.backend.IdGenerator")
    @GeneratedValue(generator = "id")
    private String id;

    private String name;

    @CreationTimestamp
    private Timestamp createdAt;

    @ManyToOne
    private Media media;

    @ManyToOne
    private User owner;

    @ManyToOne
    private Subject subject;

    // TODO: Add Relation to Category


    public File(String name, User owner, Subject subject) {
        this.name = name;
        this.owner = owner;
        this.subject = subject;
    }

    public File() {
    }
}
