package com.lucci.webadmin.service.impl;

import com.lucci.webadmin.service.FileStoreService;
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

    private static final String DEFAULT_PATH = "images";

    private final Logger log = LoggerFactory.getLogger(ImgUrlServiceImpl.class);

    private final ImgUrlRepository imgUrlRepository;
    private final FileStoreService fileStoreService;

    private final ImgUrlMapper imgUrlMapper;

    public ImgUrlServiceImpl(ImgUrlRepository imgUrlRepository, FileStoreService fileStoreService, ImgUrlMapper imgUrlMapper) {
        this.imgUrlRepository = imgUrlRepository;
        this.fileStoreService = fileStoreService;
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
        if (file.isEmpty()) {
            throw new IllegalStateException("Cannot upload empty file");
        }
        if (!IMAGE_TYPES.contains(file.getContentType())) {
            throw new IllegalStateException("File uploaded is not an image");
        }
        Map<String, String> metadata = new HashMap<>();
        metadata.put("Content-Type", file.getContentType());
        metadata.put("Content-Length", String.valueOf(file.getSize()));

        ImgUrlDTO imgUrlDTO = new ImgUrlDTO();
        imgUrlDTO.setName(file.getOriginalFilename());
        imgUrlDTO.setPath(DEFAULT_PATH);
        //Save Image in S3 and then save ImgUrl in the database
        try {
            fileStoreService.upload(imgUrlDTO.createAccessKey(), metadata, file.getInputStream());
        } catch (IOException e) {
            throw new IllegalStateException("Failed to upload file", e);
        }
        return imgUrlDTO;
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
        fileStoreService.delete(imgUrlOpt.get().createAccessKey());
        imgUrlRepository.deleteById(id);
    }
}
