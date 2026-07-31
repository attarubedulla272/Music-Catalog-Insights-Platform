import React from 'react';
import { Plus, Check, Calendar, Disc } from 'lucide-react';

export const AlbumCard = ({ album, isSaved, onSave, saving }) => {
  const artworkUrl = album.artworkUrl100
    ? album.artworkUrl100.replace('100x100bb', '300x300bb')
    : album.artworkUrl;

  const releaseYear = album.releaseDate
    ? new Date(album.releaseDate).getFullYear()
    : null;

  return (
    <div className="album-card">
      <div className="album-card-artwork">
        {artworkUrl ? (
          <img src={artworkUrl} alt={album.collectionName || album.title} loading="lazy" />
        ) : (
          <div className="empty-state-icon" style={{ width: '100%', height: '100%', borderRadius: 0 }}>
            <Disc size={48} />
          </div>
        )}
        {isSaved && (
          <div style={{ position: 'absolute', top: '0.75rem', right: '0.75rem', zIndex: 2 }}>
            <span className="badge badge-saved">Saved</span>
          </div>
        )}
      </div>

      <div className="album-card-body">
        <h4 className="album-card-title" title={album.collectionName || album.title}>
          {album.collectionName || album.title}
        </h4>
        <p className="album-card-artist" title={album.artistName}>
          {album.artistName}
        </p>

        <div className="album-card-meta">
          {album.primaryGenreName || album.genre ? (
            <span className="album-card-genre">
              {album.primaryGenreName || album.genre}
            </span>
          ) : <span></span>}
          {releaseYear && (
            <span className="album-card-year" style={{ display: 'flex', alignItems: 'center', gap: '0.2rem' }}>
              <Calendar size={12} />
              {releaseYear}
            </span>
          )}
        </div>
      </div>

      <div className="album-card-actions">
        <button
          className={`btn ${isSaved ? 'btn-secondary' : 'btn-primary'} btn-sm`}
          style={{ width: '100%' }}
          onClick={() => !isSaved && onSave(album)}
          disabled={isSaved || saving}
        >
          {isSaved ? (
            <>
              <Check size={16} />
              In Library
            </>
          ) : (
            <>
              <Plus size={16} />
              Add to Library
            </>
          )}
        </button>
      </div>
    </div>
  );
};
