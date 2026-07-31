package com.musiclib.service;

import com.musiclib.model.dto.AnalyticsResponse;
import com.musiclib.repository.AlbumRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AnalyticsService {

    private final AlbumRepository albumRepository;

    public AnalyticsService(AlbumRepository albumRepository) {
        this.albumRepository = albumRepository;
    }

    public AnalyticsResponse getAnalytics(Long userId) {
        long totalAlbums = albumRepository.countByUserId(userId);
        Long uniqueGenres = albumRepository.getUniqueGenreCount(userId);
        Long uniqueArtists = albumRepository.getUniqueArtistCount(userId);
        Double avgRating = albumRepository.getAverageRating(userId);

        List<String> topGenres = albumRepository.getTopGenres(userId);
        List<String> topArtistNames = albumRepository.getTopArtistNames(userId);

        return AnalyticsResponse.builder()
                .totalAlbums(totalAlbums)
                .uniqueGenres(uniqueGenres != null ? uniqueGenres : 0L)
                .uniqueArtists(uniqueArtists != null ? uniqueArtists : 0L)
                .averageRating(avgRating != null ? Math.round(avgRating * 10.0) / 10.0 : 0.0)
                .topGenre(!topGenres.isEmpty() ? topGenres.get(0) : "N/A")
                .topArtist(!topArtistNames.isEmpty() ? topArtistNames.get(0) : "N/A")
                .genreDistribution(mapQueryResults(albumRepository.getGenreDistribution(userId), "genre", "count"))
                .decadeDistribution(mapDecadeResults(albumRepository.getDecadeDistribution(userId)))
                .ratingDistribution(mapQueryResults(albumRepository.getRatingDistribution(userId), "rating", "count"))
                .topArtists(limitList(mapQueryResults(albumRepository.getTopArtists(userId), "artist", "count"), 10))
                .monthlyAdditions(mapMonthlyResults(albumRepository.getMonthlyAdditions(userId)))
                .build();
    }

    private List<Map<String, Object>> mapQueryResults(List<Object[]> results, String keyName, String valueName) {
        return results.stream()
                .map(row -> {
                    Map<String, Object> map = new LinkedHashMap<>();
                    map.put(keyName, row[0] != null ? row[0].toString() : "Unknown");
                    map.put(valueName, ((Number) row[1]).longValue());
                    return map;
                })
                .collect(Collectors.toList());
    }

    private List<Map<String, Object>> mapDecadeResults(List<Object[]> results) {
        Map<String, Long> decadeMap = new LinkedHashMap<>();
        for (Object[] row : results) {
            if (row[0] != null) {
                int year = ((Number) row[0]).intValue();
                int decade = (year / 10) * 10;
                String decadeLabel = decade + "s";
                decadeMap.merge(decadeLabel, ((Number) row[1]).longValue(), Long::sum);
            }
        }

        return decadeMap.entrySet().stream()
                .map(entry -> {
                    Map<String, Object> map = new LinkedHashMap<>();
                    map.put("decade", entry.getKey());
                    map.put("count", entry.getValue());
                    return map;
                })
                .collect(Collectors.toList());
    }

    private List<Map<String, Object>> mapMonthlyResults(List<Object[]> results) {
        return results.stream()
                .map(row -> {
                    Map<String, Object> map = new LinkedHashMap<>();
                    int year = ((Number) row[0]).intValue();
                    int month = ((Number) row[1]).intValue();
                    map.put("month", String.format("%d-%02d", year, month));
                    map.put("count", ((Number) row[2]).longValue());
                    return map;
                })
                .collect(Collectors.toList());
    }

    private List<Map<String, Object>> limitList(List<Map<String, Object>> list, int max) {
        return list.stream().limit(max).collect(Collectors.toList());
    }
}
