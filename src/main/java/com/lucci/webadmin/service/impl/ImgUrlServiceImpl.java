package com.lucci.webadmin.service.impl;

import com.lucci.webadmin.config.BucketName;
import com.lucci.webadmin.service.FileStore;
import com.lucci.webadmin.service.ImgUrlService;
import com.lucci.webadmin.domain.ImgUrl;
import com.lucci.webadmin.repository.ImgUrlRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

import static org.apache.http.entity.ContentType.*;

/**
 * Service Implementation for managing {@link ImgUrl}.
 */
@Service
@Transactional
public class ImgUrlServiceImpl implements ImgUrlService {

    private final Logger log = LoggerFactory.getLogger(ImgUrlServiceImpl.class);

    private final ImgUrlRepository imgUrlRepository;
    private final FileStore fileStore;

    public ImgUrlServiceImpl(ImgUrlRepository imgUrlRepository, FileStore fileStore) {
        this.imgUrlRepository = imgUrlRepository;
        this.fileStore = fileStore;
    }

    @Override
    public ImgUrl save(ImgUrl imgUrl) {
        log.debug("Request to save ImgUrl : {}", imgUrl);
        return imgUrlRepository.save(imgUrl);
    }

    @Override
    public ImgUrl upload(MultipartFile file) {
        //check if the file is empty
        if (file.isEmpty()) {
            throw new IllegalStateException("Cannot upload empty file");
        }
        //Check if the file is an image
        if (!Arrays.asList(IMAGE_PNG.getMimeType(),
            IMAGE_BMP.getMimeType(),
            IMAGE_GIF.getMimeType(),
            IMAGE_JPEG.getMimeType()).contains(file.getContentType())) {
            throw new IllegalStateException("File uploaded is not an image");
        }
        //get file metadata
        Map<String, String> metadata = new HashMap<>();
        metadata.put("Content-Type", file.getContentType());
        metadata.put("Content-Length", String.valueOf(file.getSize()));
        //Save Image in S3 and then save ImgUrl in the database
        String path = String.format("%s/%s", BucketName.IMAGE.getBucketName(), "images");
        String fileName = String.format("%s", file.getOriginalFilename());
        try {
            fileStore.upload(path, fileName, Optional.of(metadata), file.getInputStream());
        } catch (IOException e) {
            throw new IllegalStateException("Failed to upload file", e);
        }
        ImgUrl newImg = new ImgUrl();
        newImg.setImgUrl(String.format("%s/%s", path, fileName));
        return this.save(newImg);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ImgUrl> findAll() {
        log.debug("Request to get all ImgUrls");
        return imgUrlRepository.findAll();
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<ImgUrl> findOne(Long id) {
        log.debug("Request to get ImgUrl : {}", id);
        return imgUrlRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ImgUrl : {}", id);
        Optional<ImgUrl> imgUrlOpt = findOne(id);
        if (!imgUrlOpt.isPresent()) {
            return;
        }
        String[] split = imgUrlOpt.get().getImgUrl().split("/", 2);
        if (split.length < 2 || !split[0].equals(BucketName.IMAGE.getBucketName())) {
            throw new IllegalStateException("image url has incorrect format");
        }
        fileStore.delete(split[0], split[1]);
        imgUrlRepository.deleteById(id);
    }
}
