import React from "react";

const Header = () => {
  return (
    <header>
      <div className="brand">
        <div className="logo">
          <span>MB</span>
        </div>
        <div>
          <h1>MovieBox</h1>
          <p>Book tickets · Discover · Watch</p>
        </div>
      </div>

      <nav className="controls">
        <button className="btn">Sign in</button>
        <button className="btn primary">Register</button>
      </nav>
    </header>
  );
};

export default Header;
