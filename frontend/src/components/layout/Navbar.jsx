import React from 'react';
import { NavLink, useNavigate } from 'react-router-dom';
import { useAuth } from '../../hooks/useAuth';
import { Music2, Search, Library, BarChart3, LogOut, User as UserIcon } from 'lucide-react';

export const Navbar = () => {
  const { user, logout, isAuthenticated } = useAuth();
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    navigate('/login');
  };

  if (!isAuthenticated) return null;

  return (
    <nav className="navbar">
      <NavLink to="/search" className="navbar-brand">
        <Music2 size={28} />
        <span>MusicLib</span>
      </NavLink>

      <div className="navbar-links">
        <NavLink to="/search" className={({ isActive }) => `nav-link ${isActive ? 'active' : ''}`}>
          <Search size={18} />
          <span>Search</span>
        </NavLink>
        <NavLink to="/library" className={({ isActive }) => `nav-link ${isActive ? 'active' : ''}`}>
          <Library size={18} />
          <span>My Library</span>
        </NavLink>
        <NavLink to="/analytics" className={({ isActive }) => `nav-link ${isActive ? 'active' : ''}`}>
          <BarChart3 size={18} />
          <span>Analytics & AI</span>
        </NavLink>
      </div>

      <div className="navbar-user">
        <div style={{ display: 'flex', alignItems: 'center', gap: '0.5rem' }}>
          <UserIcon size={16} color="var(--text-secondary)" />
          <span className="navbar-username">{user?.username}</span>
        </div>
        <button onClick={handleLogout} className="btn-logout" title="Log out">
          <LogOut size={16} />
          <span>Logout</span>
        </button>
      </div>
    </nav>
  );
};
