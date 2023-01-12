package de.backend.features.file.dto;

import de.backend.features.file.File;
import de.backend.media.controller.MediaController;
import de.backend.media.dto.MediaDto;
import org.mapstruct.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public abstract class FileDtoMapper {

    @AfterMapping
    protected void afterMapping(@MappingTarget FileDto fileDto) {
        if (fileDto.media() != null) {
            fileDto.media().setUrl(MvcUriComponentsBuilder
                    .fromMethodName(MediaController.class, "getFile", fileDto.media().getId()).build().toString());
        }
        if (fileDto.owner().image() != null) {
            fileDto.owner().image().setUrl(MvcUriComponentsBuilder
                    .fromMethodName(MediaController.class, "getFile", fileDto.owner().image().getId()).build().toString());
        }
    }

    @Mapping(source = "subjectId", target = "subject.id")
    public  abstract File fileDtoToFile(FileDto fileDto);

    @InheritInverseConfiguration(name = "fileDtoToFile")
    public abstract FileDto fileToFileDto(File file);

    @InheritConfiguration(name = "fileDtoToFile")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract File updateFileFromFileDto(FileDto fileDto, @MappingTarget File file);
}
