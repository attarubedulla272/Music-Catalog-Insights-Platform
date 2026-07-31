import React from 'react';
import { PieChart, Pie, Cell, ResponsiveContainer, Tooltip, Legend } from 'recharts';
import { PieChart as PieIcon } from 'lucide-react';

const COLORS = ['#6c63ff', '#00d4aa', '#ff6b9d', '#ff9f43', '#ffd93d', '#a855f7', '#06b6d4', '#ec4899'];

export const GenreChart = ({ data }) => {
  if (!data || data.length === 0) {
    return (
      <div className="chart-card">
        <h3 className="chart-card-title"><PieIcon size={18} /> Genre Breakdown</h3>
        <p style={{ color: 'var(--text-muted)', textAlign: 'center', padding: '2rem' }}>No genre data available</p>
      </div>
    );
  }

  return (
    <div className="chart-card">
      <h3 className="chart-card-title"><PieIcon size={18} /> Genre Breakdown</h3>
      <div style={{ width: '100%', height: 260 }}>
        <ResponsiveContainer>
          <PieChart>
            <Pie
              data={data}
              cx="50%"
              cy="50%"
              innerRadius={55}
              outerRadius={85}
              paddingAngle={4}
              dataKey="count"
              nameKey="genre"
            >
              {data.map((entry, index) => (
                <Cell key={`cell-${index}`} fill={COLORS[index % COLORS.length]} />
              ))}
            </Pie>
            <Tooltip
              contentStyle={{
                background: 'var(--bg-secondary)',
                border: '1px solid var(--border-color)',
                borderRadius: 'var(--radius-md)',
                color: 'var(--text-primary)',
              }}
            />
            <Legend
              wrapperStyle={{ fontSize: '0.8rem', color: 'var(--text-secondary)' }}
              layout="horizontal"
              verticalAlign="bottom"
              align="center"
            />
          </PieChart>
        </ResponsiveContainer>
      </div>
    </div>
  );
};
