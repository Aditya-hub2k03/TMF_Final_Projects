import React, { useState } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import { getCurrentUser, logout } from '../auth'

function Navbar() {
  const user = getCurrentUser()
  const navigate = useNavigate()
  const [search, setSearch] = useState('')

  const handleLogout = () => {
    logout()
    navigate('/login')
  }

  const handleSearch = (e) => {
    e.preventDefault()
    if (search.trim()) {
      navigate(`/?q=${encodeURIComponent(search.trim())}`)
    }
  }

  const roleLabel = user?.role === 'APPLICATION_ADMIN'
    ? 'Admin'
    : user?.role === 'THEATRE_ADMIN'
      ? 'Theatre Admin'
      : 'User'

  return (
    <nav className="navbar navbar-expand-lg navbar-dark bg-dark">
      <div className="container-fluid">
        <Link className="navbar-brand" to="/">Movie Ticket Booking</Link>
        <button className="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
          <span className="navbar-toggler-icon"></span>
        </button>
        <div className="collapse navbar-collapse" id="navbarNav">
          <ul className="navbar-nav me-auto">
            <li className="nav-item">
              <Link className="nav-link" to="/">Home</Link>
            </li>
            <li className="nav-item">
              <Link className="nav-link" to="/">Movies</Link>
            </li>
            {user && (
              <li className="nav-item">
                <Link className="nav-link" to="/my-bookings">My Bookings</Link>
              </li>
            )}
            {user && user.role === 'APPLICATION_ADMIN' && (
              <li className="nav-item">
                <Link className="nav-link" to="/admin/dashboard">Admin</Link>
              </li>
            )}
            {user && user.role === 'THEATRE_ADMIN' && (
              <li className="nav-item">
                <Link className="nav-link" to="/theatre/dashboard">Theatres</Link>
              </li>
            )}
          </ul>

          <form className="d-flex me-3" onSubmit={handleSearch}>
            <input
              className="form-control me-2"
              type="search"
              placeholder="Search movies..."
              value={search}
              onChange={e => setSearch(e.target.value)}
            />
            <button className="btn btn-outline-light" type="submit">
              Search
            </button>
          </form>

          <ul className="navbar-nav">
            {!user && (
              <>
                <li className="nav-item">
                  <Link className="nav-link" to="/login">Login</Link>
                </li>
                <li className="nav-item">
                  <Link className="nav-link" to="/register">Register</Link>
                </li>
              </>
            )}
            {user && (
              <li className="nav-item dropdown">
                <a className="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown">
                  Welcome, {user.username}
                  <span className="badge bg-secondary ms-2">{roleLabel}</span>
                </a>
                <ul className="dropdown-menu dropdown-menu-end">
                  <li>
                    <Link className="dropdown-item" to="/profile">Profile</Link>
                  </li>
                  <li>
                    <Link className="dropdown-item" to="/bank-details">Bank Details</Link>
                  </li>
                  {user.role === 'APPLICATION_ADMIN' && (
                    <>
                      <li><hr className="dropdown-divider" /></li>
                      <li>
                        <Link className="dropdown-item" to="/admin/dashboard">Admin Dashboard</Link>
                      </li>
                      <li>
                        <Link className="dropdown-item" to="/admin/cities">Manage Cities</Link>
                      </li>
                      <li>
                        <Link className="dropdown-item" to="/admin/promotions">Manage Promotions</Link>
                      </li>
                    </>
                  )}
                  {user.role === 'THEATRE_ADMIN' && (
                    <>
                      <li><hr className="dropdown-divider" /></li>
                      <li>
                        <Link className="dropdown-item" to="/theatre/dashboard">Theatre Dashboard</Link>
                      </li>
                    </>
                  )}
                  <li><hr className="dropdown-divider" /></li>
                  <li><button className="dropdown-item" onClick={handleLogout}>Logout</button></li>
                </ul>
              </li>
            )}
          </ul>
        </div>
      </div>
    </nav>
  )
}

export default Navbar