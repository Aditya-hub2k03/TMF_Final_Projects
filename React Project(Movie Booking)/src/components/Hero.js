import React from "react";

const Hero = () => {
  return (
    <div className="hero">
      <div className="search">
        <input type="search" placeholder="Search movies, actors, genres..." />
        <div className="select">
          <select>
            <option>All cities</option>
            <option>Visakhapatnam</option>
            <option>Chennai</option>
            <option>New Delhi</option>
            <option>Mumbai</option>
            <option>Bengaluru</option>
            <option>Kolkata</option>
          </select>
          <select>
            <option>All languages</option>
            <option>English</option>
            <option>Hindi</option>
            <option>Telugu</option>
            <option>Tamil</option>
            <option>Malayalam</option>
            <option>Kannada</option>
          </select>
        </div>
      </div>

      <div className="search-btn">
        <button>Search</button>
      </div>
    </div>
  );
};

export default Hero;
