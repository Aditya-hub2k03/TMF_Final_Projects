import React, { useEffect, useState } from 'react'
import api from '../api'
import { getCurrentUser } from '../auth'
import { Link } from 'react-router-dom'

function AdminDashboardPage() {
  const [users, setUsers] = useState([])
  const user = getCurrentUser()

  useEffect(() => {
    const load = async () => {
      try {
        const res = await api.get('/api/admin/users')
        setUsers(res.data)
      } catch (e) {
        console.error(e)
      }
    }
    load()
  }, [])

  if (!user || user.role !== 'APPLICATION_ADMIN') {
    return <p>You are not authorized to view this page.</p>
  }

  const updateRole = async (id, role) => {
    try {
      await api.put(`/api/admin/users/${id}/role`, { role })
      const res = await api.get('/api/admin/users')
      setUsers(res.data)
    } catch (e) {
      console.error(e)
    }
  }

  return (
    <div>
      <h2>Admin Dashboard</h2>
      <div className="row mb-4">
        <div className="col-md-4">
          <div className="card">
            <div className="card-body">
              <h5 className="card-title">Cities</h5>
              <p className="card-text">Manage list of cities.</p>
              <Link to="/admin/cities" className="btn btn-primary">Manage Cities</Link>
            </div>
          </div>
        </div>
        <div className="col-md-4">
          <div className="card">
            <div className="card-body">
              <h5 className="card-title">Promotions</h5>
              <p className="card-text">Configure promotion codes.</p>
              <Link to="/admin/promotions" className="btn btn-primary">Manage Promotions</Link>
            </div>
          </div>
        </div>
      </div>

      <h3>Users</h3>
      <div className="table-responsive">
        <table className="table table-striped">
          <thead>
            <tr>
              <th>User ID</th>
              <th>Username</th>
              <th>Email</th>
              <th>Role</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {users.map(u => (
              <tr key={u.id}>
                <td>{u.id}</td>
                <td>{u.username}</td>
                <td>{u.email}</td>
                <td>{u.role}</td>
                <td>
                  <div className="btn-group btn-group-sm">
                    <button className="btn btn-outline-secondary" onClick={() => updateRole(u.id, 'NORMAL_USER')}>User</button>
                    <button className="btn btn-outline-secondary" onClick={() => updateRole(u.id, 'THEATRE_ADMIN')}>Theatre Admin</button>
                    <button className="btn btn-outline-secondary" onClick={() => updateRole(u.id, 'APPLICATION_ADMIN')}>Admin</button>
                  </div>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  )
}

export default AdminDashboardPage