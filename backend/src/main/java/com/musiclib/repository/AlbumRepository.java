package com.musiclib.repository;

import com.musiclib.model.Album;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlbumRepository extends JpaRepository<Album, Long> {

    Page<Album> findByUserId(Long userId, Pageable pageable);

    List<Album> findByUserId(Long userId);

    Optional<Album> findByIdAndUserId(Long id, Long userId);

    boolean existsByUserIdAndAppleCatalogId(Long userId, Long appleCatalogId);

    long countByUserId(Long userId);

    // Analytics queries
    @Query("SELECT a.genre, COUNT(a) FROM Album a WHERE a.user.id = :userId AND a.genre IS NOT NULL GROUP BY a.genre ORDER BY COUNT(a) DESC")
    List<Object[]> getGenreDistribution(@Param("userId") Long userId);

    @Query("SELECT a.artistName, COUNT(a) FROM Album a WHERE a.user.id = :userId GROUP BY a.artistName ORDER BY COUNT(a) DESC")
    List<Object[]> getTopArtists(@Param("userId") Long userId);

    @Query("SELECT a.userRating, COUNT(a) FROM Album a WHERE a.user.id = :userId AND a.userRating IS NOT NULL GROUP BY a.userRating ORDER BY a.userRating")
    List<Object[]> getRatingDistribution(@Param("userId") Long userId);

    @Query("SELECT YEAR(a.releaseDate), COUNT(a) FROM Album a WHERE a.user.id = :userId AND a.releaseDate IS NOT NULL GROUP BY YEAR(a.releaseDate) ORDER BY YEAR(a.releaseDate)")
    List<Object[]> getDecadeDistribution(@Param("userId") Long userId);

    @Query("SELECT YEAR(a.createdAt), MONTH(a.createdAt), COUNT(a) FROM Album a WHERE a.user.id = :userId GROUP BY YEAR(a.createdAt), MONTH(a.createdAt) ORDER BY YEAR(a.createdAt), MONTH(a.createdAt)")
    List<Object[]> getMonthlyAdditions(@Param("userId") Long userId);

    @Query("SELECT AVG(a.userRating) FROM Album a WHERE a.user.id = :userId AND a.userRating IS NOT NULL")
    Double getAverageRating(@Param("userId") Long userId);

    @Query("SELECT COUNT(DISTINCT a.genre) FROM Album a WHERE a.user.id = :userId AND a.genre IS NOT NULL")
    Long getUniqueGenreCount(@Param("userId") Long userId);

    @Query("SELECT COUNT(DISTINCT a.artistName) FROM Album a WHERE a.user.id = :userId")
    Long getUniqueArtistCount(@Param("userId") Long userId);

    @Query("SELECT a.genre FROM Album a WHERE a.user.id = :userId AND a.genre IS NOT NULL GROUP BY a.genre ORDER BY COUNT(a) DESC")
    List<String> getTopGenres(@Param("userId") Long userId);

    @Query("SELECT a.artistName FROM Album a WHERE a.user.id = :userId GROUP BY a.artistName ORDER BY COUNT(a) DESC")
    List<String> getTopArtistNames(@Param("userId") Long userId);

    @Query("SELECT a.appleCatalogId FROM Album a WHERE a.user.id = :userId")
    List<Long> getAppleCatalogIdsByUserId(@Param("userId") Long userId);
}
