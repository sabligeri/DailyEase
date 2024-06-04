
import "./App.css";
import { NavLink, Outlet } from "react-router-dom";

function App() {
  return (
    <>
      <label className="hamburger-menu">
        <input type="checkbox" />
      </label>
      <aside className="sidebar">
        <nav id="sidenav">
          <NavLink to="/" className="nav-link"> LOGIN </NavLink>
          <NavLink to="/main" className="nav-link"> MAIN</NavLink>
          <NavLink to="/note" className="nav-link"> NOTE</NavLink>
          <NavLink to="/shoppinglist" className="nav-link"> SHOPPING</NavLink>
          <NavLink to="/settings" className="nav-link-settings"> SETTINGS</NavLink>
        </nav>
      </aside>
      <div className="root-layout">
        <main>
          <Outlet />
        </main>
      </div>
    </>

  );
}

export default App;
