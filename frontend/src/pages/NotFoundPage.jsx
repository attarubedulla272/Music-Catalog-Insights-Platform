import React from 'react';
import { Link } from 'react-router-dom';
import { Music2 } from 'lucide-react';

export const NotFoundPage = () => {
  return (
    <div className="empty-state" style={{ minHeight: '60vh' }}>
      <div className="empty-state-icon">
        <Music2 size={48} />
      </div>
      <h1 className="page-title" style={{ fontSize: '3rem', marginBottom: '0.5rem' }}>404</h1>
      <h3 className="empty-state-title">Page Not Found</h3>
      <p className="empty-state-text" style={{ marginBottom: '1.5rem' }}>
        The track or page you are looking for doesn't exist or has been moved.
      </p>
      <Link to="/library" className="btn btn-primary">
        Return to My Library
      </Link>
    </div>
  );
};
