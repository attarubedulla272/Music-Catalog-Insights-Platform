import React from 'react';
import { Star, Edit3, Trash2, Calendar, Music } from 'lucide-react';

export const LibraryCard = ({ album, onEdit, onDelete }) => {
  const artworkUrl = album.artworkUrl
    ? album.artworkUrl.replace('100x100bb', '300x300bb')
    : null;

  const releaseYear = album.releaseDate
    ? new Date(album.releaseDate).getFullYear()
    : null;

  return (
    <div className="album-card">
      <div className="album-card-artwork">
        {artworkUrl ? (
          <img src={artworkUrl} alt={album.title} loading="lazy" />
        ) : (
          <div className="empty-state-icon" style={{ width: '100%', height: '100%', borderRadius: 0 }}>
            <Music size={48} />
          </div>
        )}
        <div className="album-card-overlay">
          <div style={{ display: 'flex', gap: '0.5rem', width: '100%' }}>
            <button
              className="btn btn-secondary btn-sm"
              style={{ flex: 1 }}
              onClick={(e) => {
                e.stopPropagation();
                onEdit(album);
              }}
            >
              <Edit3 size={14} /> Edit
            </button>
            <button
              className="btn btn-danger btn-sm"
              onClick={(e) => {
                e.stopPropagation();
                onDelete(album.id);
              }}
              title="Remove from library"
            >
              <Trash2 size={14} />
            </button>
          </div>
        </div>
      </div>

      <div className="album-card-body">
        <h4 className="album-card-title" title={album.title}>
          {album.title}
        </h4>
        <p className="album-card-artist" title={album.artistName}>
          {album.artistName}
        </p>

        <div className="album-card-meta">
          {album.genre && (
            <span className="album-card-genre">{album.genre}</span>
          )}
          {releaseYear && (
            <span className="album-card-year" style={{ display: 'flex', alignItems: 'center', gap: '0.2rem' }}>
              <Calendar size={12} />
              {releaseYear}
            </span>
          )}
        </div>
      </div>

      <div className="album-card-rating">
        {[1, 2, 3, 4, 5].map((star) => (
          <Star
            key={star}
            size={16}
            className={`star ${(album.userRating || 0) >= star ? 'filled' : ''}`}
            onClick={() => onEdit(album)}
          />
        ))}
        <span style={{ fontSize: '0.75rem', color: 'var(--text-muted)', marginLeft: '0.4rem' }}>
          {album.userRating ? `${album.userRating}/5` : 'Unrated'}
        </span>
      </div>

      {album.userNotes && (
        <div style={{ padding: '0 1rem 0.75rem', fontSize: '0.78rem', color: 'var(--text-secondary)', fontStyle: 'italic' }}>
          "{album.userNotes.length > 50 ? album.userNotes.substring(0, 50) + '...' : album.userNotes}"
        </div>
      )}
    </div>
  );
};
