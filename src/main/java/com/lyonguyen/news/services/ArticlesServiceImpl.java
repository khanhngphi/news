package com.lyonguyen.news.services;

import com.lyonguyen.news.exceptions.BadRequestException;
import com.lyonguyen.news.exceptions.NotFoundException;
import com.lyonguyen.news.models.Article;
import com.lyonguyen.news.repositories.ArticlesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Optional;

@Service
@Transactional
public class ArticlesServiceImpl implements ArticlesService {

    @Autowired
    private ArticlesRepository articlesRepository;

    public Article get(Long id) {
        Optional<Article> maybeArticle = articlesRepository.findById(id);

        if (!maybeArticle.isPresent()) {
            throw new NotFoundException();
        }

        return maybeArticle.get();
    }

    @Override
    public Article create(Article article) {
        if (article.getId() != null) {
            throw new BadRequestException();
        }

        return articlesRepository.save(article);
    }

    @Override
    public Article update(Article article) {
        if (article.getId() == null) {
            throw new BadRequestException();
        }
        if (!articlesRepository.existsById(article.getId())) {
            throw new NotFoundException();
        }

        return articlesRepository.save(article);
    }

    @Override
    public void delete(Long id) {
        if (id == null) {
            throw new BadRequestException();
        }
        if (!articlesRepository.existsById(id)) {
            throw new NotFoundException();
        }

        articlesRepository.deleteById(id);
    }
}
