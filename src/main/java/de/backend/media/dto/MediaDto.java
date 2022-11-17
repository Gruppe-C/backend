package de.backend.media.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class MediaDto implements Serializable {

    @Schema(description = "ID of the Media", example = "dfcm5rol01")
    private String id;

    @Schema(description = "The original filename of the media", example = "My media")
    private String name;

    @Schema(description = "The type of the media", example = "image/png")
    private String type;

    @Schema(description = "The url of the media", example = "http://localhost:8080/api/media/vbg23ydg56/file")
    private String url;
}
