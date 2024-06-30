package com.example.springboot.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "artists")
public class ArtistModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID idArtist;
    @NotBlank
    @Column(nullable = false)
    private String name;

    public UUID getIdArtist() {
        return idArtist;
    }

    public void setIdArtist(UUID idArtist) {
        this.idArtist = idArtist;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
