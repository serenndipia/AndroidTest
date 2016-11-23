package com.redbooth.comics;

import java.util.ArrayList;
import java.util.List;

class Marvel {
    public Data data;

    public static class Data {
        public List<Comic> results = new ArrayList<>();

        public static class Comic {
            public String    title;
            public Thumbnail thumbnail;

            public String getThumbnailURL() {
                return thumbnail.path + "." + thumbnail.extension;
            }

            public String getTitle() {
                return title;
            }

            public static class Thumbnail {
                public String path;
                public String extension;
            }
        }
    }
}


