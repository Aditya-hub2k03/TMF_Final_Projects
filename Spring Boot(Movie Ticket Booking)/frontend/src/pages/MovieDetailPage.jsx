import React, { useEffect, useState } from 'react'
import { useParams, Link } from 'react-router-dom'
import api from '../api'

function MovieDetailPage() {
  const { id } = useParams()
  const [movie, setMovie] = useState(null)
  const [shows, setShows] = useState([])
  const [loading, setLoading] = useState(true)

  useEffect(() => {
    const load = async () => {
      try {
        const [m, s] = await Promise.all([
          api.get(`/api/movies/${id}`),
          api.get(`/api/movies/${id}/shows`),
        ])
        setMovie(m.data)
        setShows(s.data)
      } catch (e) {
        console.error(e)
      } finally {
        setLoading(false)
      }
    }
    load()
  }, [id])

  if (loading) return <p>Loading...</p>
  if (!movie) return <p>Movie not found</p>

  return (
    <div>
      <h2>{movie.title}</h2>
      <p>{movie.description}</p>
      <h4>Available Shows</h4>
      <ul className="list-group">
        {shows.map(show => (
          <li key={show.id} className="list-group-item d-flex justify-content-between align-items-center">
            <span>{new Date(show.showTime).toLocaleString()} - Rs. {show.ticketPrice}</span>
            <Link to={`/movies/${movie.id}/book`} state={{ showId: show.id }} className="btn btn-success btn-sm">
              Book
            </Link>
          </li>
        ))}
      </ul>
    </div>
  )
}

export default MovieDetailPage