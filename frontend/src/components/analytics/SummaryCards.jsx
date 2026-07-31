import React from 'react';
import { Disc, Tags, User, Star } from 'lucide-react';

export const SummaryCards = ({ totalAlbums, uniqueGenres, uniqueArtists, averageRating, topGenre, topArtist }) => {
  return (
    <div className="analytics-grid">
      <div className="stat-card">
        <div className="stat-icon purple">
          <Disc size={24} />
        </div>
        <div className="stat-value">{totalAlbums || 0}</div>
        <div className="stat-label">Saved Albums</div>
      </div>

      <div className="stat-card">
        <div className="stat-icon teal">
          <Tags size={24} />
        </div>
        <div className="stat-value">{uniqueGenres || 0}</div>
        <div className="stat-label">Genres ({topGenre !== 'N/A' ? topGenre : 'None'})</div>
      </div>

      <div className="stat-card">
        <div className="stat-icon pink">
          <User size={24} />
        </div>
        <div className="stat-value">{uniqueArtists || 0}</div>
        <div className="stat-label">Artists ({topArtist !== 'N/A' ? topArtist : 'None'})</div>
      </div>

      <div className="stat-card">
        <div className="stat-icon orange">
          <Star size={24} />
        </div>
        <div className="stat-value">{averageRating ? averageRating.toFixed(1) : '0.0'}</div>
        <div className="stat-label">Average Rating</div>
      </div>
    </div>
  );
};
