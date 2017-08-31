package com.vikination.project1.Models;

import java.util.ArrayList;

/**
 * Created by Viki Andrianto on 8/8/17.
 */

public class TrailerDataResponse {
    String id;
    ArrayList<TrailerData> results;

    public String getId() {
        return id;
    }

    public ArrayList<TrailerData> getResults() {
        return results;
    }

    public static class TrailerData{
        String id;
        String key;
        String name;
        String type;

        public String getId() {
            return id;
        }

        public String getKey() {
            return key;
        }

        public String getName() {
            return name;
        }

        public String getType() {
            return type;
        }
    }
}
