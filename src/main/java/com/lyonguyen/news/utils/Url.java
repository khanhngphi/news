package com.lyonguyen.news.utils;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public class Url {

    public String param(String name, String value) {
        return ServletUriComponentsBuilder.fromCurrentRequest().replaceQueryParam(name, value).toUriString();
    }
}
