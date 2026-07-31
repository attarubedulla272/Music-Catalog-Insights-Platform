package com.musiclib.service;

import com.musiclib.exception.DuplicateAlbumException;
import com.musiclib.model.Album;
import com.musiclib.model.User;
import com.musiclib.model.dto.AlbumRequest;
import com.musiclib.model.dto.AlbumResponse;
import com.musiclib.repository.AlbumRepository;
import com.musiclib.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LibraryServiceTest {

    @Mock
    private AlbumRepository albumRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private LibraryService libraryService;

    private User testUser;
    private Album testAlbum;

    @BeforeEach
    void setUp() {
        testUser = User.builder()
                .id(1L)
                .username("testuser")
                .email("test@example.com")
                .password("password")
                .build();

        testAlbum = Album.builder()
                .id(100L)
                .user(testUser)
                .appleCatalogId(1440806041L)
                .title("Parachutes")
                .artistName("Coldplay")
                .genre("Alternative")
                .userRating(5)
                .build();
    }

    @Test
    void getUserLibrary_ReturnsPaginatedAlbums() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Album> page = new PageImpl<>(List.of(testAlbum));

        when(albumRepository.findByUserId(1L, pageable)).thenReturn(page);

        Page<AlbumResponse> result = libraryService.getUserLibrary(1L, pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals("Parachutes", result.getContent().get(0).getTitle());
    }

    @Test
    void addAlbum_Success() {
        AlbumRequest request = new AlbumRequest();
        request.setAppleCatalogId(1440806041L);
        request.setTitle("Parachutes");
        request.setArtistName("Coldplay");

        when(albumRepository.existsByUserIdAndAppleCatalogId(1L, 1440806041L)).thenReturn(false);
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(albumRepository.save(any(Album.class))).thenReturn(testAlbum);

        AlbumResponse response = libraryService.addAlbum(1L, request);

        assertNotNull(response);
        assertEquals("Parachutes", response.getTitle());
        verify(albumRepository, times(1)).save(any(Album.class));
    }

    @Test
    void addAlbum_DuplicateThrowsException() {
        AlbumRequest request = new AlbumRequest();
        request.setAppleCatalogId(1440806041L);

        when(albumRepository.existsByUserIdAndAppleCatalogId(1L, 1440806041L)).thenReturn(true);

        assertThrows(DuplicateAlbumException.class, () -> libraryService.addAlbum(1L, request));
        verify(albumRepository, never()).save(any());
    }

    @Test
    void deleteAlbum_Success() {
        when(albumRepository.findByIdAndUserId(100L, 1L)).thenReturn(Optional.of(testAlbum));

        libraryService.deleteAlbum(1L, 100L);

        verify(albumRepository, times(1)).delete(testAlbum);
    }
}
