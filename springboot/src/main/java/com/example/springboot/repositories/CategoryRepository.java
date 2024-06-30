package com.example.springboot.repositories;

import com.example.springboot.models.CategoryModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CategoryRepository extends JpaRepository<CategoryModel, UUID> {
}
