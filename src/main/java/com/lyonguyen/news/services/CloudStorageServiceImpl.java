package com.lyonguyen.news.services;

import com.google.api.client.util.IOUtils;
import com.lyonguyen.news.utils.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.WritableResource;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@Service
public class CloudStorageServiceImpl implements CloudStorageService {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private Random random;

    @Value("${webapp.bucket.name}")
    private String bucket;

    @Override
    public Resource write(String name, InputStream file) throws IOException {
        Assert.isTrue(!name.trim().isEmpty(), "File name cannot null");

        Resource resource = applicationContext.getResource("gs://" + bucket + "/" + encode(name));

        try (OutputStream os = ((WritableResource) resource).getOutputStream()) {
            IOUtils.copy(file, os);
        }
        return resource;
    }

    @Override
    public Resource read(String name) throws IOException {
        Resource resource = applicationContext.getResource("gs://" + bucket + "/" + name);

        if (!resource.exists()) {
            throw new FileNotFoundException();
        }

        return resource;
    }

    private String encode(String name) {
        return random.range(1000, 10000) + "-" + name;
    }
}
