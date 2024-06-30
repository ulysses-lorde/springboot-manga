package com.example.springboot.controllers;

import com.example.springboot.dtos.BookRecordDto;
import com.example.springboot.models.BookModel;
import com.example.springboot.repositories.BookRepository;
import com.example.springboot.services.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
public class BookController {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private BookService bookService;

    @PostMapping("/book")
    public ResponseEntity<BookModel> saveBook(@RequestBody @Valid BookRecordDto bookRecordDto) {
        BookModel bookModel = bookService.createBook(bookRecordDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(bookModel);
    }

    @GetMapping("/books")
    public ResponseEntity<Page<BookModel>> getAllBooks(@PageableDefault(size = 3) Pageable pageable) {
        Page<BookModel> bookPage = bookService.listBooks(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(bookPage);
    }

    @GetMapping("/books/{id}")
    public ResponseEntity<Object> getOneBook(@PathVariable(value="id") UUID id) {
        return bookService.findOneById(id);
    }

    @GetMapping("/books/name")
    public ResponseEntity<Page<BookModel>> findByName(
            @RequestParam("name") String title,
            @PageableDefault(size = 3) Pageable pageable
    ) {
        Page<BookModel> bookPage = bookService.findByTitle(title, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(bookPage);
    }

    @PutMapping("/books/{id}")
    public ResponseEntity<Object> updateBook(
            @PathVariable(value="id") UUID id,
            @RequestBody @Valid BookRecordDto bookRecordDto
    ) {
        return bookService.updateBook(id, bookRecordDto);
    }

    @DeleteMapping("/books/{id}")
    public ResponseEntity<Object> deleteBook(@PathVariable(value="id") UUID id) {
        Optional<BookModel> book0 = bookRepository.findById(id);
        if (book0.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found.");
        }
        bookRepository.delete(book0.get());
        return ResponseEntity.status(HttpStatus.OK).body("Book deleted successfully");
    }

    // Pesquisa: books?page=0&size=2
    /*@GetMapping("/books")
    public List<BookRecordDto> listBooks(Pageable pageable) {
        return bookService.listBooks(pageable).getContent();
    }*/

}
