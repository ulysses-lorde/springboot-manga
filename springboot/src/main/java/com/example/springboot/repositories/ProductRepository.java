package com.example.springboot.repositories;

import com.example.springboot.dtos.ProductRecordDto;
import com.example.springboot.models.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<ProductModel, UUID>, PagingAndSortingRepository<ProductModel, UUID> {
    @Query("SELECT p FROM ProductModel p WHERE LOWER(p.name) LIKE LOWER(concat('%', :name, '%'))")
    List<ProductModel> findByPartialName(@Param("name") String name);
}
