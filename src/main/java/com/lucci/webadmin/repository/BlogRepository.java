package com.lucci.webadmin.repository;

import com.lucci.webadmin.domain.Blog;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Blog entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BlogRepository extends JpaRepository<Blog, Long>, CustomBlogRepository {

    List<Blog> findByServiceItemId(Long serviceId);
}
