import React, { useState, useEffect } from 'react';
import { X, Star } from 'lucide-react';

export const EditAlbumModal = ({ album, isOpen, onClose, onSave, loading }) => {
  const [rating, setRating] = useState(album?.userRating || 0);
  const [notes, setNotes] = useState(album?.userNotes || '');

  useEffect(() => {
    if (album) {
      setRating(album.userRating || 0);
      setNotes(album.userNotes || '');
    }
  }, [album]);

  if (!isOpen || !album) return null;

  const handleSubmit = (e) => {
    e.preventDefault();
    onSave(album.id, { userRating: rating || null, userNotes: notes.trim() || null });
  };

  return (
    <div className="modal-backdrop" onClick={onClose}>
      <div className="modal" onClick={(e) => e.stopPropagation()}>
        <div className="modal-header">
          <h3 className="modal-title">Edit Album Review</h3>
          <button className="btn-ghost" onClick={onClose}>
            <X size={20} />
          </button>
        </div>

        <form onSubmit={handleSubmit}>
          <div className="modal-body">
            <div style={{ display: 'flex', gap: '1rem', alignItems: 'center' }}>
              {album.artworkUrl && (
                <img
                  src={album.artworkUrl}
                  alt={album.title}
                  style={{ width: 64, height: 64, borderRadius: 'var(--radius-md)', objectFit: 'cover' }}
                />
              )}
              <div>
                <h4 style={{ fontWeight: 700, fontSize: '1rem' }}>{album.title}</h4>
                <p style={{ color: 'var(--text-secondary)', fontSize: '0.85rem' }}>{album.artistName}</p>
              </div>
            </div>

            <div className="input-group">
              <label className="input-label">Your Rating</label>
              <div style={{ display: 'flex', gap: '0.5rem', alignItems: 'center' }}>
                {[1, 2, 3, 4, 5].map((star) => (
                  <Star
                    key={star}
                    size={28}
                    className={`star ${rating >= star ? 'filled' : ''}`}
                    onClick={() => setRating(star)}
                    style={{ cursor: 'pointer' }}
                  />
                ))}
                {rating > 0 && (
                  <button
                    type="button"
                    className="btn-ghost"
                    style={{ fontSize: '0.75rem', marginLeft: '0.5rem' }}
                    onClick={() => setRating(0)}
                  >
                    Clear
                  </button>
                )}
              </div>
            </div>

            <div className="input-group">
              <label className="input-label">Personal Notes / Review</label>
              <textarea
                className="input"
                rows={4}
                value={notes}
                onChange={(e) => setNotes(e.target.value)}
                placeholder="What did you think of this album? Favorite tracks, mood..."
              />
            </div>
          </div>

          <div className="modal-footer">
            <button type="button" className="btn btn-secondary" onClick={onClose}>
              Cancel
            </button>
            <button type="submit" className="btn btn-primary" disabled={loading}>
              {loading ? 'Saving...' : 'Save Review'}
            </button>
          </div>
        </form>
      </div>
    </div>
  );
};
