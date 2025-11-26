import React, { useEffect, useState } from 'react'
import { Link } from 'react-router-dom'
import api from '../api'
import { getCurrentUser } from '../auth'

function TheatreDashboardPage() {
  const [theatres, setTheatres] = useState([])
  const [cities, setCities] = useState([])
  const [form, setForm] = useState({ name: '', address: '', cityId: '' })
  const user = getCurrentUser()

  const load = async () => {
    try {
      const [tRes, cRes] = await Promise.all([
        api.get('/api/theatre/theatres'),
        api.get('/api/admin/cities'),
      ])
      setTheatres(tRes.data)
      setCities(cRes.data)
    } catch (e) {
      console.error(e)
    }
  }

  useEffect(() => {
    load()
  }, [])

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
      await api.post('/api/theatre/theatres', {
        name: form.name,
        address: form.address,
        cityId: parseInt(form.cityId, 10),
      })
      setForm({ name: '', address: '', cityId: '' })
      load()
    } catch (e) {
      console.error(e)
    }
  }

  return (
    <div>
      <h2>Theatre Dashboard</h2>
      <form className="mb-4" onSubmit={handleSubmit}>
        <div className="row g-2">
          <div className="col-md-3">
            <input
              className="form-control"
              name="name"
              placeholder="Theatre name"
              value={form.name}
              onChange={handleChange}
              required
            />
          </div>
          <div className="col-md-4">
            <input
              className="form-control"
              name="address"
              placeholder="Address"
              value={form.address}
              onChange={handleChange}
              required
            />
          </div>
          <div className="col-md-3">
            <select
              className="form-select"
              name="cityId"
              value={form.cityId}
              onChange={handleChange}
              required
            >
              <option value="">Select City</option>
              {cities.map(c => (
                <option key={c.id} value={c.id}>{c.name}</option>
              ))}
            </select>
          </div>
          <div className="col-md-2">
            <button className="btn btn-primary w-100" type="submit">Add Theatre</button>
          </div>
        </div>
      </form>

      <div className="table-responsive">
        <table className="table table-striped">
          <thead>
            <tr>
              <th>Name</th>
              <th>Address</th>
              <th>City</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {theatres.map(t => (
              <tr key={t.id}>
                <td>{t.name}</td>
                <td>{t.address}</td>
                <td>{t.city?.name}</td>
                <td>
                  <Link to={`/theatre/theatres/${t.id}/screens`} className="btn btn-info btn-sm me-2">
                    Manage Screens
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

export default TheatreDashboardPage