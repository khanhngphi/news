package com.lyonguyen.news.models;

import java.sql.Timestamp;

public interface News {

    Long getId();

    String getTitle();

    String getSubject();

    Timestamp getTime();

    String getImage();

    String getBrief();
}