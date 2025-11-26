import React, { useEffect, useState } from 'react'
import { useParams, Link } from 'react-router-dom'
import api from '../api'

function BookingConfirmationPage() {
  const { id } = useParams()
  const [booking, setBooking] = useState(null)
  const [error, setError] = useState('')

  useEffect(() => {
  const load = async () => {
    if (!id) {
      setError('Invalid booking id')
      return
    }
    try {
      const res = await api.get(`/api/bookings/${id}`)
      setBooking(res.data)
    } catch (e) {
      console.error(e)
      setError('Failed to load booking details')
    }
  }
  load()
}, [id])

  if (error) return <div className="alert alert-danger">{error}</div>
  if (!booking) return <p>Loading booking confirmation...</p>

  const show = booking.show
  const movie = show.movie
  const screen = show.screen
  const theatre = screen.theatre

  return (
    <div className="row justify-content-center">
      <div className="col-md-8">
        <div className="card">
          <div className="card-header text-center">
            <h3>Booking Confirmation</h3>
          </div>
          <div className="card-body">
            <h4 className="mb-3">{movie.title}</h4>
            <p><strong>Theatre:</strong> {theatre.name} - {theatre.address}</p>
            <p><strong>City:</strong> {theatre.city?.name}</p>
            <p><strong>Screen:</strong> {screen.name}</p>
            <p><strong>Show Time:</strong> {new Date(show.showTime).toLocaleString()}</p>

            <hr />

            <p><strong>Booking ID:</strong> {booking.id}</p>
            <p><strong>Booking Time:</strong> {new Date(booking.bookingTime).toLocaleString()}</p>
            <p>
              <strong>Seats:</strong>{' '}
              {booking.seats?.map(bs => bs.seat.seatNumber).join(', ')}
            </p>
            <p><strong>Total Amount:</strong> Rs. {booking.totalAmount?.toFixed(2)}</p>

            <hr />

            <div className="row">
              <div className="col-md-6 text-center mb-3">
                <p><strong>QR Code (mock):</strong></p>
                <div className="border p-3">
                  <p>QR Path:</p>
                  <code>{booking.qrCodePath}</code>
                </div>
              </div>
              <div className="col-md-6 text-center mb-3">
                <p><strong>Ticket PDF (mock):</strong></p>
                <div className="border p-3">
                  <p>PDF Path:</p>
                  <code>{booking.pdfPath}</code>
                </div>
              </div>
            </div>

            <div className="text-center mt-4">
              <Link to="/my-bookings" className="btn btn-primary me-2">Go to My Bookings</Link>
              <Link to="/" className="btn btn-secondary">Back to Home</Link>
            </div>
          </div>
        </div>
      </div>
    </div>
  )
}

export default BookingConfirmationPage