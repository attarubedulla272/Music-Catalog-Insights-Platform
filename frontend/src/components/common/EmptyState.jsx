import React from 'react';
import { Disc3 } from 'lucide-react';

export const EmptyState = ({ icon: Icon = Disc3, title, message, action }) => {
  return (
    <div className="empty-state">
      <div className="empty-state-icon">
        <Icon size={40} />
      </div>
      <h3 className="empty-state-title">{title}</h3>
      <p className="empty-state-text">{message}</p>
      {action && <div style={{ marginTop: '1.5rem' }}>{action}</div>}
    </div>
  );
};
