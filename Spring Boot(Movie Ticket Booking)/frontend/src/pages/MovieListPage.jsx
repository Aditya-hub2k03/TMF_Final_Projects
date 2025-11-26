import React, { useEffect, useState } from 'react'
import { Link, useLocation } from 'react-router-dom'
import api from '../api'

function useQuery() {
  const { search } = useLocation()
  return React.useMemo(() => new URLSearchParams(search), [search])
}

function MovieListPage() {
  const [movies, setMovies] = useState([])
  const [loading, setLoading] = useState(true)
  const query = useQuery()
  const q = query.get('q') || ''

  useEffect(() => {
    const load = async () => {
      try {
        const res = await api.get('/api/movies')
        let data = res.data
        if (q) {
          const lower = q.toLowerCase()
          data = data.filter(m =>
            m.title.toLowerCase().includes(lower) ||
            (m.description && m.description.toLowerCase().includes(lower))
          )
        }
        setMovies(data)
      } catch (e) {
        console.error(e)
      } finally {
        setLoading(false)
      }
    }
    load()
  }, [q])

  if (loading) return <p>Loading movies...</p>

  return (
    <div className="row">
      {movies.map(movie => (
        <div className="col-md-4 mb-3" key={movie.id}>
          <div className="card h-100">
            {movie.posterUrl && <img src={movie.posterUrl} className="card-img-top" alt={movie.title} />}
            <div className="card-body d-flex flex-column">
              <h5 className="card-title">{movie.title}</h5>
              <p className="card-text flex-grow-1">{movie.description?.slice(0, 100)}...</p>
              <Link to={`/movies/${movie.id}`} className="btn btn-primary mt-2">View Shows</Link>
            </div>
          </div>
        </div>
      ))}
      {movies.length === 0 && (
        <div className="col-12">
          <p>No movies found.</p>
        </div>
      )}
    </div>
  )
}

export default MovieListPage