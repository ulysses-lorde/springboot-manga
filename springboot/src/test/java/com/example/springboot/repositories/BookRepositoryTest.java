package com.example.springboot.repositories;

import com.example.springboot.models.ArtistModel;
import com.example.springboot.models.AuthorModel;
import com.example.springboot.models.BookModel;
import com.example.springboot.models.CategoryModel;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    @Transactional
    public void testFindByPartialTitle() {
        // Preparar dados de teste

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

        // Criar Livros
        BookModel book1 = new BookModel();
        book1.setTitle("Spring Boot Guide");
        book1.setType(BookModel.Type.LIGHT_NOVEL);
        book1.setReleaseDate(new Date());
        book1.setStatus(BookModel.Status.EM_CURSO);
        book1.setAuthor(author);
        book1.setArtist(artist);
        book1.setCategories(categories);
        bookRepository.save(book1);

        BookModel book2 = new BookModel();
        book2.setTitle("Java Programming");
        book2.setType(BookModel.Type.LIGHT_NOVEL);
        book2.setReleaseDate(new Date());
        book2.setStatus(BookModel.Status.FINALIZADO);
        book2.setAuthor(author);
        book2.setArtist(artist);
        book2.setCategories(categories);
        bookRepository.save(book2);

        BookModel book3 = new BookModel();
        book3.setTitle("Spring Framework");
        book3.setType(BookModel.Type.LIGHT_NOVEL);
        book3.setReleaseDate(new Date());
        book3.setStatus(BookModel.Status.EM_CURSO);
        book3.setAuthor(author);
        book3.setArtist(artist);
        book3.setCategories(categories);
        bookRepository.save(book3);

        Pageable pageable = PageRequest.of(0, 10);

        // Executar o método de teste
        Page<BookModel> result = bookRepository.findByPartialTitle("spring", pageable);

        // Verificar o resultado
        assertThat(result.getTotalElements()).isEqualTo(2);
        assertThat(result.getContent()).extracting("title").containsExactlyInAnyOrder("Spring Boot Guide", "Spring Framework");
    }
}