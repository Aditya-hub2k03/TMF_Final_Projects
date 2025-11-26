import React, { useEffect, useState } from 'react'
import { useLocation, useNavigate, useParams } from 'react-router-dom'
import api from '../api'

function BookingPage() {
  const { id } = useParams()
  const location = useLocation()
  const navigate = useNavigate()
  const showIdFromState = location.state?.showId
  const [show, setShow] = useState(null)
  const [seats, setSeats] = useState([])
  const [selected, setSelected] = useState([])
  const [error, setError] = useState('')

  useEffect(() => {
    const load = async () => {
      try {
        const showRes = await api.get(`/api/movies/${id}/shows`)
        const showToUse = showRes.data.find(s => s.id === showIdFromState) || showRes.data[0]
        setShow(showToUse)
        const seatsRes = await api.get(`/api/screens/${showToUse.screen.id}/seats`)
        setSeats(seatsRes.data)
      } catch (e) {
        console.error(e)
        setError('Failed to load show or seats')
      }
    }
    load()
  }, [id, showIdFromState])

  const toggleSeat = (seatId) => {
    setSelected(prev => prev.includes(seatId) ? prev.filter(id => id !== seatId) : [...prev, seatId])
  }

  const handleBook = async () => {
    if (!selected.length) {
      setError('Please select at least one seat')
      return
    }
    try {
      const res = await api.post('/api/bookings', {
        showId: show.id,
        seatIds: selected.map(String),
      })
      // Navigate to confirmation page with booking id
      navigate(`/booking/confirmation/${res.data.id}`)
    } catch (e) {
      console.error(e)
      setError(e.response?.data || 'Booking failed')
    }
  }

  if (!show) return <p>Loading...</p>

  return (
    <div>
      <h3>Booking for {show.movie.title} - {new Date(show.showTime).toLocaleString()}</h3>
      {error && <div className="alert alert-danger">{error}</div>}
      <div className="seat-layout">
        {seats.map(seat => (
          <button
            key={seat.id}
            className={
              'seat ' +
              (seat.available ? 'available ' : 'booked ') +
              (selected.includes(seat.id) ? 'selected' : '')
            }
            disabled={!seat.available}
            onClick={() => toggleSeat(seat.id)}
          >
            {seat.seatNumber}
          </button>
        ))}
      </div>
      <button className="btn btn-success mt-3" onClick={handleBook}>
        Confirm Booking ({selected.length} seats)
      </button>
    </div>
  )
}

export default BookingPage