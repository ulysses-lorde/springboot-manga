package com.example.springboot.repositories;

import com.example.springboot.models.ChapterModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ChapterRepository extends JpaRepository<ChapterModel, UUID> {
}
