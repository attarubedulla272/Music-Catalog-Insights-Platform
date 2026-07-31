import React, { useState, useEffect } from 'react';
import { SearchBar } from '../components/search/SearchBar';
import { AlbumCard } from '../components/search/AlbumCard';
import { LoadingSpinner } from '../components/common/LoadingSpinner';
import { EmptyState } from '../components/common/EmptyState';
import { searchCatalog } from '../api/searchApi';
import { addAlbumToLibrary, getLibrary } from '../api/libraryApi';
import { useDebounce } from '../hooks/useDebounce';
import { Search, Sparkles } from 'lucide-react';
import toast from 'react-hot-toast';

export const SearchPage = () => {
  const [searchTerm, setSearchTerm] = useState('Coldplay');
  const debouncedTerm = useDebounce(searchTerm, 350);
  const [results, setResults] = useState([]);
  const [loading, setLoading] = useState(false);
  const [savedCatalogIds, setSavedCatalogIds] = useState([]);
  const [savingId, setSavingId] = useState(null);

  // Fetch saved catalog IDs to show "Saved" badge
  useEffect(() => {
    fetchSavedIds();
  }, []);

  const fetchSavedIds = async () => {
    try {
      const data = await getLibrary(0, 100);
      const ids = data.content ? data.content.map((album) => album.appleCatalogId) : [];
      setSavedCatalogIds(ids);
    } catch (err) {
      console.error('Failed to fetch library catalog IDs:', err);
    }
  };

  // Perform debounced search against iTunes proxy API
  useEffect(() => {
    if (!debouncedTerm.trim()) {
      setResults([]);
      return;
    }

    const performSearch = async () => {
      setLoading(true);
      try {
        const data = await searchCatalog(debouncedTerm.trim(), 'album', 24);
        setResults(data.results || []);
      } catch (err) {
        toast.error('Failed to search iTunes catalog');
        setResults([]);
      } finally {
        setLoading(false);
      }
    };

    performSearch();
  }, [debouncedTerm]);

  const handleSaveAlbum = async (album) => {
    setSavingId(album.collectionId);
    try {
      await addAlbumToLibrary({
        appleCatalogId: album.collectionId,
        title: album.collectionName,
        artistName: album.artistName,
        genre: album.primaryGenreName,
        releaseDate: album.releaseDate,
        trackCount: album.trackCount,
        price: album.collectionPrice,
        artworkUrl: album.artworkUrl100,
      });

      toast.success(`Saved "${album.collectionName}" to your library!`);
      setSavedCatalogIds((prev) => [...prev, album.collectionId]);
    } catch (err) {
      toast.error(err.response?.data?.message || 'Failed to save album');
    } finally {
      setSavingId(null);
    }
  };

  return (
    <div>
      <div className="page-header" style={{ textAlign: 'center' }}>
        <h1 className="page-title">Explore Public Music Catalog</h1>
        <p className="page-subtitle">
          Search millions of albums on iTunes and save them to your personal collection
        </p>
      </div>

      <SearchBar
        value={searchTerm}
        onChange={setSearchTerm}
        onClear={() => setSearchTerm('')}
        placeholder="Search albums by name, artist, or genre (e.g. Coldplay, Taylor Swift, Rock)..."
      />

      {loading ? (
        <LoadingSpinner message="Searching iTunes catalog..." />
      ) : results.length > 0 ? (
        <div>
          <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '1rem' }}>
            <span style={{ fontSize: '0.9rem', color: 'var(--text-secondary)' }}>
              Found <strong>{results.length}</strong> albums for "{debouncedTerm}"
            </span>
          </div>

          <div className="album-grid">
            {results.map((album) => (
              <AlbumCard
                key={album.collectionId}
                album={album}
                isSaved={savedCatalogIds.includes(album.collectionId)}
                onSave={handleSaveAlbum}
                saving={savingId === album.collectionId}
              />
            ))}
          </div>
        </div>
      ) : debouncedTerm.trim() ? (
        <EmptyState
          icon={Search}
          title="No Albums Found"
          message={`No results matching "${debouncedTerm}". Try searching for another artist or album!`}
        />
      ) : (
        <EmptyState
          icon={Sparkles}
          title="Search iTunes Catalog"
          message="Type an artist, album title, or music genre above to get started."
        />
      )}
    </div>
  );
};
