package com.musiclib.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "albums", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"user_id", "apple_catalog_id"}, name = "uk_user_album")
})
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "apple_catalog_id", nullable = false)
    private Long appleCatalogId;

    @NotBlank
    @Size(max = 255)
    @Column(nullable = false)
    private String title;

    @NotBlank
    @Size(max = 255)
    @Column(name = "artist_name", nullable = false)
    private String artistName;

    @Size(max = 100)
    private String genre;

    @Column(name = "release_date")
    private LocalDate releaseDate;

    @Column(name = "track_count")
    private Integer trackCount;

    @Column(precision = 10, scale = 2)
    private BigDecimal price;

    @Size(max = 500)
    @Column(name = "artwork_url", length = 500)
    private String artworkUrl;

    @Min(1)
    @Max(5)
    @Column(name = "user_rating")
    private Integer userRating;

    @Column(name = "user_notes", columnDefinition = "TEXT")
    private String userNotes;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Album() {}

    public Album(Long id, User user, Long appleCatalogId, String title, String artistName, String genre,
                 LocalDate releaseDate, Integer trackCount, BigDecimal price, String artworkUrl,
                 Integer userRating, String userNotes, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.user = user;
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

    public static AlbumBuilder builder() {
        return new AlbumBuilder();
    }

    public static class AlbumBuilder {
        private Long id;
        private User user;
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

        public AlbumBuilder id(Long id) { this.id = id; return this; }
        public AlbumBuilder user(User user) { this.user = user; return this; }
        public AlbumBuilder appleCatalogId(Long appleCatalogId) { this.appleCatalogId = appleCatalogId; return this; }
        public AlbumBuilder title(String title) { this.title = title; return this; }
        public AlbumBuilder artistName(String artistName) { this.artistName = artistName; return this; }
        public AlbumBuilder genre(String genre) { this.genre = genre; return this; }
        public AlbumBuilder releaseDate(LocalDate releaseDate) { this.releaseDate = releaseDate; return this; }
        public AlbumBuilder trackCount(Integer trackCount) { this.trackCount = trackCount; return this; }
        public AlbumBuilder price(BigDecimal price) { this.price = price; return this; }
        public AlbumBuilder artworkUrl(String artworkUrl) { this.artworkUrl = artworkUrl; return this; }
        public AlbumBuilder userRating(Integer userRating) { this.userRating = userRating; return this; }
        public AlbumBuilder userNotes(String userNotes) { this.userNotes = userNotes; return this; }
        public AlbumBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
        public AlbumBuilder updatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; return this; }

        public Album build() {
            return new Album(id, user, appleCatalogId, title, artistName, genre, releaseDate,
                    trackCount, price, artworkUrl, userRating, userNotes, createdAt, updatedAt);
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

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
