import React, { useEffect, useState } from 'react'
import api from '../api'

function BankDetailsPage() {
  const [bankDetails, setBankDetails] = useState([])
  const [form, setForm] = useState({
    cardNumber: '',
    cardHolderName: '',
    expiryDate: '',
    cvv: '',
  })
  const [message, setMessage] = useState('')

  const load = async () => {
    try {
      const res = await api.get('/api/users/me/bank-details')
      setBankDetails(res.data)
    } catch (e) {
      console.error(e)
    }
  }

  useEffect(() => {
    load()
  }, [])

  const handleChange = (e) => {
    const { name, value } = e.target
    setForm(prev => ({ ...prev, [name]: value }))
  }

  const handleSubmit = async (e) => {
    e.preventDefault()
    try {
      await api.post('/api/users/me/bank-details', form)
      setMessage('Bank details added')
      setForm({ cardNumber: '', cardHolderName: '', expiryDate: '', cvv: '' })
      load()
    } catch (e) {
      console.error(e)
      setMessage('Failed to add bank details')
    }
  }

  const handleDelete = async (id) => {
    try {
      await api.delete(`/api/users/me/bank-details/${id}`)
      load()
    } catch (e) {
      console.error(e)
    }
  }

  return (
    <div className="row">
      <div className="col-md-6">
        <div className="card mb-4">
          <div className="card-header">
            <h3 className="text-center">Add Bank Details</h3>
          </div>
          <div className="card-body">
            {message && <div className="alert alert-info">{message}</div>}
            <form onSubmit={handleSubmit}>
              <div className="mb-3">
                <label className="form-label">Card Number</label>
                <input
                  name="cardNumber"
                  className="form-control"
                  value={form.cardNumber}
                  onChange={handleChange}
                  required
                />
              </div>
              <div className="mb-3">
                <label className="form-label">Card Holder Name</label>
                <input
                  name="cardHolderName"
                  className="form-control"
                  value={form.cardHolderName}
                  onChange={handleChange}
                  required
                />
              </div>
              <div className="mb-3">
                <label className="form-label">Expiry Date (MM/YYYY)</label>
                <input
                  name="expiryDate"
                  className="form-control"
                  value={form.expiryDate}
                  onChange={handleChange}
                  required
                />
              </div>
              <div className="mb-3">
                <label className="form-label">CVV</label>
                <input
                  name="cvv"
                  className="form-control"
                  value={form.cvv}
                  onChange={handleChange}
                  required
                />
              </div>
              <button type="submit" className="btn btn-primary w-100">Save</button>
            </form>
          </div>
        </div>
      </div>
      <div className="col-md-6">
        <h3>Saved Cards</h3>
        {bankDetails.length === 0 && <p>No bank details saved.</p>}
        <ul className="list-group">
          {bankDetails.map(bd => (
            <li key={bd.id} className="list-group-item d-flex justify-content-between align-items-center">
              <div>
                <div>{bd.cardHolderName}</div>
                <div>**** **** **** {bd.cardNumber.slice(-4)}</div>
                <div>Exp: {bd.expiryDate}</div>
              </div>
              <button className="btn btn-danger btn-sm" onClick={() => handleDelete(bd.id)}>Delete</button>
            </li>
          ))}
        </ul>
      </div>
    </div>
  )
}

export default BankDetailsPage