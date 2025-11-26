import React from "react";

const Sidebar = () => {
  return (
    <aside className="sidebar">
      <div className="location">
        <label htmlFor="city">Your location</label>
        <select id="city">
          <option>Visakhapatnam</option>
          <option>New Delhi</option>
          <option>Mumbai</option>
          <option>Bengaluru</option>
          <option>Kolkata</option>
          <option>Chennai</option>
        </select>

        <label htmlFor="theatre">Preferred theatre (optional)</label>
        <input id="theatre" placeholder="Search theatre name" />
      </div>
    </aside>
  );
};

export default Sidebar;
