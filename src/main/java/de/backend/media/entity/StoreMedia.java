package de.backend.media.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Lob;

@Entity
@Getter
@Setter
public class StoreMedia extends Media {

    @Lob
    private byte[] data;

    public StoreMedia() {
    }

    public StoreMedia(String name, String type, byte[] data, Long size) {
        super(name, type, size);
        this.data = data;
    }
}
