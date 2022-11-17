package de.backend.media.dto;

import de.backend.media.controller.MediaController;
import de.backend.media.entity.Media;
import org.mapstruct.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public abstract class MediaDtoMapper {

    public abstract Media mediaDtoToMedia(MediaDto mediaDto);

    public abstract MediaDto mediaToMediaDto(Media media);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract Media updateMediaFromMediaDto(MediaDto mediaDto, @MappingTarget Media media);

    @AfterMapping
    protected void afterMapping(@MappingTarget MediaDto mediaDto) {
        mediaDto.setUrl(MvcUriComponentsBuilder
                .fromMethodName(MediaController.class, "getFile", mediaDto.getId()).build().toString());
    }
}
