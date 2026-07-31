import React from 'react';
import { BarChart, Bar, XAxis, YAxis, Tooltip, ResponsiveContainer, CartesianGrid } from 'recharts';
import { Calendar } from 'lucide-react';

export const DecadeChart = ({ data }) => {
  if (!data || data.length === 0) {
    return (
      <div className="chart-card">
        <h3 className="chart-card-title"><Calendar size={18} /> Albums by Decade</h3>
        <p style={{ color: 'var(--text-muted)', textAlign: 'center', padding: '2rem' }}>No release date data</p>
      </div>
    );
  }

  return (
    <div className="chart-card">
      <h3 className="chart-card-title"><Calendar size={18} /> Albums by Decade</h3>
      <div style={{ width: '100%', height: 260 }}>
        <ResponsiveContainer>
          <BarChart data={data} margin={{ top: 10, right: 10, left: -20, bottom: 0 }}>
            <CartesianGrid strokeDasharray="3 3" stroke="rgba(255,255,255,0.05)" />
            <XAxis dataKey="decade" stroke="var(--text-secondary)" fontSize={12} />
            <YAxis stroke="var(--text-secondary)" fontSize={12} allowDecimals={false} />
            <Tooltip
              contentStyle={{
                background: 'var(--bg-secondary)',
                border: '1px solid var(--border-color)',
                borderRadius: 'var(--radius-md)',
                color: 'var(--text-primary)',
              }}
            />
            <Bar dataKey="count" fill="var(--accent-secondary)" radius={[6, 6, 0, 0]} />
          </BarChart>
        </ResponsiveContainer>
      </div>
    </div>
  );
};
