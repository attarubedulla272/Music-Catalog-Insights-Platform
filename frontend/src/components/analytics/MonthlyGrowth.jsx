import React from 'react';
import { LineChart, Line, XAxis, YAxis, Tooltip, ResponsiveContainer, CartesianGrid } from 'recharts';
import { TrendingUp } from 'lucide-react';

export const MonthlyGrowth = ({ data }) => {
  if (!data || data.length === 0) {
    return (
      <div className="chart-card">
        <h3 className="chart-card-title"><TrendingUp size={18} /> Library Additions Over Time</h3>
        <p style={{ color: 'var(--text-muted)', textAlign: 'center', padding: '2rem' }}>No activity data</p>
      </div>
    );
  }

  return (
    <div className="chart-card">
      <h3 className="chart-card-title"><TrendingUp size={18} /> Library Additions Over Time</h3>
      <div style={{ width: '100%', height: 260 }}>
        <ResponsiveContainer>
          <LineChart data={data} margin={{ top: 10, right: 10, left: -20, bottom: 0 }}>
            <CartesianGrid strokeDasharray="3 3" stroke="rgba(255,255,255,0.05)" />
            <XAxis dataKey="month" stroke="var(--text-secondary)" fontSize={12} />
            <YAxis stroke="var(--text-secondary)" fontSize={12} allowDecimals={false} />
            <Tooltip
              contentStyle={{
                background: 'var(--bg-secondary)',
                border: '1px solid var(--border-color)',
                borderRadius: 'var(--radius-md)',
                color: 'var(--text-primary)',
              }}
            />
            <Line
              type="monotone"
              dataKey="count"
              stroke="var(--accent-primary)"
              strokeWidth={3}
              dot={{ fill: 'var(--accent-primary)', r: 5 }}
              activeDot={{ r: 8, fill: 'var(--accent-secondary)' }}
            />
          </LineChart>
        </ResponsiveContainer>
      </div>
    </div>
  );
};
