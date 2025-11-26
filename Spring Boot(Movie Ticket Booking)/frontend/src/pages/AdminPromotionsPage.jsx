import React, { useEffect, useState } from 'react'
import api from '../api'
import { getCurrentUser } from '../auth'

function AdminPromotionsPage() {
  const [promotions, setPromotions] = useState([])
  const [form, setForm] = useState({
    code: '',
    discountPercentage: 0,
    startDate: '',
    endDate: '',
  })
  const user = getCurrentUser()
  const [editingId, setEditingId] = useState(null)

  const load = async () => {
    try {
      const res = await api.get('/api/admin/promotions')
      setPromotions(res.data)
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

  const handleChange = (e) => {
    const { name, value } = e.target
    setForm(prev => ({ ...prev, [name]: value }))
  }

  const handleSubmit = async (e) => {
    e.preventDefault()
    try {
      await api.post('/api/admin/promotions', {
        code: form.code,
        discountPercentage: parseFloat(form.discountPercentage),
        startDate: form.startDate,
        endDate: form.endDate,
      })
      setForm({ code: '', discountPercentage: 0, startDate: '', endDate: '' })
      load()
    } catch (e) {
      console.error(e)
    }
  }

  const saveEdit = async (promo) => {
    try {
      await api.put(`/api/admin/promotions/${promo.id}`, promo)
      setEditingId(null)
      load()
    } catch (e) {
      console.error(e)
    }
  }

  const deletePromotion = async (id) => {
    if (!confirm('Delete this promotion?')) return
    try {
      await api.delete(`/api/admin/promotions/${id}`)
      load()
    } catch (e) {
      console.error(e)
    }
  }

  return (
    <div>
      <h2>Promotions</h2>
      <form className="mb-3" onSubmit={handleSubmit}>
        <div className="row g-2">
          <div className="col-md-3">
            <input
              className="form-control"
              placeholder="Code"
              name="code"
              value={form.code}
              onChange={handleChange}
              required
            />
          </div>
          <div className="col-md-2">
            <input
              className="form-control"
              placeholder="Discount %"
              name="discountPercentage"
              type="number"
              value={form.discountPercentage}
              onChange={handleChange}
              required
            />
          </div>
          <div className="col-md-3">
            <input
              className="form-control"
              placeholder="Start date (YYYY-MM-DD)"
              name="startDate"
              value={form.startDate}
              onChange={handleChange}
              required
            />
          </div>
          <div className="col-md-3">
            <input
              className="form-control"
              placeholder="End date (YYYY-MM-DD)"
              name="endDate"
              value={form.endDate}
              onChange={handleChange}
              required
            />
          </div>
          <div className="col-md-1">
            <button className="btn btn-primary w-100" type="submit">Add</button>
          </div>
        </div>
      </form>

      <div className="table-responsive">
        <table className="table table-striped">
          <thead>
            <tr>
              <th>ID</th>
              <th>Code</th>
              <th>Discount %</th>
              <th>Start</th>
              <th>End</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {promotions.map(p => (
              <tr key={p.id}>
                <td>{p.id}</td>
                <td>
                  {editingId === p.id ? (
                    <input
                      className="form-control"
                      value={p.code}
                      onChange={e => setPromotions(old => old.map(o => o.id === p.id ? { ...o, code: e.target.value } : o))}
                    />
                  ) : p.code}
                </td>
                <td>
                  {editingId === p.id ? (
                    <input
                      className="form-control"
                      type="number"
                      value={p.discountPercentage}
                      onChange={e => setPromotions(old => old.map(o => o.id === p.id ? { ...o, discountPercentage: e.target.value } : o))}
                    />
                  ) : p.discountPercentage}
                </td>
                <td>
                  {editingId === p.id ? (
                    <input
                      className="form-control"
                      value={p.startDate}
                      onChange={e => setPromotions(old => old.map(o => o.id === p.id ? { ...o, startDate: e.target.value } : o))}
                    />
                  ) : p.startDate}
                </td>
                <td>
                  {editingId === p.id ? (
                    <input
                      className="form-control"
                      value={p.endDate}
                      onChange={e => setPromotions(old => old.map(o => o.id === p.id ? { ...o, endDate: e.target.value } : o))}
                    />
                  ) : p.endDate}
                </td>
                <td>
                  {editingId === p.id ? (
                    <button className="btn btn-success btn-sm me-2" onClick={() => saveEdit(p)}>Save</button>
                  ) : (
                    <button className="btn btn-warning btn-sm me-2" onClick={() => setEditingId(p.id)}>Edit</button>
                  )}
                  <button className="btn btn-danger btn-sm" onClick={() => deletePromotion(p.id)}>Delete</button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  )
}

export default AdminPromotionsPage