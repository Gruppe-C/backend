package de.backend.media.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
public class FileMedia extends Media {

    private String path;

    public FileMedia() {
    }

    public FileMedia(String name, String type, String path) {
        super(name, type);
        this.path = path;
    }
}
