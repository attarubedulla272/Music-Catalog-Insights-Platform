import React, { useState } from 'react';
import { Sparkles, Plus, Check, RefreshCw } from 'lucide-react';
import { addAlbumToLibrary } from '../../api/libraryApi';
import toast from 'react-hot-toast';

export const RecommendationPanel = ({ data, onRefresh, savedCatalogIds, onAlbumAdded }) => {
  const [savingId, setSavingId] = useState(null);

  if (!data) return null;

  const { recommendations, summary } = data;

  const handleAdd = async (rec) => {
    setSavingId(rec.appleCatalogId);
    try {
      await addAlbumToLibrary({
        appleCatalogId: rec.appleCatalogId,
        title: rec.title,
        artistName: rec.artistName,
        genre: rec.genre,
        releaseDate: rec.releaseDate,
        trackCount: rec.trackCount,
        price: rec.price,
        artworkUrl: rec.artworkUrl,
      });
      toast.success(`Added "${rec.title}" to library!`);
      if (onAlbumAdded) onAlbumAdded(rec.appleCatalogId);
    } catch (err) {
      toast.error(err.response?.data?.message || 'Failed to add album');
    } finally {
      setSavingId(null);
    }
  };

  return (
    <div className="recommendation-panel">
      <div className="recommendation-header">
        <div style={{ display: 'flex', alignItems: 'center', gap: '0.75rem' }}>
          <div className="empty-state-icon" style={{ width: 40, height: 40, margin: 0 }}>
            <Sparkles size={20} color="var(--accent-primary)" />
          </div>
          <div>
            <h2 style={{ fontSize: '1.4rem', fontWeight: 800 }}>AI Recommendations Engine</h2>
            <p style={{ fontSize: '0.85rem', color: 'var(--text-secondary)' }}>
              Smart discoveries based on your library taste profile
            </p>
          </div>
        </div>

        {onRefresh && (
          <button className="btn btn-secondary btn-sm" onClick={onRefresh}>
            <RefreshCw size={14} /> Refresh AI Suggestions
          </button>
        )}
      </div>

      {summary && (
        <div className="recommendation-summary">
          <div className="recommendation-summary-icon">
            <Sparkles size={22} />
          </div>
          <div className="recommendation-summary-text">
            <strong>Taste Summary:</strong> {summary}
          </div>
        </div>
      )}

      {recommendations && recommendations.length > 0 ? (
        <div className="album-grid">
          {recommendations.map((rec) => {
            const isAlreadySaved = savedCatalogIds?.includes(rec.appleCatalogId);
            return (
              <div key={rec.appleCatalogId} className="album-card">
                <div className="album-card-artwork">
                  {rec.artworkUrl ? (
                    <img
                      src={rec.artworkUrl.replace('100x100bb', '300x300bb')}
                      alt={rec.title}
                      loading="lazy"
                    />
                  ) : (
                    <div className="empty-state-icon" style={{ width: '100%', height: '100%', borderRadius: 0 }}>
                      <Sparkles size={48} />
                    </div>
                  )}
                </div>

                <div className="album-card-body">
                  <h4 className="album-card-title" title={rec.title}>{rec.title}</h4>
                  <p className="album-card-artist" title={rec.artistName}>{rec.artistName}</p>

                  <div className="album-card-meta">
                    <span className="album-card-genre">{rec.genre || 'Music'}</span>
                  </div>

                  <span className="recommendation-reason">{rec.reason}</span>
                </div>

                <div className="album-card-actions">
                  <button
                    className={`btn ${isAlreadySaved ? 'btn-secondary' : 'btn-primary'} btn-sm`}
                    style={{ width: '100%' }}
                    onClick={() => !isAlreadySaved && handleAdd(rec)}
                    disabled={isAlreadySaved || savingId === rec.appleCatalogId}
                  >
                    {isAlreadySaved ? (
                      <><Check size={14} /> Saved</>
                    ) : savingId === rec.appleCatalogId ? (
                      'Saving...'
                    ) : (
                      <><Plus size={14} /> Add Recommendation</>
                    )}
                  </button>
                </div>
              </div>
            );
          })}
        </div>
      ) : (
        <p style={{ color: 'var(--text-muted)', textAlign: 'center', padding: '2rem' }}>
          No recommendations available yet. Try adding more albums to your library first!
        </p>
      )}
    </div>
  );
};
