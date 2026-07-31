package com.musiclib.service;

import com.musiclib.exception.DuplicateAlbumException;
import com.musiclib.exception.ResourceNotFoundException;
import com.musiclib.model.Album;
import com.musiclib.model.User;
import com.musiclib.model.dto.AlbumRequest;
import com.musiclib.model.dto.AlbumResponse;
import com.musiclib.model.dto.AlbumUpdateRequest;
import com.musiclib.repository.AlbumRepository;
import com.musiclib.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class LibraryService {

    private final AlbumRepository albumRepository;
    private final UserRepository userRepository;

    public LibraryService(AlbumRepository albumRepository, UserRepository userRepository) {
        this.albumRepository = albumRepository;
        this.userRepository = userRepository;
    }

    public Page<AlbumResponse> getUserLibrary(Long userId, Pageable pageable) {
        return albumRepository.findByUserId(userId, pageable)
                .map(this::toResponse);
    }

    @Transactional
    public AlbumResponse addAlbum(Long userId, AlbumRequest request) {
        if (albumRepository.existsByUserIdAndAppleCatalogId(userId, request.getAppleCatalogId())) {
            throw new DuplicateAlbumException("Album already exists in your library");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Album album = Album.builder()
                .user(user)
                .appleCatalogId(request.getAppleCatalogId())
                .title(request.getTitle())
                .artistName(request.getArtistName())
                .genre(request.getGenre())
                .releaseDate(parseDate(request.getReleaseDate()))
                .trackCount(request.getTrackCount())
                .price(request.getPrice())
                .artworkUrl(request.getArtworkUrl())
                .userRating(request.getUserRating())
                .userNotes(request.getUserNotes())
                .build();

        Album saved = albumRepository.save(album);
        return toResponse(saved);
    }

    @Transactional
    public AlbumResponse updateAlbum(Long userId, Long albumId, AlbumUpdateRequest request) {
        Album album = albumRepository.findByIdAndUserId(albumId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Album not found in your library"));

        if (request.getUserRating() != null) {
            album.setUserRating(request.getUserRating());
        }
        if (request.getUserNotes() != null) {
            album.setUserNotes(request.getUserNotes());
        }

        Album updated = albumRepository.save(album);
        return toResponse(updated);
    }

    @Transactional
    public void deleteAlbum(Long userId, Long albumId) {
        Album album = albumRepository.findByIdAndUserId(albumId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Album not found in your library"));

        albumRepository.delete(album);
    }

    public AlbumResponse getAlbum(Long userId, Long albumId) {
        Album album = albumRepository.findByIdAndUserId(albumId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Album not found in your library"));

        return toResponse(album);
    }

    private AlbumResponse toResponse(Album album) {
        return AlbumResponse.builder()
                .id(album.getId())
                .appleCatalogId(album.getAppleCatalogId())
                .title(album.getTitle())
                .artistName(album.getArtistName())
                .genre(album.getGenre())
                .releaseDate(album.getReleaseDate())
                .trackCount(album.getTrackCount())
                .price(album.getPrice())
                .artworkUrl(album.getArtworkUrl())
                .userRating(album.getUserRating())
                .userNotes(album.getUserNotes())
                .createdAt(album.getCreatedAt())
                .updatedAt(album.getUpdatedAt())
                .build();
    }

    private LocalDate parseDate(String dateStr) {
        if (dateStr == null || dateStr.isEmpty()) return null;
        try {
            if (dateStr.contains("T")) {
                return LocalDate.parse(dateStr.substring(0, 10));
            }
            return LocalDate.parse(dateStr);
        } catch (Exception e) {
            return null;
        }
    }
}
