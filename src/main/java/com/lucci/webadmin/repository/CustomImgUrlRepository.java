package com.lucci.webadmin.repository;

import com.lucci.webadmin.domain.ImgUrl;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CustomImgUrlRepository {
    @Query(value = "SELECT * FROM img_url WHERE patient_id=?1", nativeQuery = true)
    List<ImgUrl> findByPatientId(Long patientId);

    @Query(value = "SELECT * FROM img_url WHERE img_url=?1", nativeQuery = true)
    Optional<ImgUrl> findByURL(String url);
}
