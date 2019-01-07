package com.lyonguyen.news.repositories;

import com.lyonguyen.news.models.Article;
import com.lyonguyen.news.models.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticlesRepository extends CrudRepository<Article, Long> {

    Page<News> findAllByOrderByTimeDesc(Pageable pageable);

    Iterable<News> findTop5ByOrderByTimeDesc();

    Page<News> findByTitleLike(String key, Pageable pageable);
}
