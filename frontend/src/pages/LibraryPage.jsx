import React, { useState, useEffect } from 'react';
import { getLibrary, updateLibraryAlbum, removeAlbumFromLibrary } from '../api/libraryApi';
import { LibraryCard } from '../components/library/LibraryCard';
import { EditAlbumModal } from '../components/library/EditAlbumModal';
import { LoadingSpinner } from '../components/common/LoadingSpinner';
import { EmptyState } from '../components/common/EmptyState';
import { Link } from 'react-router-dom';
import { Library, Plus, ChevronLeft, ChevronRight, ArrowUpDown } from 'lucide-react';
import toast from 'react-hot-toast';

export const LibraryPage = () => {
  const [albums, setAlbums] = useState([]);
  const [loading, setLoading] = useState(true);
  const [page, setPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);
  const [totalElements, setTotalElements] = useState(0);
  const [sortBy, setSortBy] = useState('createdAt');
  const [sortDir, setSortDir] = useState('desc');

  // Edit modal state
  const [selectedAlbum, setSelectedAlbum] = useState(null);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [updating, setUpdating] = useState(false);

  useEffect(() => {
    fetchLibrary();
  }, [page, sortBy, sortDir]);

  const fetchLibrary = async () => {
    setLoading(true);
    try {
      const data = await getLibrary(page, 12, sortBy, sortDir);
      setAlbums(data.content || []);
      setTotalPages(data.totalPages || 0);
      setTotalElements(data.totalElements || 0);
    } catch (err) {
      toast.error('Failed to load your library');
    } finally {
      setLoading(false);
    }
  };

  const handleEditClick = (album) => {
    setSelectedAlbum(album);
    setIsModalOpen(true);
  };

  const handleSaveReview = async (id, updateData) => {
    setUpdating(true);
    try {
      const updated = await updateLibraryAlbum(id, updateData);
      setAlbums((prev) => prev.map((a) => (a.id === id ? updated : a)));
      toast.success('Review saved!');
      setIsModalOpen(false);
    } catch (err) {
      toast.error('Failed to update review');
    } finally {
      setUpdating(false);
    }
  };

  const handleDeleteAlbum = async (id) => {
    if (!window.confirm('Remove this album from your library?')) return;
    
    // Optimistic UI update: instantly remove from screen
    setAlbums((prev) => prev.filter((a) => a.id !== id));
    setTotalElements((prev) => Math.max(0, prev - 1));

    try {
      await removeAlbumFromLibrary(id);
      toast.success('Album removed from library');
    } catch (err) {
      toast.error('Failed to remove album');
      fetchLibrary(); // Re-fetch to restore if backend deletion failed
    }
  };

  return (
    <div>
      <div className="page-header">
        <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', flexWrap: 'wrap', gap: '1rem' }}>
          <div>
            <h1 className="page-title">Personal Music Library</h1>
            <p className="page-subtitle">
              Manage your collection ({totalElements} {totalElements === 1 ? 'album' : 'albums'})
            </p>
          </div>
          <Link to="/search" className="btn btn-primary">
            <Plus size={18} /> Add New Album
          </Link>
        </div>
      </div>

      {totalElements > 0 && (
        <div className="toolbar">
          <div className="toolbar-left">
            <span style={{ fontSize: '0.85rem', color: 'var(--text-secondary)' }}>Sort by:</span>
            <select
              className="select"
              value={sortBy}
              onChange={(e) => { setSortBy(e.target.value); setPage(0); }}
            >
              <option value="createdAt">Date Added</option>
              <option value="userRating">Rating</option>
              <option value="title">Title</option>
              <option value="artistName">Artist</option>
              <option value="releaseDate">Release Date</option>
            </select>

            <button
              className="btn btn-secondary btn-sm"
              onClick={() => setSortDir((prev) => (prev === 'asc' ? 'desc' : 'asc'))}
              title="Toggle sort direction"
            >
              <ArrowUpDown size={14} /> {sortDir.toUpperCase()}
            </button>
          </div>
        </div>
      )}

      {loading ? (
        <LoadingSpinner message="Loading your library..." />
      ) : albums.length > 0 ? (
        <>
          <div className="album-grid">
            {albums.map((album) => (
              <LibraryCard
                key={album.id}
                album={album}
                onEdit={handleEditClick}
                onDelete={handleDeleteAlbum}
              />
            ))}
          </div>

          {totalPages > 1 && (
            <div className="pagination">
              <button
                className="pagination-btn"
                onClick={() => setPage((p) => Math.max(0, p - 1))}
                disabled={page === 0}
              >
                <ChevronLeft size={18} />
              </button>
              <span className="pagination-info">
                Page {page + 1} of {totalPages}
              </span>
              <button
                className="pagination-btn"
                onClick={() => setPage((p) => Math.min(totalPages - 1, p + 1))}
                disabled={page >= totalPages - 1}
              >
                <ChevronRight size={18} />
              </button>
            </div>
          )}
        </>
      ) : (
        <EmptyState
          icon={Library}
          title="Your Library is Empty"
          message="You haven't saved any albums yet. Explore the catalog to build your library!"
          action={
            <Link to="/search" className="btn btn-primary">
              <Plus size={18} /> Explore Music Catalog
            </Link>
          }
        />
      )}

      <EditAlbumModal
        album={selectedAlbum}
        isOpen={isModalOpen}
        onClose={() => setIsModalOpen(false)}
        onSave={handleSaveReview}
        loading={updating}
      />
    </div>
  );
};
