package com.lucci.webadmin.repository;

import com.lucci.webadmin.domain.Blog;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CustomBlogRepository {
    @Query(value = "SELECT * FROM blog WHERE service_item_id=?1", nativeQuery = true)
    List<Blog> findAllByServiceId(Long id);
}
