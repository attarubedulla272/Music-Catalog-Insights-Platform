import React from 'react';
import { BarChart, Bar, XAxis, YAxis, Tooltip, ResponsiveContainer, CartesianGrid } from 'recharts';
import { UserCheck } from 'lucide-react';

export const TopArtistsChart = ({ data }) => {
  if (!data || data.length === 0) {
    return (
      <div className="chart-card">
        <h3 className="chart-card-title"><UserCheck size={18} /> Top Artists</h3>
        <p style={{ color: 'var(--text-muted)', textAlign: 'center', padding: '2rem' }}>No artist data</p>
      </div>
    );
  }

  return (
    <div className="chart-card">
      <h3 className="chart-card-title"><UserCheck size={18} /> Top Artists in Library</h3>
      <div style={{ width: '100%', height: 260 }}>
        <ResponsiveContainer>
          <BarChart
            layout="vertical"
            data={data}
            margin={{ top: 10, right: 20, left: 20, bottom: 0 }}
          >
            <CartesianGrid strokeDasharray="3 3" stroke="rgba(255,255,255,0.05)" />
            <XAxis type="number" stroke="var(--text-secondary)" fontSize={12} allowDecimals={false} />
            <YAxis
              type="category"
              dataKey="artist"
              stroke="var(--text-secondary)"
              fontSize={11}
              width={100}
              tickFormatter={(val) => (val.length > 12 ? val.substring(0, 12) + '...' : val)}
            />
            <Tooltip
              contentStyle={{
                background: 'var(--bg-secondary)',
                border: '1px solid var(--border-color)',
                borderRadius: 'var(--radius-md)',
                color: 'var(--text-primary)',
              }}
            />
            <Bar dataKey="count" fill="var(--accent-pink)" radius={[0, 6, 6, 0]} />
          </BarChart>
        </ResponsiveContainer>
      </div>
    </div>
  );
};
