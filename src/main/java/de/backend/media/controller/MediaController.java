package de.backend.media.controller;

import de.backend.media.dto.MediaDto;
import de.backend.media.dto.MediaDtoMapper;
import de.backend.media.entity.Media;
import de.backend.media.service.MediaService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/media")
@Tag(name = "Media", description = "The Media API")
public class MediaController {

    private final MediaService mediaService;

    private final MediaDtoMapper mediaDtoMapper;

    @Autowired
    public MediaController(MediaService mediaService, MediaDtoMapper mediaDtoMapper) {
        this.mediaService = mediaService;
        this.mediaDtoMapper = mediaDtoMapper;
    }

    @GetMapping("/{id}")
    public MediaDto get(@PathVariable String id) {
        return this.mediaDtoMapper.mediaToMediaDto(this.mediaService.get(id));
    }

    @GetMapping("/{id}/file")
    public ResponseEntity<byte[]> getFile(@PathVariable String id) {
        Media media = this.mediaService.get(id);
        byte[] file = this.mediaService.getMedia(media);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf(media.getType())).body(file);
    }
}
