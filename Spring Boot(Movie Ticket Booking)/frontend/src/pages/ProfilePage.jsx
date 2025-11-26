import React, { useEffect, useState } from 'react'
import api from '../api'

function ProfilePage() {
  const [user, setUser] = useState(null)
  const [username, setUsername] = useState('')
  const [email, setEmail] = useState('')
  const [message, setMessage] = useState('')

  useEffect(() => {
    const load = async () => {
      try {
        const res = await api.get('/api/users/me')
        setUser(res.data)
        setUsername(res.data.username)
        setEmail(res.data.email)
      } catch (e) {
        console.error(e)
      }
    }
    load()
  }, [])

  const handleSubmit = async (e) => {
    e.preventDefault()
    try {
      const res = await api.put('/api/users/me', { username, email })
      setUser(res.data)
      setMessage('Profile updated successfully')
    } catch (e) {
      console.error(e)
      setMessage('Failed to update profile')
    }
  }

  if (!user) return <p>Loading profile...</p>

  return (
    <div className="row justify-content-center">
      <div className="col-md-6">
        <div className="card">
          <div className="card-header">
            <h3 className="text-center">Profile</h3>
          </div>
          <div className="card-body">
            {message && <div className="alert alert-info">{message}</div>}
            <form onSubmit={handleSubmit}>
              <div className="mb-3">
                <label className="form-label">Username</label>
                <input className="form-control" value={username} onChange={e => setUsername(e.target.value)} required />
              </div>
              <div className="mb-3">
                <label className="form-label">Email</label>
                <input type="email" className="form-control" value={email} onChange={e => setEmail(e.target.value)} required />
              </div>
              <button type="submit" className="btn btn-primary w-100">Save</button>
            </form>
          </div>
        </div>
      </div>
    </div>
  )
}

export default ProfilePage