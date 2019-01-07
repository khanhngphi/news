package com.lyonguyen.news.controllers;

import com.lyonguyen.news.models.Article;
import com.lyonguyen.news.models.News;
import com.lyonguyen.news.services.ArticlesService;
import com.lyonguyen.news.services.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/article")
public class ArticlesController {

    @Autowired
    private ArticlesService articlesService;

    @PostMapping
    public ResponseEntity createArticle(@RequestBody Article article) {

        Article savedArticle = articlesService.create(article);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().replacePath("article").path("/{id}")
                .buildAndExpand(savedArticle.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity updateArticle(@RequestBody Article article,
                                       @PathVariable("id") Long id) {
        article.setId(id);

        articlesService.update(article);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().replacePath("article").path("/{id}")
                .buildAndExpand(id).toUri();

        return ResponseEntity.created(location).build();

    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteArticle(@PathVariable("id") Long id) {

        articlesService.delete(id);

        return ResponseEntity.noContent().build();

    }

}
