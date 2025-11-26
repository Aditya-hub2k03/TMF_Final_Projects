import React, { useEffect, useState } from 'react'
import { useParams, Link } from 'react-router-dom'
import api from '../api'
import { getCurrentUser } from '../auth'

function TheatreScreensPage() {
  const { theatreId } = useParams()
  const [screens, setScreens] = useState([])
  const [theatre, setTheatre] = useState(null)
  const [form, setForm] = useState({ name: '', totalSeats: 50 })
  const user = getCurrentUser()

  const load = async () => {
    try {
      const [tRes, sRes] = await Promise.all([
        api.get('/api/admin/theatres'),
        api.get(`/api/theatre/theatres/${theatreId}/screens`),
      ])
      setTheatre(tRes.data.find(t => t.id === parseInt(theatreId, 10)))
      setScreens(sRes.data)
    } catch (e) {
      console.error(e)
    }
  }

  useEffect(() => {
    load()
  }, [theatreId])

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
      await api.post(`/api/theatre/theatres/${theatreId}/screens`, {
        name: form.name,
        totalSeats: parseInt(form.totalSeats, 10),
      })
      setForm({ name: '', totalSeats: 50 })
      load()
    } catch (e) {
      console.error(e)
    }
  }

  return (
    <div>
      <h2>Screens for {theatre ? theatre.name : '...'}</h2>
      <form className="mb-3" onSubmit={handleSubmit}>
        <div className="row g-2">
          <div className="col-md-5">
            <input
              className="form-control"
              name="name"
              placeholder="Screen name"
              value={form.name}
              onChange={handleChange}
              required
            />
          </div>
          <div className="col-md-3">
            <input
              className="form-control"
              name="totalSeats"
              type="number"
              placeholder="Total seats"
              value={form.totalSeats}
              onChange={handleChange}
              required
            />
          </div>
          <div className="col-md-2">
            <button className="btn btn-primary w-100" type="submit">Add Screen</button>
          </div>
        </div>
      </form>

      <div className="table-responsive">
        <table className="table table-striped">
          <thead>
            <tr>
              <th>Name</th>
              <th>Total Seats</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {screens.map(s => (
              <tr key={s.id}>
                <td>{s.name}</td>
                <td>{s.totalSeats}</td>
                <td>
                  <Link to={`/theatre/screens/${s.id}/shows`} className="btn btn-info btn-sm me-2">
                    Manage Shows
                  </Link>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  )
}

export default TheatreScreensPage