package com.lucci.webadmin.service.impl;

import com.lucci.webadmin.config.BucketName;
import com.lucci.webadmin.service.FileStore;
import com.lucci.webadmin.service.ImgUrlService;
import com.lucci.webadmin.domain.ImgUrl;
import com.lucci.webadmin.repository.ImgUrlRepository;
import com.lucci.webadmin.service.dto.ImgUrlDTO;
import com.lucci.webadmin.service.mapper.ImgUrlMapper;
import org.apache.http.entity.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.apache.http.entity.ContentType.*;
import static org.apache.http.entity.ContentType.IMAGE_JPEG;

/**
 * Service Implementation for managing {@link ImgUrl}.
 */
@Service
@Transactional
public class ImgUrlServiceImpl implements ImgUrlService {

    private static final List<String> IMAGE_TYPES =
        Stream.of(IMAGE_PNG,IMAGE_BMP,IMAGE_GIF,IMAGE_JPEG)
            .map(ContentType::getMimeType)
            .collect(Collectors.toList());

    private static final String folderName = "images";

    private final Logger log = LoggerFactory.getLogger(ImgUrlServiceImpl.class);

    private final ImgUrlRepository imgUrlRepository;
    private final FileStore fileStore;

    private final ImgUrlMapper imgUrlMapper;

    public ImgUrlServiceImpl(ImgUrlRepository imgUrlRepository, FileStore fileStore, ImgUrlMapper imgUrlMapper) {
        this.imgUrlRepository = imgUrlRepository;
        this.fileStore = fileStore;
        this.imgUrlMapper = imgUrlMapper;
    }

    @Override
    public ImgUrlDTO save(ImgUrlDTO imgUrlDTO) {
        log.debug("Request to save ImgUrl : {}", imgUrlDTO);
        ImgUrl imgUrl = imgUrlMapper.toEntity(imgUrlDTO);
        imgUrl = imgUrlRepository.save(imgUrl);
        return imgUrlMapper.toDto(imgUrl);
    }

    @Override
    public ImgUrlDTO upload(MultipartFile file) {
        //check if the file is empty
        if (file.isEmpty()) {
            throw new IllegalStateException("Cannot upload empty file");
        }
        //Check if the file is an image
        if (!IMAGE_TYPES.contains(file.getContentType())) {
            throw new IllegalStateException("File uploaded is not an image");
        }
        //get file metadata
        Map<String, String> metadata = new HashMap<>();
        metadata.put("Content-Type", file.getContentType());
        metadata.put("Content-Length", String.valueOf(file.getSize()));
        //Save Image in S3 and then save ImgUrl in the database
        String path = String.format("%s/%s", BucketName.IMAGE.getBucketName(), folderName);
        String fileName = file.getOriginalFilename();
        try {
            fileStore.upload(path, fileName, Optional.of(metadata), file.getInputStream());
        } catch (IOException e) {
            throw new IllegalStateException("Failed to upload file", e);
        }
        ImgUrlDTO newImg = new ImgUrlDTO();
        newImg.setName(fileName);
        newImg.setPath(folderName);
        return newImg;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ImgUrlDTO> findAll() {
        log.debug("Request to get all ImgUrls");
        return imgUrlRepository.findAll().stream()
            .map(imgUrlMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<ImgUrlDTO> findOne(Long id) {
        log.debug("Request to get ImgUrl : {}", id);
        return imgUrlRepository.findById(id)
            .map(imgUrlMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ImgUrl : {}", id);
        Optional<ImgUrlDTO> imgUrlOpt = findOne(id);
        if (!imgUrlOpt.isPresent()) {
            log.debug("ImgUrl {} does not exist", id);
            return;
        }
        fileStore.delete(BucketName.IMAGE.getBucketName(), imgUrlOpt.get().createAccessKey());
        imgUrlRepository.deleteById(id);
    }
}
