package com.lyonguyen.news.services;

import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;

public interface StorageService {

    Resource write(String name, InputStream file) throws IOException;

    Resource read(String name) throws IOException;
}
