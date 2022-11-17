package de.backend.media.service;

import de.backend.exception.EntityNotFoundException;
import de.backend.media.MediaRepository;
import de.backend.media.entity.FileMedia;
import de.backend.media.entity.Media;
import de.backend.media.entity.StoreMedia;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

@Service
public class MediaService {

    private final MediaRepository repository;

    @Autowired
    public MediaService(MediaRepository repository) {
        this.repository = repository;
    }

    public StoreMedia upload(MultipartFile file) throws IOException {
        StoreMedia media = new StoreMedia(file.getOriginalFilename(), file.getContentType(), this.compressImage(file.getBytes()));
        return this.repository.save(media);
    }

    public FileMedia upload(MultipartFile file, String dir) throws IOException {
        String path = "store/" + dir + file.getOriginalFilename();

        FileMedia media = new FileMedia(file.getOriginalFilename(), file.getContentType(), path);
        Path uploadPath = Paths.get("store", dir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
            Path filePath = uploadPath.resolve(Objects.requireNonNull(file.getOriginalFilename()));
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {
            throw new IOException("Could not save file");
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
        return this.repository.save(media);
    }

    public Media get(String id) {
        return this.repository.findById(id).orElseThrow(EntityNotFoundException::new);
    }


    public byte[] getMedia(Media media) throws EntityNotFoundException {
        byte[] bytes;
        if (media instanceof StoreMedia) {
            bytes = this.decompressImage(((StoreMedia) media).getData());
        } else {
            try {
                bytes = Files.readAllBytes(new File(((FileMedia) media).getPath()).toPath());
            } catch (IOException e) {
                throw new EntityNotFoundException("Media not found");
            }
        }
        return bytes;
    }


    private byte[] compressImage(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setLevel(Deflater.BEST_COMPRESSION);
        deflater.setInput(data);
        deflater.finish();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] tmp = new byte[4 * 1024];
        while (!deflater.finished()) {
            int size = deflater.deflate(tmp);
            outputStream.write(tmp, 0, size);
        }
        try {
            outputStream.close();
        } catch (Exception ignored) {
        }
        return outputStream.toByteArray();
    }


    private byte[] decompressImage(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] tmp = new byte[4 * 1024];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(tmp);
                outputStream.write(tmp, 0, count);
            }
            outputStream.close();
        } catch (Exception ignored) {
        }
        return outputStream.toByteArray();
    }


}
