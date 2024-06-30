package com.example.springboot.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "categories")
public class CategoryModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID idCategory;
    @NotBlank
    @Column(nullable = false, unique = true)
    private String name;
    @ManyToMany (mappedBy = "categories")
    private Set<BookModel> books;

    public UUID getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(UUID idCategory) {
        this.idCategory = idCategory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
