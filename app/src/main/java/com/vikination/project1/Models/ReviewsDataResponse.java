package com.vikination.project1.Models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Viki Andrianto on 8/8/17.
 */

public class ReviewsDataResponse {
    String id;
    String page;
    ArrayList<ReviewData> results;

    public String getId() {
        return id;
    }

    public String getPage() {
        return page;
    }

    public ArrayList<ReviewData> getResults() {
        return results;
    }

    public static class ReviewData implements Serializable{
        private static final long serialVersionUID = 3815932791971578864L;
        String id;
        String author;
        String content;
        String url;

        public String getId() {
            return id;
        }

        public String getAuthor() {
            return author;
        }

        public String getContent() {
            return content;
        }

        public String getUrl() {
            return url;
        }
    }
}
