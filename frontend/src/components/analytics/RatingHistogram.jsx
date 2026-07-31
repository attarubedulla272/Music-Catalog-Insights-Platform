import React from 'react';
import { BarChart, Bar, XAxis, YAxis, Tooltip, ResponsiveContainer, CartesianGrid, Cell } from 'recharts';
import { Star } from 'lucide-react';

const RATING_COLORS = {
  '1': '#ef4444',
  '2': '#ff9f43',
  '3': '#ffd93d',
  '4': '#00d4aa',
  '5': '#6c63ff',
};

export const RatingHistogram = ({ data }) => {
  const formattedData = [1, 2, 3, 4, 5].map((star) => {
    const found = data?.find((d) => String(d.rating) === String(star));
    return {
      rating: `${star} Star${star > 1 ? 's' : ''}`,
      rawRating: String(star),
      count: found ? found.count : 0,
    };
  });

  return (
    <div className="chart-card">
      <h3 className="chart-card-title"><Star size={18} /> Rating Distribution</h3>
      <div style={{ width: '100%', height: 260 }}>
        <ResponsiveContainer>
          <BarChart data={formattedData} margin={{ top: 10, right: 10, left: -20, bottom: 0 }}>
            <CartesianGrid strokeDasharray="3 3" stroke="rgba(255,255,255,0.05)" />
            <XAxis dataKey="rating" stroke="var(--text-secondary)" fontSize={12} />
            <YAxis stroke="var(--text-secondary)" fontSize={12} allowDecimals={false} />
            <Tooltip
              contentStyle={{
                background: 'var(--bg-secondary)',
                border: '1px solid var(--border-color)',
                borderRadius: 'var(--radius-md)',
                color: 'var(--text-primary)',
              }}
            />
            <Bar dataKey="count" radius={[6, 6, 0, 0]}>
              {formattedData.map((entry, index) => (
                <Cell key={`cell-${index}`} fill={RATING_COLORS[entry.rawRating] || '#6c63ff'} />
              ))}
            </Bar>
          </BarChart>
        </ResponsiveContainer>
      </div>
    </div>
  );
};
