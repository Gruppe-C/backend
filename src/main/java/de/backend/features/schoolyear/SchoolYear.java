package de.backend.features.schoolyear;

import de.backend.features.group.Group;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.sql.Timestamp;
import java.time.Year;

@Entity
@Getter
@Setter
public class SchoolYear {

    @Id
    @GenericGenerator(name = "id", strategy = "de.backend.IdGenerator")
    @GeneratedValue(generator = "id")
    private String id;

    @CreationTimestamp
    private Timestamp createdAt;

    private int startYear;

    private int endYear;

    @ManyToOne
    private Group group;

    public SchoolYear(int startYear, int endYear, Group group) {
        this.startYear = startYear;
        this.endYear = endYear;
        this.group = group;
    }

    public SchoolYear() {
    }
}
