package com.lyonguyen.news.models;

public class CKEditorUploadResponse {

    private int uploaded;

    private String fileName;

    private String url;

    public CKEditorUploadResponse() {
    }

    public CKEditorUploadResponse(int uploaded, String fileName, String url) {
        this.uploaded = uploaded;
        this.fileName = fileName;
        this.url = url;
    }

    public int getUploaded() {
        return uploaded;
    }

    public void setUploaded(int uploaded) {
        this.uploaded = uploaded;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
