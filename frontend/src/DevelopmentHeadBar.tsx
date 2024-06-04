import { NavLink } from "react-router-dom";

export default function DevelopmentHeadBar() {
  return (
    <nav id="development-header">
      <NavLink to="/"> Login </NavLink>
      <NavLink to="/main"> MainPage</NavLink>
      <NavLink to="/note"> Notes</NavLink>
      <NavLink to="/settings"> Settings</NavLink>
    </nav>
  );
}
