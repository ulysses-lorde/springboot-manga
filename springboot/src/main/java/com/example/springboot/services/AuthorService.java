package com.example.springboot.services;

import com.example.springboot.controllers.AuthorController;
import com.example.springboot.models.AuthorModel;
import com.example.springboot.repositories.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class AuthorService {
    @Autowired
    private AuthorRepository authorRepository;

    public Page<AuthorModel> listAuthors(Pageable pageable) {
        Page<AuthorModel> authorPage = authorRepository.findAll(pageable);
        List<AuthorModel> authors = authorPage.getContent();
        return listAuthorsWithLinks(authors);
    }

    public void addAuthorsListlinks(AuthorModel author) {
        Link authorListLink = linkTo(methodOn(AuthorController.class).getAllAuthors(null)).withRel("Author List");
        author.add(authorListLink);
    }

    public Page<AuthorModel> listAuthorsWithLinks(List<AuthorModel> authorList) {
        for (AuthorModel author : authorList) {
            UUID id = author.getIdAuthor();
            author.add(linkTo(methodOn(AuthorController.class).getOneAuthor(id)).withSelfRel());
        }
        return new PageImpl<>(authorList);
    }
}
