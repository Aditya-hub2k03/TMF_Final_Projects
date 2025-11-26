import React, { useEffect, useState } from 'react'
import { useParams } from 'react-router-dom'
import api from '../api'
import { getCurrentUser } from '../auth'

function TheatreShowsPage() {
  const { screenId } = useParams()
  const [shows, setShows] = useState([])
  const [movies, setMovies] = useState([])
  const [screen, setScreen] = useState(null)
  const [form, setForm] = useState({
    movieId: '',
    showTime: '',
    ticketPrice: 10.0,
  })
  const user = getCurrentUser()

  const load = async () => {
    try {
      const [sRes, shRes, mRes] = await Promise.all([
        api.get('/api/theatre/theatres'), // to resolve screen's theatre name not strictly needed
        api.get(`/api/theatre/screens/${screenId}/shows`),
        api.get('/api/movies'),
      ])
      setShows(shRes.data)
      setMovies(mRes.data)
      // screen info is not directly returned here; skip for now or derive from show
      if (shRes.data.length > 0) {
        setScreen(shRes.data[0].screen)
      }
    } catch (e) {
      console.error(e)
    }
  }

  useEffect(() => {
    load()
  }, [screenId])

  if (!user || (user.role !== 'THEATRE_ADMIN' && user.role !== 'APPLICATION_ADMIN')) {
    return <p>You are not authorized to view this page.</p>
  }

  const handleChange = (e) => {
    const { name, value } = e.target
    setForm(prev => ({ ...prev, [name]: value }))
  }

  const handleSubmit = async (e) => {
    e.preventDefault()
    try {
      await api.post(`/api/theatre/screens/${screenId}/shows`, {
        movieId: parseInt(form.movieId, 10),
        showTime: form.showTime,
        ticketPrice: parseFloat(form.ticketPrice),
      })
      setForm({ movieId: '', showTime: '', ticketPrice: 10.0 })
      load()
    } catch (e) {
      console.error(e)
    }
  }

  const deleteShow = async (id) => {
    if (!confirm('Delete this show?')) return
    try {
      await api.delete(`/api/theatre/shows/${id}`)
      load()
    } catch (e) {
      console.error(e)
    }
  }

  return (
    <div>
      <h2>Shows for Screen #{screenId}</h2>
      <form className="mb-3" onSubmit={handleSubmit}>
        <div className="row g-2">
          <div className="col-md-3">
            <select
              className="form-select"
              name="movieId"
              value={form.movieId}
              onChange={handleChange}
              required
            >
              <option value="">Select Movie</option>
              {movies.map(m => (
                <option key={m.id} value={m.id}>{m.title}</option>
              ))}
            </select>
          </div>
          <div className="col-md-4">
            <input
              className="form-control"
              name="showTime"
              type="datetime-local"
              value={form.showTime}
              onChange={handleChange}
              required
            />
          </div>
          <div className="col-md-2">
            <input
              className="form-control"
              name="ticketPrice"
              type="number"
              value={form.ticketPrice}
              onChange={handleChange}
              required
            />
          </div>
          <div className="col-md-2">
            <button className="btn btn-primary w-100" type="submit">Add Show</button>
          </div>
        </div>
      </form>

      <div className="table-responsive">
        <table className="table table-striped">
          <thead>
            <tr>
              <th>ID</th>
              <th>Movie</th>
              <th>Show Time</th>
              <th>Price</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {shows.map(sh => (
              <tr key={sh.id}>
                <td>{sh.id}</td>
                <td>{sh.movie?.title}</td>
                <td>{new Date(sh.showTime).toLocaleString()}</td>
                <td>{sh.ticketPrice}</td>
                <td>
                  <button className="btn btn-danger btn-sm" onClick={() => deleteShow(sh.id)}>Delete</button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  )
}

export default TheatreShowsPage