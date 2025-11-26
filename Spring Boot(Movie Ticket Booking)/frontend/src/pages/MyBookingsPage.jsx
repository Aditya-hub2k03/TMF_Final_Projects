import React, { useEffect, useState } from 'react'
import api from '../api'

function MyBookingsPage() {
  const [bookings, setBookings] = useState([])
  const [loading, setLoading] = useState(true)

  useEffect(() => {
    const load = async () => {
      try {
        const res = await api.get('/api/bookings')
        setBookings(res.data)
      } catch (e) {
        console.error(e)
      } finally {
        setLoading(false)
      }
    }
    load()
  }, [])

  if (loading) return <p>Loading bookings...</p>

  if (!bookings.length) return <p>No bookings yet.</p>

  return (
    <div>
      <h3>My Bookings</h3>
      <ul className="list-group">
        {bookings.map(b => (
          <li key={b.id} className="list-group-item">
            <div>
              <strong>Booking #{b.id}</strong> - {new Date(b.bookingTime).toLocaleString()}
            </div>
            <div>
              Total Amount: Rs. {b.totalAmount?.toFixed(2)}
            </div>
            <div>
              Seats: {b.seats?.map(s => s.seat.seatNumber).join(', ')}
            </div>
          </li>
        ))}
      </ul>
    </div>
  )
}

export default MyBookingsPage