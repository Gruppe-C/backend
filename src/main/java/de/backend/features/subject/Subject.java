package de.backend.features.subject;

import de.backend.features.schoolyear.SchoolYear;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name = "`subject`")
public final class Subject {
    @Id
    @GenericGenerator(name = "id", strategy = "de.backend.IdGenerator")
    @GeneratedValue(generator = "id")
    private String id;

    private String name;

    private String color;

    @CreationTimestamp
    private Timestamp createdAt;

    @ManyToOne
    private SchoolYear schoolYear;
}
