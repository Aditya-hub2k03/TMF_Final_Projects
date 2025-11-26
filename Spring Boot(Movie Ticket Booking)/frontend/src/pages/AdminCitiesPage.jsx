import React, { useEffect, useState } from 'react'
import api from '../api'
import { getCurrentUser } from '../auth'

function AdminCitiesPage() {
  const [cities, setCities] = useState([])
  const [name, setName] = useState('')
  const [editingId, setEditingId] = useState(null)
  const [editingName, setEditingName] = useState('')
  const user = getCurrentUser()

  const load = async () => {
    try {
      const res = await api.get('/api/admin/cities')
      setCities(res.data)
    } catch (e) {
      console.error(e)
    }
  }

  useEffect(() => {
    load()
  }, [])

  if (!user || user.role !== 'APPLICATION_ADMIN') {
    return <p>You are not authorized to view this page.</p>
  }

  const addCity = async (e) => {
    e.preventDefault()
    try {
      await api.post('/api/admin/cities', { name })
      setName('')
      load()
    } catch (e) {
      console.error(e)
    }
  }

  const saveEdit = async (id) => {
    try {
      await api.put(`/api/admin/cities/${id}`, { name: editingName })
      setEditingId(null)
      setEditingName('')
      load()
    } catch (e) {
      console.error(e)
    }
  }

  const deleteCity = async (id) => {
    if (!confirm('Delete this city?')) return
    try {
      await api.delete(`/api/admin/cities/${id}`)
      load()
    } catch (e) {
      console.error(e)
    }
  }

  return (
    <div>
      <h2>Cities</h2>
      <form className="mb-3" onSubmit={addCity}>
        <div className="row g-2">
          <div className="col-md-6">
            <input
              className="form-control"
              placeholder="City name"
              value={name}
              onChange={e => setName(e.target.value)}
              required
            />
          </div>
          <div className="col-md-3">
            <button className="btn btn-primary w-100" type="submit">Add City</button>
          </div>
        </div>
      </form>

      <div className="table-responsive">
        <table className="table table-striped">
          <thead>
            <tr>
              <th>ID</th>
              <th>Name</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {cities.map(city => (
              <tr key={city.id}>
                <td>{city.id}</td>
                <td>
                  {editingId === city.id ? (
                    <input
                      className="form-control"
                      value={editingName}
                      onChange={e => setEditingName(e.target.value)}
                    />
                  ) : (
                    city.name
                  )}
                </td>
                <td>
                  {editingId === city.id ? (
                    <button className="btn btn-success btn-sm me-2" onClick={() => saveEdit(city.id)}>Save</button>
                  ) : (
                    <button
                      className="btn btn-warning btn-sm me-2"
                      onClick={() => { setEditingId(city.id); setEditingName(city.name) }}
                    >
                      Edit
                    </button>
                  )}
                  <button className="btn btn-danger btn-sm" onClick={() => deleteCity(city.id)}>Delete</button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  )
}

export default AdminCitiesPage