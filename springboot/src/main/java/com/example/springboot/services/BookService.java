package com.example.springboot.services;

import com.example.springboot.controllers.BookController;
import com.example.springboot.dtos.BookRecordDto;
import com.example.springboot.models.BookModel;
import com.example.springboot.repositories.BookRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    public BookModel createBook(@Valid BookRecordDto bookRecordDto) {
        var bookModel = new BookModel();
        BeanUtils.copyProperties(bookRecordDto, bookModel);
        return bookRepository.save(bookModel);
    }

    public Page<BookModel> listBooks(Pageable pageable) {
        Page<BookModel> bookPage = bookRepository.findAll(pageable);
        bookPage.forEach(this::addBookLinkList);
        return bookPage;
    }

    public Page<BookModel> findByTitle(String title, Pageable pageable) {
        Page<BookModel> bookPage = bookRepository.findByPartialTitle(title, pageable);
        bookPage.forEach(this::addBookLinkList);
        return bookPage;
    }

    public ResponseEntity<Object> findOneById(UUID id) {
        Optional<BookModel> book = bookRepository.findById(id);
        if (book.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found.");
        }
        book.get().add(linkTo(methodOn(BookController.class).getAllBooks(null)).withRel("Book List"));
        return ResponseEntity.status(HttpStatus.OK).body(book.get());
    }

    public void addBookLinkList(BookModel book) {
        book.add(linkTo(methodOn(BookController.class).getAllBooks(null)).withRel("Book List"));
    }

    public ResponseEntity<Object> updateBook(UUID id, @Valid BookRecordDto bookRecordDto) {
        Optional<BookModel> bookOptional = bookRepository.findById(id);

        if (bookOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found.");
        }

        BookModel book = bookOptional.get();
        BeanUtils.copyProperties(bookRecordDto, book);
        return ResponseEntity.status(HttpStatus.OK).body(bookRepository.save(book));
    }


    public ResponseEntity<Object> deleteBook(UUID id) {
        return bookRepository.findById(id)
                .map(book -> {
                    bookRepository.delete(book);
                    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found."));
    }
}
