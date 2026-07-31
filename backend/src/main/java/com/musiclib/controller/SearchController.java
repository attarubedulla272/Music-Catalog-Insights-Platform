package com.musiclib.controller;

import com.musiclib.service.ITunesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/search")
public class SearchController {

    private final ITunesService iTunesService;

    public SearchController(ITunesService iTunesService) {
        this.iTunesService = iTunesService;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> search(
            @RequestParam String query,
            @RequestParam(defaultValue = "album") String type,
            @RequestParam(defaultValue = "25") Integer limit) {

        if (query == null || query.trim().isEmpty()) {
            throw new IllegalArgumentException("Search query cannot be empty");
        }
        if (limit < 1 || limit > 200) {
            throw new IllegalArgumentException("Limit must be between 1 and 200");
        }

        Map<String, Object> results = iTunesService.search(query.trim(), type, limit);
        return ResponseEntity.ok(results);
    }

    @GetMapping("/lookup/{id}")
    public ResponseEntity<Map<String, Object>> lookup(@PathVariable Long id) {
        Map<String, Object> result = iTunesService.lookup(id);
        return ResponseEntity.ok(result);
    }
}
