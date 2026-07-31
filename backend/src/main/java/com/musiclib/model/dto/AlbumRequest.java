package com.musiclib.model.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public class AlbumRequest {

    @NotNull(message = "Apple catalog ID is required")
    private Long appleCatalogId;

    @NotBlank(message = "Title is required")
    @Size(max = 255)
    private String title;

    @NotBlank(message = "Artist name is required")
    @Size(max = 255)
    private String artistName;

    @Size(max = 100)
    private String genre;

    private String releaseDate;
    private Integer trackCount;
    private BigDecimal price;

    @Size(max = 500)
    private String artworkUrl;

    @Min(value = 1, message = "Rating must be between 1 and 5")
    @Max(value = 5, message = "Rating must be between 1 and 5")
    private Integer userRating;

    private String userNotes;

    public AlbumRequest() {}

    public AlbumRequest(Long appleCatalogId, String title, String artistName, String genre,
                        String releaseDate, Integer trackCount, BigDecimal price, String artworkUrl,
                        Integer userRating, String userNotes) {
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

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public String getArtworkUrl() { return artworkUrl; }
    public void setArtworkUrl(String artworkUrl) { this.artworkUrl = artworkUrl; }

    public Integer getUserRating() { return userRating; }
    public void setUserRating(Integer userRating) { this.userRating = userRating; }

    public String getUserNotes() { return userNotes; }
    public void setUserNotes(String userNotes) { this.userNotes = userNotes; }
}
