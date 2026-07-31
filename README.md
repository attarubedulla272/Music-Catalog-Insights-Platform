# MusicLib — Full-Stack Music Library & AI Insights Application

A full-stack web application that allows users to search the public iTunes Music Catalog, save selected albums to a personal library stored in MySQL, visualize rich analytics through interactive dashboards, and receive AI-driven album recommendations.

---

## Focus Choice: Albums

### Choice & Justification
Out of Albums, Songs, and Artists, **Albums** was chosen as the core entity focus.

**Why Albums?**
1. **Data Richness**: Albums in iTunes contain multi-dimensional metadata (title, artist, genre, release date, track count, price, artwork). This enables comprehensive visual analytics (genre breakdown donut charts, release decade bar charts, rating histograms, top artists, and monthly additions).
2. **Collectibility & Domain Fit**: Users naturally organize their music collections around full albums.
3. **Optimized AI Profiling**: An album-based library provides clean signals for genre and artist preferences, powering highly accurate recommendation algorithms.

---

## 1. Database & Schema

### Database Choice: MySQL (Relational SQL)

**Justification for SQL over NoSQL:**
- **Relational Integrity**: Saved library items belong strictly to authenticated users (`users` 1-to-N `albums`). Foreign keys with `ON DELETE CASCADE` ensure strict data consistency.
- **Analytics Performance**: Aggregations (`COUNT`, `GROUP BY genre`, `AVG(user_rating)`, `YEAR(release_date)`) are natively optimized in relational SQL databases.
- **ACID Compliance**: Prevents duplicate entry races (`UNIQUE KEY uk_user_album (user_id, apple_catalog_id)`).

### Schema Definition

```sql
-- Users Table
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Albums (Saved Library) Table
CREATE TABLE albums (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    apple_catalog_id BIGINT NOT NULL,
    title VARCHAR(255) NOT NULL,
    artist_name VARCHAR(255) NOT NULL,
    genre VARCHAR(100),
    release_date DATE,
    track_count INT,
    price DECIMAL(10,2),
    artwork_url VARCHAR(500),
    user_rating INT CHECK (user_rating BETWEEN 1 AND 5),
    user_notes TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT uk_user_album UNIQUE (user_id, apple_catalog_id)
);
```

---

## 2. REST API Documentation

Base URL: `http://localhost:8080/api`

### Authentication Endpoints (Public)
- `POST /api/auth/register` — Register a new account
- `POST /api/auth/login` — Login and receive a JWT Bearer token

### iTunes Catalog Proxy
- `GET /api/search?query={term}&type=album&limit=25` — Search public iTunes catalog with in-memory 5-min caching
- `GET /api/search/lookup/{id}` — Lookup catalog item by Apple ID

### User Library Endpoints (JWT Protected)
- `GET /api/library?page=0&size=12&sortBy=createdAt&sortDir=desc` — Paginated user library
- `POST /api/library` — Save album from catalog into MySQL library
- `PUT /api/library/{id}` — Update user rating (1-5) and personal review notes
- `DELETE /api/library/{id}` — Remove album from library
- `GET /api/library/{id}` — Get single saved album details

### Analytics & AI Endpoints (JWT Protected)
- `GET /api/analytics` — Get aggregated KPI summary + 5 dataset breakdowns for Recharts
- `GET /api/recommendations` — Fetch AI-generated album recommendations based on library profile

---

## 3. Analytics Dashboard (5 Visualizations)

Implemented using **Recharts** with responsive dark-mode aesthetics:
1. **Genre Breakdown (Donut/Pie Chart)**: Distribution of saved albums by primary genre.
2. **Albums by Decade (Vertical Bar Chart)**: Release timeline distribution grouped by decade (e.g. 1990s, 2000s, 2010s, 2020s).
3. **Rating Distribution (Histogram)**: Visual distribution of user ratings across 1 to 5 stars.
4. **Top Artists (Horizontal Bar Chart)**: Ranking of top artists in the user's library by album count.
5. **Library Additions Over Time (Line Chart)**: Monthly growth trajectory of saved albums.

---

## 4. AI Feature: Smart Recommendation Engine

### Overview
A rule-based AI recommendation system that analyzes the user's library taste profile (top genres, top artists, release eras) and queries the iTunes API for new, unsaved albums with similarity scoring.

### Algorithm Steps
1. **Taste Profiling**: Extracts top genres and top artists from the user's library.
2. **Multi-Strategy Catalog Querying**:
   - *Genre Affinity*: Fetches top albums matching user's favorite genres.
   - *Artist Similarity*: Searches for related titles from favorite artists.
   - *Discovery Picks*: Queries underrepresented genres in the user's library.
3. **Deduplication & Exclusion**: Excludes all `apple_catalog_id` items already present in the user's library.
4. **Scoring & Ranking**: Assigns a relevance score (0.0 to 1.0) and human-readable reasoning (e.g., *"Because you saved 5 Alternative albums"* or *"More from Coldplay"*).
5. **One-Click Add**: Allows adding any recommendation directly into the library in 1 click.

---

## 5. Technology Stack

- **Backend**: Java 17, Spring Boot 3.2, Spring Security (JWT), Spring Data JPA, Hibernate, Bean Validation, RestTemplate.
- **Frontend**: React 18, Vite, React Router DOM, Axios (with Bearer interceptor), Recharts, Lucide Icons, React Hot Toast.
- **Database**: MySQL 8.0 / H2 (for tests).
- **Styling**: Vanilla CSS with Design Tokens, Glassmorphism, CSS Grid/Flexbox, Skeleton Loaders, Responsive Media Queries.

---

## 6. Setup & Execution Instructions

### Prerequisites
- Java 17+
- Node.js 18+ and npm
- MySQL Server running on `localhost:3306` (Database: `musiclib`, User: `root`, Password: ``)

### 1. Database Setup
```sql
CREATE DATABASE IF NOT EXISTS musiclib;
```

### 2. Backend Execution
```bash
cd backend
mvn spring-boot:run
```
The backend API will start at `http://localhost:8080`.

### 3. Frontend Execution
```bash
cd frontend
npm install
npm run dev
```
The web application will launch at `http://localhost:5173`.

### 4. Running Backend Unit Tests
```bash
cd backend
mvn test
```

---

## 7. Trade-Offs & Architecture Decisions

1. **In-Memory iTunes Caching vs Redis**: Used a `ConcurrentHashMap` with 5-minute TTL inside `ITunesService` for caching iTunes API responses. This avoids external Redis setup overhead while preventing rate-limiting on repetitive searches.
2. **Rule-Based AI Engine vs External LLM API**: A rule-based scoring engine querying live iTunes endpoints was chosen because it delivers deterministic, zero-latency, key-free recommendations directly actionable by the user without requiring third-party AI keys.
3. **Explicit Java Boilerplate vs Lombok**: Used standard Java getters, setters, builders, and constructors to ensure 100% build reliability across any JDK version (including JDK 25/26).
