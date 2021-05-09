package com.lucci.webadmin.repository;

import com.lucci.webadmin.domain.Video;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Video entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {
}
