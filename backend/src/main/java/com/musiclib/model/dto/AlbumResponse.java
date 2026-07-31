package com.musiclib.model.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class AlbumResponse {

    private Long id;
    private Long appleCatalogId;
    private String title;
    private String artistName;
    private String genre;
    private LocalDate releaseDate;
    private Integer trackCount;
    private BigDecimal price;
    private String artworkUrl;
    private Integer userRating;
    private String userNotes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public AlbumResponse() {}

    public AlbumResponse(Long id, Long appleCatalogId, String title, String artistName, String genre,
                         LocalDate releaseDate, Integer trackCount, BigDecimal price, String artworkUrl,
                         Integer userRating, String userNotes, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.appleCatalogId = appleCatalogId;
        this.title = title;
        this.artistName = artistName;
        this.genre = genre;
        this.releaseDate = releaseDate;
        this.trackCount = trackCount;
        this.price = price;
        this.artworkUrl = artworkUrl;
        this.userRating = userRating;
        this.userNotes = userNotes;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static AlbumResponseBuilder builder() {
        return new AlbumResponseBuilder();
    }

    public static class AlbumResponseBuilder {
        private Long id;
        private Long appleCatalogId;
        private String title;
        private String artistName;
        private String genre;
        private LocalDate releaseDate;
        private Integer trackCount;
        private BigDecimal price;
        private String artworkUrl;
        private Integer userRating;
        private String userNotes;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public AlbumResponseBuilder id(Long id) { this.id = id; return this; }
        public AlbumResponseBuilder appleCatalogId(Long appleCatalogId) { this.appleCatalogId = appleCatalogId; return this; }
        public AlbumResponseBuilder title(String title) { this.title = title; return this; }
        public AlbumResponseBuilder artistName(String artistName) { this.artistName = artistName; return this; }
        public AlbumResponseBuilder genre(String genre) { this.genre = genre; return this; }
        public AlbumResponseBuilder releaseDate(LocalDate releaseDate) { this.releaseDate = releaseDate; return this; }
        public AlbumResponseBuilder trackCount(Integer trackCount) { this.trackCount = trackCount; return this; }
        public AlbumResponseBuilder price(BigDecimal price) { this.price = price; return this; }
        public AlbumResponseBuilder artworkUrl(String artworkUrl) { this.artworkUrl = artworkUrl; return this; }
        public AlbumResponseBuilder userRating(Integer userRating) { this.userRating = userRating; return this; }
        public AlbumResponseBuilder userNotes(String userNotes) { this.userNotes = userNotes; return this; }
        public AlbumResponseBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
        public AlbumResponseBuilder updatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; return this; }

        public AlbumResponse build() {
            return new AlbumResponse(id, appleCatalogId, title, artistName, genre, releaseDate,
                    trackCount, price, artworkUrl, userRating, userNotes, createdAt, updatedAt);
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getAppleCatalogId() { return appleCatalogId; }
    public void setAppleCatalogId(Long appleCatalogId) { this.appleCatalogId = appleCatalogId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getArtistName() { return artistName; }
    public void setArtistName(String artistName) { this.artistName = artistName; }

    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }

    public LocalDate getReleaseDate() { return releaseDate; }
    public void setReleaseDate(LocalDate releaseDate) { this.releaseDate = releaseDate; }

    public Integer getTrackCount() { return trackCount; }
    public void setTrackCount(Integer trackCount) { this.trackCount = trackCount; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public String getArtworkUrl() { return artworkUrl; }
    public void setArtworkUrl(String artworkUrl) { this.artworkUrl = artworkUrl; }

    public Integer getUserRating() { return userRating; }
    public void setUserRating(Integer userRating) { this.userRating = userRating; }

    public String getUserNotes() { return userNotes; }
    public void setUserNotes(String userNotes) { this.userNotes = userNotes; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
