package com.musiclib.model.dto;

import java.util.List;

public class RecommendationResponse {

    private List<RecommendationItem> recommendations;
    private String summary;

    public RecommendationResponse() {}

    public RecommendationResponse(List<RecommendationItem> recommendations, String summary) {
        this.recommendations = recommendations;
        this.summary = summary;
    }

    public static RecommendationResponseBuilder builder() {
        return new RecommendationResponseBuilder();
    }

    public static class RecommendationResponseBuilder {
        private List<RecommendationItem> recommendations;
        private String summary;

        public RecommendationResponseBuilder recommendations(List<RecommendationItem> recommendations) {
            this.recommendations = recommendations;
            return this;
        }

        public RecommendationResponseBuilder summary(String summary) {
            this.summary = summary;
            return this;
        }

        public RecommendationResponse build() {
            return new RecommendationResponse(recommendations, summary);
        }
    }

    public List<RecommendationItem> getRecommendations() { return recommendations; }
    public void setRecommendations(List<RecommendationItem> recommendations) { this.recommendations = recommendations; }

    public String getSummary() { return summary; }
    public void setSummary(String summary) { this.summary = summary; }

    public static class RecommendationItem {
        private Long appleCatalogId;
        private String title;
        private String artistName;
        private String genre;
        private String releaseDate;
        private Integer trackCount;
        private Double price;
        private String artworkUrl;
        private String reason;
        private Double score;

        public RecommendationItem() {}

        public RecommendationItem(Long appleCatalogId, String title, String artistName, String genre,
                                  String releaseDate, Integer trackCount, Double price, String artworkUrl,
                                  String reason, Double score) {
            this.appleCatalogId = appleCatalogId;
            this.title = title;
            this.artistName = artistName;
            this.genre = genre;
            this.releaseDate = releaseDate;
            this.trackCount = trackCount;
            this.price = price;
            this.artworkUrl = artworkUrl;
            this.reason = reason;
            this.score = score;
        }

        public static RecommendationItemBuilder builder() {
            return new RecommendationItemBuilder();
        }

        public static class RecommendationItemBuilder {
            private Long appleCatalogId;
            private String title;
            private String artistName;
            private String genre;
            private String releaseDate;
            private Integer trackCount;
            private Double price;
            private String artworkUrl;
            private String reason;
            private Double score;

            public RecommendationItemBuilder appleCatalogId(Long appleCatalogId) { this.appleCatalogId = appleCatalogId; return this; }
            public RecommendationItemBuilder title(String title) { this.title = title; return this; }
            public RecommendationItemBuilder artistName(String artistName) { this.artistName = artistName; return this; }
            public RecommendationItemBuilder genre(String genre) { this.genre = genre; return this; }
            public RecommendationItemBuilder releaseDate(String releaseDate) { this.releaseDate = releaseDate; return this; }
            public RecommendationItemBuilder trackCount(Integer trackCount) { this.trackCount = trackCount; return this; }
            public RecommendationItemBuilder price(Double price) { this.price = price; return this; }
            public RecommendationItemBuilder artworkUrl(String artworkUrl) { this.artworkUrl = artworkUrl; return this; }
            public RecommendationItemBuilder reason(String reason) { this.reason = reason; return this; }
            public RecommendationItemBuilder score(Double score) { this.score = score; return this; }

            public RecommendationItem build() {
                return new RecommendationItem(appleCatalogId, title, artistName, genre, releaseDate,
                        trackCount, price, artworkUrl, reason, score);
            }
        }

        public Long getAppleCatalogId() { return appleCatalogId; }
        public void setAppleCatalogId(Long appleCatalogId) { this.appleCatalogId = appleCatalogId; }

        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }

        public String getArtistName() { return artistName; }
        public void setArtistName(String artistName) { this.artistName = artistName; }

        public String getGenre() { return genre; }
        public void setGenre(String genre) { this.genre = genre; }

        public String getReleaseDate() { return releaseDate; }
        public void setReleaseDate(String releaseDate) { this.releaseDate = releaseDate; }

        public Integer getTrackCount() { return trackCount; }
        public void setTrackCount(Integer trackCount) { this.trackCount = trackCount; }

        public Double getPrice() { return price; }
        public void setPrice(Double price) { this.price = price; }

        public String getArtworkUrl() { return artworkUrl; }
        public void setArtworkUrl(String artworkUrl) { this.artworkUrl = artworkUrl; }

        public String getReason() { return reason; }
        public void setReason(String reason) { this.reason = reason; }

        public Double getScore() { return score; }
        public void setScore(Double score) { this.score = score; }
    }
}
