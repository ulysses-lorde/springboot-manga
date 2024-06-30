package com.example.springboot.controllers;

import com.example.springboot.dtos.AuthorRecordDto;
import com.example.springboot.models.AuthorModel;
import com.example.springboot.repositories.AuthorRepository;
import com.example.springboot.services.AuthorService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class AuthorController {
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private AuthorService authorService;

    @PostMapping("/authors")
    public ResponseEntity<AuthorModel> saveAuthor(@RequestBody @Valid AuthorRecordDto authorRecordDto) {
        var authorModel = new AuthorModel();
        BeanUtils.copyProperties(authorRecordDto, authorModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(authorRepository.save(authorModel));
    }

    @GetMapping("/authors")
    public ResponseEntity<List<AuthorModel>> getAllAuthors(@PageableDefault(size = 3) Pageable pageable) {
        Page<AuthorModel> authorList = authorService.listAuthors(pageable);
        List<AuthorModel> authors = authorList.getContent();
        return ResponseEntity.status(HttpStatus.OK).body(authors);
    }

    @GetMapping("/authors/{id}")
    public ResponseEntity<Object> getOneAuthor(@PathVariable(value="id") UUID id) {
        Optional<AuthorModel> author0 = authorRepository.findById(id);
        if (author0.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Author not found.");
        }
        author0.get().add(linkTo(methodOn(AuthorController.class).getAllAuthors(null)).withRel("Author List"));
        return ResponseEntity.status(HttpStatus.OK).body(author0.get());
    }
}
