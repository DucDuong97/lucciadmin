package com.lucci.webadmin.repository;

import com.lucci.webadmin.domain.ImgUrl;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

/**
 * Spring Data  repository for the ImgUrl entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ImgUrlRepository extends JpaRepository<ImgUrl, Long> {

    List<ImgUrl> findByPathAndName(String path, String name);
    List<ImgUrl> findByTreatmentsId(Long treatmentId);

    List<ImgUrl> findByServiceItemsId(Long serviceId);
}
