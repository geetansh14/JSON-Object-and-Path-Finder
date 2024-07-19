package com.scoreme.root;

public class JsonResponse {
    private String path;

    public JsonResponse() {
    }

    public JsonResponse(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
