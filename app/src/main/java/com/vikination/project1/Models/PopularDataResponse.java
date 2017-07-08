package com.vikination.project1.Models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Viki Andrianto on 7/8/17.
 */

public class PopularDataResponse {
    private int page;
    private int total_results;
    private int total_pages;
    private ArrayList<MovieData> results;

    public int getPage() {
        return page;
    }

    public int getTotal_results() {
        return total_results;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public ArrayList<MovieData> getResults() {
        return results;
    }

    public static class MovieData implements Serializable{
        private static final long serialVersionUID = 2627896765245549965L;

        int vote_count;
        int id;
        boolean video;
        double vote_average;
        String title;
        double popularity;
        String poster_path;
        String original_language;
        String original_title;
        String backdrop_path;
        boolean adult;
        String overview;
        String release_date;

        public int getVote_count() {
            return vote_count;
        }

        public int getId() {
            return id;
        }

        public boolean isVideo() {
            return video;
        }

        public double getVote_average() {
            return vote_average;
        }

        public String getTitle() {
            return title;
        }

        public double getPopularity() {
            return popularity;
        }

        public String getPoster_path() {
            return poster_path;
        }

        public String getOriginal_language() {
            return original_language;
        }

        public String getOriginal_title() {
            return original_title;
        }

        public String getBackdrop_path() {
            return backdrop_path;
        }

        public boolean isAdult() {
            return adult;
        }

        public String getOverview() {
            return overview;
        }

        public String getRelease_date() {
            return release_date;
        }
    }
}
