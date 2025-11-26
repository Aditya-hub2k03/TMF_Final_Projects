// MovieGrid.js
import React, { useState, useEffect } from "react";
import { fetchMovies, fetchGenres } from "./data";

const MovieGrid = () => {
  const [movies, setMovies] = useState([]);
  const [genres, setGenres] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [filter, setFilter] = useState("now_playing");

  useEffect(() => {
    const loadMovies = async () => {
      setLoading(true);
      try {
        const moviesData = await fetchMovies(filter);
        const genresData = await fetchGenres();
        setMovies(moviesData);
        setGenres(genresData);
      } catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };

    loadMovies();
  }, [filter]);

  const getGenreNames = (genreIds) => {
    return genreIds
      .map((id) => genres.find((g) => g.id === id)?.name)
      .filter(Boolean)
      .join(", ");
  };

  if (loading) return <div>Loading...</div>;
  if (error) return <div>Error: {error}</div>;

  return (
    <section className="movies">
      <div className="section-head">
        <h2>Now Showing</h2>
        <div className="filters">
          <div className="chip" onClick={() => setFilter("now_playing")}>
            Now Showing
          </div>
          <div className="chip" onClick={() => setFilter("top_rated")}>
            Top Rated
          </div>
          <div className="chip" onClick={() => setFilter("popular")}>
            Popular
          </div>
        </div>
      </div>

      <div className="movies-grid">
        {movies.map((movie) => (
          <article key={movie.id} className="card">
            <a href="#" className="poster">
              <img
                src={`https://image.tmdb.org/t/p/w500${movie.poster_path}`}
                alt={movie.title}
              />
              <div className="rating">★ {movie.vote_average}</div>
            </a>
            <div className="meta">
              <strong>{movie.title}</strong>
              <p>
                {getGenreNames(movie.genre_ids)} ·{" "}
                {Math.floor(movie.runtime / 60)}h {movie.runtime % 60}m ·
                English
              </p>
            </div>
            <div className="actions">
              <button>Details</button>
              <button className="book">Book</button>
            </div>
          </article>
        ))}
      </div>
    </section>
  );
};

export default MovieGrid;
