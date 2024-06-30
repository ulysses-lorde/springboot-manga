package com.example.springboot.repositories;

import com.example.springboot.models.BookModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BookRepository extends JpaRepository<BookModel, UUID> {
    @Query("SELECT b FROM BookModel b WHERE LOWER(b.title) LIKE LOWER(CONCAT('%', :title, '%'))")
    Page<BookModel> findByPartialTitle(@Param("title") String title, Pageable pagealble);
}
