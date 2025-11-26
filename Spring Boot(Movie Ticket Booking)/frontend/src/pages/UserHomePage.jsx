import React from 'react'
import { Link } from 'react-router-dom'
import { getCurrentUser } from '../auth'

function UserHomePage() {
  const user = getCurrentUser()

  if (!user) {
    return (
      <div className="text-center">
        <h2>Welcome to YP Movie Ticket Booking</h2>
        <p>Please <Link to="/login">login</Link> or <Link to="/register">register</Link> to continue.</p>
      </div>
    )
  }

  return (
    <div>
      <h2 className="mb-4">Welcome, {user.username}!</h2>
      <div className="row">
        <div className="col-md-4 mb-3">
          <div className="card h-100">
            <div className="card-body d-flex flex-column">
              <h5 className="card-title">My Bookings</h5>
              <p className="card-text flex-grow-1">
                View all your past and upcoming bookings, including seat details and amounts.
              </p>
              <Link to="/my-bookings" className="btn btn-primary mt-2">View Bookings</Link>
            </div>
          </div>
        </div>
        <div className="col-md-4 mb-3">
          <div className="card h-100">
            <div className="card-body d-flex flex-column">
              <h5 className="card-title">Profile</h5>
              <p className="card-text flex-grow-1">
                Update your personal information like username and email.
              </p>
              <Link to="/profile" className="btn btn-primary mt-2">Go to Profile</Link>
            </div>
          </div>
        </div>
        <div className="col-md-4 mb-3">
          <div className="card h-100">
            <div className="card-body d-flex flex-column">
              <h5 className="card-title">Payment Methods</h5>
              <p className="card-text flex-grow-1">
                Manage your saved cards for quick checkout.
              </p>
              <Link to="/bank-details" className="btn btn-primary mt-2">Manage Cards</Link>
            </div>
          </div>
        </div>
      </div>

      <div className="row mt-3">
        <div className="col-md-4 mb-3">
          <div className="card h-100">
            <div className="card-body d-flex flex-column">
              <h5 className="card-title">Browse Movies</h5>
              <p className="card-text flex-grow-1">
                Explore all movies currently available and book your tickets.
              </p>
              <Link to="/" className="btn btn-secondary mt-2">Browse Movies</Link>
            </div>
          </div>
        </div>
        {user.role === 'APPLICATION_ADMIN' && (
          <div className="col-md-4 mb-3">
            <div className="card h-100">
              <div className="card-body d-flex flex-column">
                <h5 className="card-title">Admin Panel</h5>
                <p className="card-text flex-grow-1">
                  Manage cities, promotions, and user roles.
                </p>
                <Link to="/admin/dashboard" className="btn btn-warning mt-2">Admin Dashboard</Link>
              </div>
            </div>
          </div>
        )}
        {user.role === 'THEATRE_ADMIN' && (
          <div className="col-md-4 mb-3">
            <div className="card h-100">
              <div className="card-body d-flex flex-column">
                <h5 className="card-title">Theatre Panel</h5>
                <p className="card-text flex-grow-1">
                  Manage your theatres, screens, and shows.
                </p>
                <Link to="/theatre/dashboard" className="btn btn-info mt-2">Theatre Dashboard</Link>
              </div>
            </div>
          </div>
        )}
      </div>
    </div>
  )
}

export default UserHomePage