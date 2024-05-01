package com.example.springboot.service;

import com.example.springboot.controllers.ProductController;
import com.example.springboot.dtos.ProductRecordDto;
import com.example.springboot.models.ProductModel;
import com.example.springboot.repositories.ProductRepository;
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
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    public Page<ProductModel> listProducts(Pageable pageable) {
        Page<ProductModel> productPage = productRepository.findAll(pageable);
        List<ProductModel> products = productPage.getContent();
        return listProductsWithLinks(products);
    }

    public List<ProductModel> findByName(String name) {
        List<ProductModel> products = productRepository.findByPartialName(name);
        for (ProductModel product : products) {
            addProductListLink(product);
        }
        return products;
    }

    public void addProductListLink(ProductModel product) {
        Link productListLink = linkTo(methodOn(ProductController.class).getAllProducts(null)).withRel("Product List");
        product.add(productListLink);
    }

    public Page<ProductModel> listProductsWithLinks(List<ProductModel> productList) {
        for (ProductModel product : productList) {
            UUID id = product.getIdProduct();
            product.add(linkTo(methodOn(ProductController.class).getOneProduct(id)).withSelfRel());
        }

        return new PageImpl<>(productList);
    }
}
