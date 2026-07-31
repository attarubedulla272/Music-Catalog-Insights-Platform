import React from 'react';
import { Search, X } from 'lucide-react';

export const SearchBar = ({ value, onChange, onClear, placeholder = 'Search albums, artists, or genres...' }) => {
  return (
    <div className="search-container">
      <div className="search-input-wrapper">
        <Search size={20} />
        <input
          type="text"
          className="search-input"
          value={value}
          onChange={(e) => onChange(e.target.value)}
          placeholder={placeholder}
          autoFocus
        />
        {value && (
          <button
            onClick={onClear}
            style={{
              position: 'absolute',
              right: '1.25rem',
              color: 'var(--text-muted)',
              cursor: 'pointer',
            }}
          >
            <X size={18} />
          </button>
        )}
      </div>
    </div>
  );
};
