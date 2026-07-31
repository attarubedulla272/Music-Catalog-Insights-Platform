package com.musiclib.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ITunesService {

    private static final Logger log = LoggerFactory.getLogger(ITunesService.class);

    @Value("${itunes.api.base-url}")
    private String baseUrl;

    private final RestTemplate restTemplate;

    private final Map<String, CacheEntry> cache = new ConcurrentHashMap<>();
    private static final long CACHE_TTL_MS = 5 * 60 * 1000;

    public ITunesService() {
        this.restTemplate = new RestTemplate();
        // iTunes API returns Content-Type: text/javascript instead of application/json.
        // Configure Jackson converter to also accept text/javascript.
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(Arrays.asList(
                MediaType.APPLICATION_JSON,
                new MediaType("text", "javascript")
        ));
        this.restTemplate.getMessageConverters().add(0, converter);
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> search(String query, String entity, Integer limit) {
        String cacheKey = query + ":" + entity + ":" + limit;

        CacheEntry cached = cache.get(cacheKey);
        if (cached != null && !cached.isExpired()) {
            log.info("Cache hit for iTunes search: {}", cacheKey);
            return cached.getData();
        }

        String url = UriComponentsBuilder.fromHttpUrl(baseUrl + "/search")
                .queryParam("term", query)
                .queryParam("entity", entity != null ? entity : "album")
                .queryParam("limit", limit != null ? limit : 25)
                .build()
                .toUriString();

        log.info("iTunes API search: {}", url);

        try {
            Map<String, Object> response = restTemplate.getForObject(url, Map.class);
            if (response != null) {
                cache.put(cacheKey, new CacheEntry(response));
            }
            return response != null ? response : Collections.emptyMap();
        } catch (Exception e) {
            log.error("iTunes API error: {}", e.getMessage());
            throw new RuntimeException("Failed to fetch from iTunes API: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> lookup(Long id) {
        String cacheKey = "lookup:" + id;

        CacheEntry cached = cache.get(cacheKey);
        if (cached != null && !cached.isExpired()) {
            return cached.getData();
        }

        String url = UriComponentsBuilder.fromHttpUrl(baseUrl + "/lookup")
                .queryParam("id", id)
                .build()
                .toUriString();

        log.info("iTunes API lookup: {}", url);

        try {
            Map<String, Object> response = restTemplate.getForObject(url, Map.class);
            if (response != null) {
                cache.put(cacheKey, new CacheEntry(response));
            }
            return response != null ? response : Collections.emptyMap();
        } catch (Exception e) {
            log.error("iTunes API lookup error: {}", e.getMessage());
            throw new RuntimeException("Failed to fetch from iTunes API: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> searchAlbums(String query, int limit) {
        Map<String, Object> response = search(query, "album", limit);
        Object results = response.get("results");
        if (results instanceof List) {
            return (List<Map<String, Object>>) results;
        }
        return Collections.emptyList();
    }

    private static class CacheEntry {
        private final Map<String, Object> data;
        private final long timestamp;

        CacheEntry(Map<String, Object> data) {
            this.data = data;
            this.timestamp = System.currentTimeMillis();
        }

        boolean isExpired() {
            return System.currentTimeMillis() - timestamp > CACHE_TTL_MS;
        }

        Map<String, Object> getData() {
            return data;
        }
    }
}
