package com.example.springboot.dtos;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record BookRecordDto(@NotBlank String title, UUID id) {
}
