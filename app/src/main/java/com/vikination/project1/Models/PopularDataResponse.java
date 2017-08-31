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
        String offline_trailer;
        String offline_review;
        byte[] offline_image;

        public byte[] getOffline_image() {
            return offline_image;
        }

        public void setOffline_image(byte[] offline_image) {
            this.offline_image = offline_image;
        }

        public String getOffline_trailer() {
            return offline_trailer;
        }

        public void setOffline_trailer(String offline_trailer) {
            this.offline_trailer = offline_trailer;
        }

        public String getOffline_review() {
            return offline_review;
        }

        public void setOffline_review(String offline_review) {
            this.offline_review = offline_review;
        }

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

        public void setVote_count(int vote_count) {
            this.vote_count = vote_count;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setVideo(boolean video) {
            this.video = video;
        }

        public void setVote_average(double vote_average) {
            this.vote_average = vote_average;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setPopularity(double popularity) {
            this.popularity = popularity;
        }

        public void setPoster_path(String poster_path) {
            this.poster_path = poster_path;
        }

        public void setOriginal_language(String original_language) {
            this.original_language = original_language;
        }

        public void setOriginal_title(String original_title) {
            this.original_title = original_title;
        }

        public void setBackdrop_path(String backdrop_path) {
            this.backdrop_path = backdrop_path;
        }

        public void setAdult(boolean adult) {
            this.adult = adult;
        }

        public void setOverview(String overview) {
            this.overview = overview;
        }

        public void setRelease_date(String release_date) {
            this.release_date = release_date;
        }
    }
}
