package com.musiclib.model.dto;

import java.util.List;
import java.util.Map;

public class AnalyticsResponse {

    private Long totalAlbums;
    private Long uniqueGenres;
    private Long uniqueArtists;
    private Double averageRating;
    private String topGenre;
    private String topArtist;
    private List<Map<String, Object>> genreDistribution;
    private List<Map<String, Object>> decadeDistribution;
    private List<Map<String, Object>> ratingDistribution;
    private List<Map<String, Object>> topArtists;
    private List<Map<String, Object>> monthlyAdditions;

    public AnalyticsResponse() {}

    public AnalyticsResponse(Long totalAlbums, Long uniqueGenres, Long uniqueArtists, Double averageRating,
                             String topGenre, String topArtist, List<Map<String, Object>> genreDistribution,
                             List<Map<String, Object>> decadeDistribution, List<Map<String, Object>> ratingDistribution,
                             List<Map<String, Object>> topArtists, List<Map<String, Object>> monthlyAdditions) {
        this.totalAlbums = totalAlbums;
        this.uniqueGenres = uniqueGenres;
        this.uniqueArtists = uniqueArtists;
        this.averageRating = averageRating;
        this.topGenre = topGenre;
        this.topArtist = topArtist;
        this.genreDistribution = genreDistribution;
        this.decadeDistribution = decadeDistribution;
        this.ratingDistribution = ratingDistribution;
        this.topArtists = topArtists;
        this.monthlyAdditions = monthlyAdditions;
    }

    public static AnalyticsResponseBuilder builder() {
        return new AnalyticsResponseBuilder();
    }

    public static class AnalyticsResponseBuilder {
        private Long totalAlbums;
        private Long uniqueGenres;
        private Long uniqueArtists;
        private Double averageRating;
        private String topGenre;
        private String topArtist;
        private List<Map<String, Object>> genreDistribution;
        private List<Map<String, Object>> decadeDistribution;
        private List<Map<String, Object>> ratingDistribution;
        private List<Map<String, Object>> topArtists;
        private List<Map<String, Object>> monthlyAdditions;

        public AnalyticsResponseBuilder totalAlbums(Long totalAlbums) { this.totalAlbums = totalAlbums; return this; }
        public AnalyticsResponseBuilder uniqueGenres(Long uniqueGenres) { this.uniqueGenres = uniqueGenres; return this; }
        public AnalyticsResponseBuilder uniqueArtists(Long uniqueArtists) { this.uniqueArtists = uniqueArtists; return this; }
        public AnalyticsResponseBuilder averageRating(Double averageRating) { this.averageRating = averageRating; return this; }
        public AnalyticsResponseBuilder topGenre(String topGenre) { this.topGenre = topGenre; return this; }
        public AnalyticsResponseBuilder topArtist(String topArtist) { this.topArtist = topArtist; return this; }
        public AnalyticsResponseBuilder genreDistribution(List<Map<String, Object>> genreDistribution) { this.genreDistribution = genreDistribution; return this; }
        public AnalyticsResponseBuilder decadeDistribution(List<Map<String, Object>> decadeDistribution) { this.decadeDistribution = decadeDistribution; return this; }
        public AnalyticsResponseBuilder ratingDistribution(List<Map<String, Object>> ratingDistribution) { this.ratingDistribution = ratingDistribution; return this; }
        public AnalyticsResponseBuilder topArtists(List<Map<String, Object>> topArtists) { this.topArtists = topArtists; return this; }
        public AnalyticsResponseBuilder monthlyAdditions(List<Map<String, Object>> monthlyAdditions) { this.monthlyAdditions = monthlyAdditions; return this; }

        public AnalyticsResponse build() {
            return new AnalyticsResponse(totalAlbums, uniqueGenres, uniqueArtists, averageRating, topGenre,
                    topArtist, genreDistribution, decadeDistribution, ratingDistribution, topArtists, monthlyAdditions);
        }
    }

    public Long getTotalAlbums() { return totalAlbums; }
    public void setTotalAlbums(Long totalAlbums) { this.totalAlbums = totalAlbums; }

    public Long getUniqueGenres() { return uniqueGenres; }
    public void setUniqueGenres(Long uniqueGenres) { this.uniqueGenres = uniqueGenres; }

    public Long getUniqueArtists() { return uniqueArtists; }
    public void setUniqueArtists(Long uniqueArtists) { this.uniqueArtists = uniqueArtists; }

    public Double getAverageRating() { return averageRating; }
    public void setAverageRating(Double averageRating) { this.averageRating = averageRating; }

    public String getTopGenre() { return topGenre; }
    public void setTopGenre(String topGenre) { this.topGenre = topGenre; }

    public String getTopArtist() { return topArtist; }
    public void setTopArtist(String topArtist) { this.topArtist = topArtist; }

    public List<Map<String, Object>> getGenreDistribution() { return genreDistribution; }
    public void setGenreDistribution(List<Map<String, Object>> genreDistribution) { this.genreDistribution = genreDistribution; }

    public List<Map<String, Object>> getDecadeDistribution() { return decadeDistribution; }
    public void setDecadeDistribution(List<Map<String, Object>> decadeDistribution) { this.decadeDistribution = decadeDistribution; }

    public List<Map<String, Object>> getRatingDistribution() { return ratingDistribution; }
    public void setRatingDistribution(List<Map<String, Object>> ratingDistribution) { this.ratingDistribution = ratingDistribution; }

    public List<Map<String, Object>> getTopArtists() { return topArtists; }
    public void setTopArtists(List<Map<String, Object>> topArtists) { this.topArtists = topArtists; }

    public List<Map<String, Object>> getMonthlyAdditions() { return monthlyAdditions; }
    public void setMonthlyAdditions(List<Map<String, Object>> monthlyAdditions) { this.monthlyAdditions = monthlyAdditions; }
}
