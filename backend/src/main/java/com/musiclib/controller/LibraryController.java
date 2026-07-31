package com.musiclib.controller;

import com.musiclib.model.User;
import com.musiclib.model.dto.AlbumRequest;
import com.musiclib.model.dto.AlbumResponse;
import com.musiclib.model.dto.AlbumUpdateRequest;
import com.musiclib.repository.UserRepository;
import com.musiclib.service.LibraryService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/library")
public class LibraryController {

    private final LibraryService libraryService;
    private final UserRepository userRepository;

    public LibraryController(LibraryService libraryService, UserRepository userRepository) {
        this.libraryService = libraryService;
        this.userRepository = userRepository;
    }

    @GetMapping
    public ResponseEntity<Page<AlbumResponse>> getLibrary(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {

        Long userId = getUserId(userDetails);
        Sort sort = sortDir.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<AlbumResponse> library = libraryService.getUserLibrary(userId, pageable);
        return ResponseEntity.ok(library);
    }

    @PostMapping
    public ResponseEntity<AlbumResponse> addAlbum(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody AlbumRequest request) {

        Long userId = getUserId(userDetails);
        AlbumResponse response = libraryService.addAlbum(userId, request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlbumResponse> updateAlbum(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long id,
            @Valid @RequestBody AlbumUpdateRequest request) {

        Long userId = getUserId(userDetails);
        AlbumResponse response = libraryService.updateAlbum(userId, id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteAlbum(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long id) {

        Long userId = getUserId(userDetails);
        libraryService.deleteAlbum(userId, id);
        return ResponseEntity.ok(Map.of("message", "Album removed from library"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlbumResponse> getAlbum(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long id) {

        Long userId = getUserId(userDetails);
        AlbumResponse response = libraryService.getAlbum(userId, id);
        return ResponseEntity.ok(response);
    }

    private Long getUserId(UserDetails userDetails) {
        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));
        return user.getId();
    }
}
