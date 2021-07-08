package com.lucci.webadmin.repository;

import com.lucci.webadmin.domain.ImgUrl;

import com.lucci.webadmin.repository.custom.CustomImgUrlRepository;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ImgUrl entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ImgUrlRepository extends JpaRepository<ImgUrl, Long>, CustomImgUrlRepository {
}
