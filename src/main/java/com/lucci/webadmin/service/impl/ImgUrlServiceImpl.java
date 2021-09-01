package com.lucci.webadmin.service.impl;

import com.lucci.webadmin.domain.Treatment;
import com.lucci.webadmin.service.FileStoreService;
import com.lucci.webadmin.service.ImgUrlService;
import com.lucci.webadmin.domain.ImgUrl;
import com.lucci.webadmin.repository.ImgUrlRepository;
import com.lucci.webadmin.service.TreatmentService;
import com.lucci.webadmin.service.dto.ImgUrlDTO;
import com.lucci.webadmin.service.mapper.ImgUrlMapper;
import org.apache.http.entity.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Value;
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

    private final Logger log = LoggerFactory.getLogger(ImgUrlServiceImpl.class);

    private final TreatmentService treatmentService;

    private final ImgUrlRepository imgUrlRepository;

    private final ImgUrlMapper imgUrlMapper;

    private final FileStoreService fileStoreService;

    public ImgUrlServiceImpl(TreatmentService treatmentService, ImgUrlRepository imgUrlRepository, ImgUrlMapper imgUrlMapper, FileStoreService fileStoreService) {
        this.treatmentService = treatmentService;
        this.imgUrlRepository = imgUrlRepository;
        this.imgUrlMapper = imgUrlMapper;
        this.fileStoreService = fileStoreService;
    }

    @Override
    public ImgUrlDTO save(ImgUrlDTO imgUrlDTO) {
        log.debug("Request to save ImgUrl : {}", imgUrlDTO);
        ImgUrl imgUrl = imgUrlMapper.toEntity(imgUrlDTO);
        imgUrl = imgUrlRepository.save(imgUrl);
        imgUrl.addTreatments();
        return imgUrlMapper.toDto(imgUrl);
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
        ImgUrl imgUrl = imgUrlRepository.findById(id).get();
        imgUrl.clearTreatments();
        fileStoreService.delete(bucket, imgUrl.createAccessKey());
        imgUrlRepository.deleteById(id);
    }

    @Override
    public List<ImgUrlDTO> findByTreatmentId(Long treatmentId) {
        log.debug("Request to get all ImgUrls of Treatment {}", treatmentId);
        return imgUrlRepository.findByTreatmentsId(treatmentId).stream()
            .map(imgUrlMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public List<ImgUrlDTO> findByServiceId(Long serviceId) {
        log.debug("Request to get all ImgUrls of Service {}", serviceId);
        return imgUrlRepository.findByServiceItemsId(serviceId).stream()
            .map(imgUrlMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Value("${file-store-bucket-name}")
    private String bucket;

    private static final List<String> IMAGE_TYPES = Stream.of(IMAGE_PNG,IMAGE_BMP,IMAGE_GIF,IMAGE_JPEG)
                                                    .map(ContentType::getMimeType)
                                                    .collect(Collectors.toList());

    private static final String DEFAULT_PATH = "images";

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

        String fileName = validFileName(DEFAULT_PATH, file.getOriginalFilename());
        ImgUrlDTO imgUrlDTO = new ImgUrlDTO();
        imgUrlDTO.setName(fileName);
        imgUrlDTO.setPath(DEFAULT_PATH);
        //Save Image in S3 and then save ImgUrl in the database
        try {
            fileStoreService.upload(bucket, imgUrlDTO.createAccessKey(), metadata, file.getInputStream());
        } catch (IOException e) {
            throw new IllegalStateException("Failed to upload file", e);
        }
        return imgUrlDTO;
    }

    private String validFileName(String path, String fileName) {
        String result = fileName;
        for (long duplicate = 0; !imgUrlRepository.findByPathAndName(path, result).isEmpty(); duplicate++) {
            result = String.format("(%d)%s", duplicate, fileName);
        }
        return result;
    }

    @Override
    public Optional<ImgUrlDTO> findByURL(String url) {
        List<String> split = Arrays.asList(url.split("/"));
        if (split.size() < 2) {
            return Optional.empty();
        }
        String name = split.get(split.size() - 1);
        String path = String.join("/", split.subList(0, split.size() - 1));
        try {
            return Optional.of(imgUrlMapper.toDto(imgUrlRepository.findByPathAndName(path, name).get(0)));
        } catch (IndexOutOfBoundsException ignored) {
            return Optional.empty();
        }
    }
}
