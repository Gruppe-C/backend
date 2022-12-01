package de.backend.media.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import java.sql.Timestamp;

@Getter
@Setter
@javax.persistence.Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Media {

    @Id
    @GenericGenerator(name = "id", strategy = "de.backend.IdGenerator")
    @GeneratedValue(generator = "id")
    private String id;

    @CreationTimestamp
    private Timestamp createdAt;

    private String name;

    private String type;

    private Long size;

    public Media() {
    }

    public Media(String name, String type, Long size) {
        this.name = name;
        this.type = type;
        this.size = size;
    }
}
