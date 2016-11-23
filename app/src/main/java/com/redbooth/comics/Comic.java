package com.redbooth.comics;

class Comic {
    private String    title;
    private Thumbnail thumbnail;

    String getThumbnailURL() {
        return thumbnail.path + "." + thumbnail.extension;
    }

    public String getTitle() {
        return title;
    }

    private static class Thumbnail {
        String path;
        String extension;
    }
}


