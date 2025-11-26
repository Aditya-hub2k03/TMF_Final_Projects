import React, { useState } from 'react'
import { useNavigate, Link } from 'react-router-dom'
import api from '../api'
import { saveAuth } from '../auth'

function RegisterPage() {
  const [username, setUsername] = useState('')
  const [email, setEmail] = useState('')
  const [password, setPassword] = useState('')
  const [error, setError] = useState('')
  const navigate = useNavigate()

  const handleSubmit = async (e) => {
    e.preventDefault()
    setError('')
    try {
      const res = await api.post('/api/auth/register', { username, email, password })
      saveAuth(res.data)
      navigate('/')
    } catch (err) {
      setError(err.response?.data || 'Registration failed')
    }
  }

  return (
    <div className="row justify-content-center">
      <div className="col-md-6">
        <div className="card">
          <div className="card-header">
            <h3 className="text-center">Register</h3>
          </div>
          <div className="card-body">
            {error && <div className="alert alert-danger">{error}</div>}
            <form onSubmit={handleSubmit}>
              <div className="mb-3">
                <label className="form-label">Username</label>
                <input className="form-control" value={username} onChange={e => setUsername(e.target.value)} required />
              </div>
              <div className="mb-3">
                <label className="form-label">Email</label>
                <input type="email" className="form-control" value={email} onChange={e => setEmail(e.target.value)} required />
              </div>
              <div className="mb-3">
                <label className="form-label">Password</label>
                <input type="password" className="form-control" value={password} onChange={e => setPassword(e.target.value)} required />
              </div>
              <button type="submit" className="btn btn-primary w-100">Register</button>
            </form>
          </div>
          <div className="card-footer text-center">
            <p>Already have an account? <Link to="/login">Login</Link></p>
          </div>
        </div>
      </div>
    </div>
  )
}

export default RegisterPage