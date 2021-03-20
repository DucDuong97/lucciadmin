package com.lucci.webadmin.repository;

import com.lucci.webadmin.domain.SingletonContent;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the SingletonContent entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SingletonContentRepository extends JpaRepository<SingletonContent, Long> {
}
