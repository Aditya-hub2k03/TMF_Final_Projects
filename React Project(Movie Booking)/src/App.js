import React from "react";
import Header from "./components/Header";
import Hero from "./components/Hero";
import MovieGrid from "./components/MovieGrid";
import Sidebar from "./components/Sidebar";
import Footer from "./components/Footer";

const App = () => (
  <div className="container">
    <Header />
    <Hero />
    <main className="content">
      <MovieGrid />
      <Sidebar />
    </main>
    <Footer />
  </div>
);

export default App;
