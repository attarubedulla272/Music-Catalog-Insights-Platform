import React, { useState, useEffect } from 'react';
import { getAnalyticsData } from '../api/analyticsApi';
import { getRecommendations } from '../api/recommendationApi';
import { getLibrary } from '../api/libraryApi';
import { SummaryCards } from '../components/analytics/SummaryCards';
import { GenreChart } from '../components/analytics/GenreChart';
import { DecadeChart } from '../components/analytics/DecadeChart';
import { RatingHistogram } from '../components/analytics/RatingHistogram';
import { TopArtistsChart } from '../components/analytics/TopArtistsChart';
import { MonthlyGrowth } from '../components/analytics/MonthlyGrowth';
import { RecommendationPanel } from '../components/recommendations/RecommendationPanel';
import { LoadingSpinner } from '../components/common/LoadingSpinner';
import { EmptyState } from '../components/common/EmptyState';
import { Link } from 'react-router-dom';
import { BarChart3, Plus, RefreshCw } from 'lucide-react';
import toast from 'react-hot-toast';

export const AnalyticsPage = () => {
  const [analytics, setAnalytics] = useState(null);
  const [recommendations, setRecommendations] = useState(null);
  const [savedCatalogIds, setSavedCatalogIds] = useState([]);
  const [loading, setLoading] = useState(true);
  const [refreshingAi, setRefreshingAi] = useState(false);

  useEffect(() => {
    loadData();
  }, []);

  const loadData = async () => {
    setLoading(true);
    try {
      const [analyticsRes, recsRes, libraryRes] = await Promise.all([
        getAnalyticsData(),
        getRecommendations(),
        getLibrary(0, 100),
      ]);

      setAnalytics(analyticsRes);
      setRecommendations(recsRes);
      setSavedCatalogIds(
        libraryRes.content ? libraryRes.content.map((album) => album.appleCatalogId) : []
      );
    } catch (err) {
      toast.error('Failed to load analytics data');
    } finally {
      setLoading(false);
    }
  };

  const handleRefreshAi = async () => {
    setRefreshingAi(true);
    try {
      const recsRes = await getRecommendations();
      setRecommendations(recsRes);
      toast.success('Refreshed AI recommendations!');
    } catch (err) {
      toast.error('Failed to refresh AI suggestions');
    } finally {
      setRefreshingAi(false);
    }
  };

  const handleAlbumAdded = (newCatalogId) => {
    setSavedCatalogIds((prev) => [...prev, newCatalogId]);
    // Refresh analytics after adding
    getAnalyticsData().then(setAnalytics).catch(() => {});
  };

  if (loading) {
    return <LoadingSpinner message="Generating your music library analytics & AI insights..." />;
  }

  if (!analytics || analytics.totalAlbums === 0) {
    return (
      <div>
        <div className="page-header">
          <h1 className="page-title">Library Analytics & AI Insights</h1>
          <p className="page-subtitle">Visual breakdowns and personalized suggestions</p>
        </div>
        <EmptyState
          icon={BarChart3}
          title="Not Enough Data for Analytics"
          message="Save at least 1 album to your library to unlock charts and AI recommendations!"
          action={
            <Link to="/search" className="btn btn-primary">
              <Plus size={18} /> Search & Add Albums
            </Link>
          }
        />
      </div>
    );
  }

  return (
    <div>
      <div className="page-header">
        <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', flexWrap: 'wrap', gap: '1rem' }}>
          <div>
            <h1 className="page-title">Library Analytics & AI Insights</h1>
            <p className="page-subtitle">
              Deep analytics into your saved music catalog & rule-based recommendation engine
            </p>
          </div>
          <button className="btn btn-secondary btn-sm" onClick={loadData}>
            <RefreshCw size={14} /> Refresh Data
          </button>
        </div>
      </div>

      {/* Summary KPI Cards */}
      <SummaryCards
        totalAlbums={analytics.totalAlbums}
        uniqueGenres={analytics.uniqueGenres}
        uniqueArtists={analytics.uniqueArtists}
        averageRating={analytics.averageRating}
        topGenre={analytics.topGenre}
        topArtist={analytics.topArtist}
      />

      {/* 5 Visual Charts */}
      <div className="charts-grid" style={{ marginBottom: '2rem' }}>
        <GenreChart data={analytics.genreDistribution} />
        <DecadeChart data={analytics.decadeDistribution} />
        <RatingHistogram data={analytics.ratingDistribution} />
        <TopArtistsChart data={analytics.topArtists} />
        <MonthlyGrowth data={analytics.monthlyAdditions} />
      </div>

      {/* AI Recommendation Feature */}
      <RecommendationPanel
        data={recommendations}
        onRefresh={handleRefreshAi}
        savedCatalogIds={savedCatalogIds}
        onAlbumAdded={handleAlbumAdded}
      />
    </div>
  );
};
