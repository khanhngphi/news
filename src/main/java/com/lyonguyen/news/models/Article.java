package com.lyonguyen.news.models;

import org.springframework.stereotype.Indexed;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "articles")
public class Article {

    @Id
    @GeneratedValue
    private Long id;

    private String title;

    private String subject;

    private Timestamp time;

    private String image;

    private String brief;

    private String content;

    public Article() {}

    public Article(String title, String subject, Timestamp time, String image, String brief, String content) {
        this.title = title;
        this.subject = subject;
        this.time = time;
        this.image = image;
        this.brief = brief;
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
