package com.lyonguyen.news.services;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.InputStreamContent;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.json.JsonFactory;

import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.*;
import com.google.api.services.drive.Drive;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


@Service
public class GoogleDriveServiceImpl implements GoogleDriveService {

    @Value("${webapp.name}")
    private String APPLICATION_NAME;

    @Value("${webapp.drive.service-account}")
    private String SERVICE_ACCOUNT;

    @Value("${webapp.drive.credentials}")
    private String CREDENTIALS_JSON;

    @Value("${webapp.drive.folder-id}")
    private String FOLDER_ID;

    private HttpTransport HTTP_TRANSPORT;

    private JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    private final List<String> SCOPES = Arrays.asList(DriveScopes.DRIVE);

    private Drive service;

    @PostConstruct
    public void init() throws Exception {
        HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();

        service = getDriveService();

        listAll();
    }

    private Credential getCredential() throws IOException {
        InputStream inputStream = new ClassPathResource(CREDENTIALS_JSON).getInputStream();

        GoogleCredential credential = GoogleCredential
                .fromStream(inputStream)
                .createScoped(SCOPES);
        GoogleCredential.Builder builder = new GoogleCredential.Builder()
                .setTransport(HTTP_TRANSPORT)
                .setJsonFactory(JSON_FACTORY)
                .setServiceAccountScopes(SCOPES)
                .setServiceAccountId(credential.getServiceAccountId())
                .setServiceAccountPrivateKey(credential.getServiceAccountPrivateKey())
                .setServiceAccountPrivateKeyId(credential.getServiceAccountPrivateKeyId())
                .setTokenServerEncodedUrl(credential.getTokenServerEncodedUrl())
                .setServiceAccountUser(SERVICE_ACCOUNT);

        return builder.build();
    }

    private Drive getDriveService() throws IOException {
        Credential credential = getCredential();

        return new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
                    .setApplicationName(APPLICATION_NAME)
                    .build();
    }

    public Resource write(String name, InputStream file) throws IOException {
        File fileMetadata = new File();
        fileMetadata.setName(name);
        fileMetadata.setParents(Collections.singletonList(FOLDER_ID));

        InputStreamContent mediaContent = new InputStreamContent(null, file);

        File uploadedFile = service.files()
                .create(fileMetadata, mediaContent)
                .setFields("id")
                .setFields("webContentLink")
                .execute();

        return new UrlResource(uploadedFile.getWebContentLink());
    }

    @Override
    public Resource read(String name) {
        return null;
    }

    public void listAll() throws Exception {
        Drive service = getDriveService();
        System.out.println(service.about().get());

        FileList result = service.files().list()
                .setPageSize(10)
                .setFields("nextPageToken, files(id, name)")
                .execute();
        List<File> files = result.getFiles();
        if (files == null || files.size() == 0) {
            System.out.println("No files found.");
        } else {
            System.out.println("Files:");
            for (File file : files) {
                System.out.printf("%s (%s)\n", file.getName(), file.getId());
            }
        }
    }
}
