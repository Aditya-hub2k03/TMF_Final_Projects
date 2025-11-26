import React from 'react'
import { Routes, Route, Navigate } from 'react-router-dom'
import Navbar from './components/Navbar'
import Footer from './components/Footer'
import LoginPage from './pages/LoginPage'
import RegisterPage from './pages/RegisterPage'
import MovieListPage from './pages/MovieListPage'
import MovieDetailPage from './pages/MovieDetailPage'
import MyBookingsPage from './pages/MyBookingsPage'
import BookingPage from './pages/BookingPage'
import BookingConfirmationPage from './pages/BookingConfirmationPage'
import AdminDashboardPage from './pages/AdminDashboardPage'
import AdminCitiesPage from './pages/AdminCitiesPage'
import AdminPromotionsPage from './pages/AdminPromotionsPage'
import TheatreDashboardPage from './pages/TheatreDashboardPage'
import TheatreScreensPage from './pages/TheatreScreensPage'
import TheatreShowsPage from './pages/TheatreShowsPage'
import ProfilePage from './pages/ProfilePage'
import BankDetailsPage from './pages/BankDetailsPage'
import UserHomePage from './pages/UserHomePage'

function App() {
  return (
    <div className="d-flex flex-column min-vh-100">
      <Navbar />
      <div className="container mt-4 flex-grow-1">
        <Routes>
          <Route path="/" element={<MovieListPage />} />
          <Route path="/home" element={<UserHomePage />} />
          <Route path="/login" element={<LoginPage />} />
          <Route path="/register" element={<RegisterPage />} />
          <Route path="/movies/:id" element={<MovieDetailPage />} />
          <Route path="/movies/:id/book" element={<BookingPage />} />
          <Route path="/booking/confirmation/:id" element={<BookingConfirmationPage />} />
          <Route path="/my-bookings" element={<MyBookingsPage />} />
          <Route path="/profile" element={<ProfilePage />} />
          <Route path="/bank-details" element={<BankDetailsPage />} />

          {/* Admin */}
          <Route path="/admin/dashboard" element={<AdminDashboardPage />} />
          <Route path="/admin/cities" element={<AdminCitiesPage />} />
          <Route path="/admin/promotions" element={<AdminPromotionsPage />} />

          {/* Theatre admin */}
          <Route path="/theatre/dashboard" element={<TheatreDashboardPage />} />
          <Route path="/theatre/theatres/:theatreId-screens" element={<TheatreScreensPage />} />
          <Route path="/theatre/theatres/:theatreId/screens" element={<TheatreScreensPage />} />
          <Route path="/theatre/screens/:screenId/shows" element={<TheatreShowsPage />} />

          <Route path="*" element={<Navigate to="/" />} />
        </Routes>
      </div>
      <Footer />
    </div>
  )
}

export default App