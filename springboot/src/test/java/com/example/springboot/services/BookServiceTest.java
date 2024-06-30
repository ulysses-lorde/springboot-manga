package com.example.springboot.services;

import com.example.springboot.dtos.BookRecordDto;
import com.example.springboot.models.ArtistModel;
import com.example.springboot.models.AuthorModel;
import com.example.springboot.models.BookModel;
import com.example.springboot.models.CategoryModel;
import com.example.springboot.repositories.BookRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class BookServiceTest {

    @Autowired
    private BookService bookService;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    @Transactional
    public void testCreateBook() {
        // Criar Autor e Artista
        AuthorModel author = new AuthorModel();
        author.setName("Author A");
        entityManager.persist(author);

        ArtistModel artist = new ArtistModel();
        artist.setName("Artist A");
        entityManager.persist(artist);

        // Criar Categorias
        CategoryModel category1 = new CategoryModel();
        category1.setName("Category 1");
        entityManager.persist(category1);

        CategoryModel category2 = new CategoryModel();
        category2.setName("Category 2");
        entityManager.persist(category2);

        Set<CategoryModel> categories = new HashSet<>();
        categories.add(category1);
        categories.add(category2);

        // Preparar DTO
        BookRecordDto bookRecordDto = new BookRecordDto("Spring in Action", null);

        // Executar o método de teste
        BookModel createdBook = bookService.createBook(bookRecordDto);

        // Verificar o resultado
        assertThat(createdBook).isNotNull();
        assertThat(createdBook.getIdBook()).isNotNull();
        assertThat(createdBook.getTitle()).isEqualTo("Spring in Action");
    }
}
