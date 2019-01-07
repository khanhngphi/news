package com.lyonguyen.news.services;

import com.lyonguyen.news.models.News;
import com.lyonguyen.news.repositories.ArticlesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
@Transactional
public class NewsServiceImpl implements NewsService {

    @Autowired
    private ArticlesRepository articlesRepository;

    @Value("${webapp.pagesize}")
    private int pageSize;

    @Override
    public Page<News> getNewsPage(int page) {
        Pageable pageable = new QPageRequest(page, pageSize);

        Page<News> news = articlesRepository.findAllByOrderByTimeDesc(pageable);

        Assert.isTrue(page < news.getTotalPages() || page == 0, "Except limit page!");

        return news;
    }

    @Override
    public Iterable<News> getLatestNews() {
        return articlesRepository.findTop5ByOrderByTimeDesc();
    }

    @Override
    public Page<News> searchNews(String key, int page) {
        Pageable pageable = new QPageRequest(page, pageSize);

        Page<News> news;

        if (key.trim().isEmpty()) {
            news = Page.empty(pageable);
        } else {
            news = articlesRepository.findByTitleLike("%" + key + "%", pageable);
        }

        Assert.isTrue(page < news.getTotalPages() || page == 0, "Except limit page!");

        return news;
    }
}
