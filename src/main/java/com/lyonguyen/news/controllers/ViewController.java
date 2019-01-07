package com.lyonguyen.news.controllers;

import com.lyonguyen.news.models.Article;
import com.lyonguyen.news.models.News;
import com.lyonguyen.news.services.ArticlesService;
import com.lyonguyen.news.services.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class ViewController {

    @Autowired
    private ArticlesService articlesService;

    @Autowired
    private NewsService newsService;

    @GetMapping("/")
    public String home(Model model,
                       @RequestParam(name = "search", required = false) String searchKey,
                       @RequestParam(name = "page", defaultValue = "1") int page) {

        Page<News> newsStream;
        Iterable latestNews = newsService.getLatestNews();
        model.addAttribute("latestNews", latestNews);

        if (searchKey != null) {

            newsStream = newsService.searchNews(searchKey, page - 1);
            model.addAttribute("newsStream", newsStream);

            return "search";
        }

        newsStream = newsService.getNewsPage(page - 1);
        model.addAttribute("newsStream", newsStream);

        return "home";
    }

    @GetMapping("/article/{id}")
    public String getArticle(Model model,
                             @PathVariable("id") Long id) {

        Article article = articlesService.get(id);
        Iterable<News> latestNews = newsService.getLatestNews();

        model.addAttribute("article", article);
        model.addAttribute("latestNews", latestNews);

        return "article";
    }

    @GetMapping("editor")
    public String createArticle(Model model) {
        model.addAttribute("article", new Article());

        return "editor";
    }

    @GetMapping("editor/{id}")
    public String editArticle(Model model,
                              @PathVariable("id") Long id) {
        Article article = articlesService.get(id);

        model.addAttribute("article", article);

        return "editor";
    }
}
