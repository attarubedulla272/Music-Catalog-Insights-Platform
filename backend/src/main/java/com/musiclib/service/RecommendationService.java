package com.musiclib.service;

import com.musiclib.model.dto.RecommendationResponse;
import com.musiclib.model.dto.RecommendationResponse.RecommendationItem;
import com.musiclib.repository.AlbumRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RecommendationService {

    private static final Logger log = LoggerFactory.getLogger(RecommendationService.class);

    private final AlbumRepository albumRepository;
    private final ITunesService iTunesService;

    public RecommendationService(AlbumRepository albumRepository, ITunesService iTunesService) {
        this.albumRepository = albumRepository;
        this.iTunesService = iTunesService;
    }

    public RecommendationResponse getRecommendations(Long userId) {
        List<String> topGenres = albumRepository.getTopGenres(userId);
        List<String> topArtists = albumRepository.getTopArtistNames(userId);
        List<Long> existingIds = albumRepository.getAppleCatalogIdsByUserId(userId);
        long totalAlbums = albumRepository.countByUserId(userId);

        if (totalAlbums == 0) {
            return RecommendationResponse.builder()
                    .recommendations(Collections.emptyList())
                    .summary("Add some albums to your library to get personalized recommendations!")
                    .build();
        }

        Set<Long> existingIdSet = new HashSet<>(existingIds);
        List<RecommendationItem> allRecommendations = new ArrayList<>();

        int genreLimit = Math.min(topGenres.size(), 3);
        for (int i = 0; i < genreLimit; i++) {
            String genre = topGenres.get(i);
            try {
                List<Map<String, Object>> results = iTunesService.searchAlbums(genre + " music", 15);
                for (Map<String, Object> result : results) {
                    Long catalogId = getLongValue(result, "collectionId");
                    if (catalogId != null && !existingIdSet.contains(catalogId)) {
                        double score = 0.9 - (i * 0.15);
                        allRecommendations.add(buildRecommendation(
                                result, "Because you like " + genre, score
                        ));
                        existingIdSet.add(catalogId);
                    }
                }
            } catch (Exception e) {
                log.warn("Failed to fetch genre recommendations for {}: {}", genre, e.getMessage());
            }
        }

        int artistLimit = Math.min(topArtists.size(), 3);
        for (int i = 0; i < artistLimit; i++) {
            String artist = topArtists.get(i);
            try {
                List<Map<String, Object>> results = iTunesService.searchAlbums(artist, 10);
                for (Map<String, Object> result : results) {
                    Long catalogId = getLongValue(result, "collectionId");
                    if (catalogId != null && !existingIdSet.contains(catalogId)) {
                        String resultArtist = (String) result.get("artistName");
                        double score = 0.85 - (i * 0.1);
                        String reason = artist.equalsIgnoreCase(resultArtist)
                                ? "More from " + artist
                                : "Fans of " + artist + " also enjoy this";
                        allRecommendations.add(buildRecommendation(result, reason, score));
                        existingIdSet.add(catalogId);
                    }
                }
            } catch (Exception e) {
                log.warn("Failed to fetch artist recommendations for {}: {}", artist, e.getMessage());
            }
        }

        if (topGenres.size() > 3) {
            String discoveryGenre = topGenres.get(topGenres.size() - 1);
            try {
                List<Map<String, Object>> results = iTunesService.searchAlbums("best " + discoveryGenre + " albums", 8);
                for (Map<String, Object> result : results) {
                    Long catalogId = getLongValue(result, "collectionId");
                    if (catalogId != null && !existingIdSet.contains(catalogId)) {
                        allRecommendations.add(buildRecommendation(
                                result, "Expand your " + discoveryGenre + " collection", 0.6
                        ));
                        existingIdSet.add(catalogId);
                    }
                }
            } catch (Exception e) {
                log.warn("Failed to fetch discovery recommendations: {}", e.getMessage());
            }
        }

        List<RecommendationItem> topRecommendations = allRecommendations.stream()
                .sorted(Comparator.comparingDouble(RecommendationItem::getScore).reversed())
                .limit(12)
                .collect(Collectors.toList());

        String summary = generateSummary(topGenres, topArtists, totalAlbums);

        return RecommendationResponse.builder()
                .recommendations(topRecommendations)
                .summary(summary)
                .build();
    }

    private RecommendationItem buildRecommendation(Map<String, Object> result, String reason, double score) {
        return RecommendationItem.builder()
                .appleCatalogId(getLongValue(result, "collectionId"))
                .title(getStringValue(result, "collectionName"))
                .artistName(getStringValue(result, "artistName"))
                .genre(getStringValue(result, "primaryGenreName"))
                .releaseDate(getStringValue(result, "releaseDate"))
                .trackCount(getIntValue(result, "trackCount"))
                .price(getDoubleValue(result, "collectionPrice"))
                .artworkUrl(getStringValue(result, "artworkUrl100"))
                .reason(reason)
                .score(score)
                .build();
    }

    private String generateSummary(List<String> topGenres, List<String> topArtists, long totalAlbums) {
        StringBuilder sb = new StringBuilder();
        sb.append("Based on your library of ").append(totalAlbums).append(" album");
        if (totalAlbums > 1) sb.append("s");
        sb.append(", ");

        if (!topGenres.isEmpty()) {
            sb.append("you seem to love ").append(topGenres.get(0));
            if (topGenres.size() > 1) {
                sb.append(" and ").append(topGenres.get(1));
            }
            sb.append(". ");
        }

        if (!topArtists.isEmpty()) {
            sb.append("Your top artist is ").append(topArtists.get(0)).append(". ");
        }

        sb.append("Here are some albums you might enjoy!");
        return sb.toString();
    }

    private Long getLongValue(Map<String, Object> map, String key) {
        Object val = map.get(key);
        if (val instanceof Number) return ((Number) val).longValue();
        return null;
    }

    private String getStringValue(Map<String, Object> map, String key) {
        Object val = map.get(key);
        return val != null ? val.toString() : null;
    }

    private Integer getIntValue(Map<String, Object> map, String key) {
        Object val = map.get(key);
        if (val instanceof Number) return ((Number) val).intValue();
        return null;
    }

    private Double getDoubleValue(Map<String, Object> map, String key) {
        Object val = map.get(key);
        if (val instanceof Number) return ((Number) val).doubleValue();
        return null;
    }
}
