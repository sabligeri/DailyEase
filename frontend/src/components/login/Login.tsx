import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import "./Login.css";

async function addUser(name: string, password: string) {
  console.log("Add user: ");
  console.log("name: " + name, "pass: " + password);

  const userToRegister = {
    name,
    password,
  };

  try {
    const registerUserResponse = await fetch("/api/user/register", {
      method: "POST",
      body: JSON.stringify(userToRegister),
      headers: {
        "Content-Type": "application/json",
      },
    });

    const addedUser = await registerUserResponse.json();

    console.log("registered: " + addedUser);
  } catch (error) {
    console.log(error);
  }
}

export default function Login() {
  const [userName, setUsername] = useState("");
  const [userNamePlaceholder, setUserNamePlaceholder] = useState("Username");
  const [password, setUserPassword] = useState("");
  const [passwordPlaceholder, setPasswordPlaceholder] = useState("Password");
  const [userId, setUserId] = useState(-1);

  const navigate = useNavigate();

  useEffect(() => {
    const id = JSON.parse(localStorage.getItem("userId")!);
    if (id > 0) {
      setUserId(id);
    } 
  }, []);

  const handleRegister = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    await addUser(userName, password);

    setUsername("");
    setUserPassword("");
  };

  async function handleLogin(e: React.FormEvent<HTMLFormElement>) {
    e.preventDefault();

    const user = {
      name: userName,
      password,
    };

    console.log(user.name, user.password);

    try {
      const response = await fetch("/api/user/login", {
        method: "POST",
        body: JSON.stringify(user),
        headers: {
          "Content-Type": "application/json",
        },
      });
      console.log("Login respons: " + response);

      if (response.ok) {
        const responseUser = await response.json();
        const responseUserId = responseUser.id;

        localStorage.setItem("userId", JSON.stringify(responseUserId));
        localStorage.setItem("jwt", JSON.stringify(responseUser.jwt));
        localStorage.setItem("roles", JSON.stringify(responseUser.roles));
        localStorage.setItem("username", JSON.stringify(responseUser.userName));
        setUserId(responseUser.id);
        navigate("/main");

      }
        if (!response.ok) {
          setUsername("");
          setUserNamePlaceholder("Wrong username try again...");
          setUserPassword("");
          setPasswordPlaceholder("Wrong password try again...");
          console.error("Failed to Login");
      }
    } catch (error) {
      console.error(error);
    }
  }

  async function handleLogout(e: React.FormEvent<HTMLFormElement>) {
    e.preventDefault();
    setUserId(-1);
    localStorage.clear();
  }

  return userId > 0 ? (
    <div id="login-root">
      <form className="login-layout" onSubmit={(e) => handleLogout(e)}>
        <h2>You are logged in! </h2>
        <button type="submit" className="login-page-btn">
          Log out
        </button>
      </form>
    </div>
  ) : (
    <div id="login-root">
      <div className="main">
        <input type="checkbox" id="chk" aria-hidden="true" />
        <div className="signup">
          <form onSubmit={(e) => handleRegister(e)}>
            <label htmlFor="chk" aria-hidden="true">
              Sign up
            </label>
            <input
              type="text"
              name="userName"
              onChange={(e) => setUsername(e.target.value)}
              value={userName}
              placeholder="Username"
              required
            />
            <input
              type="password"
              onChange={(e) => setUserPassword(e.target.value)}
              value={password}
              placeholder="Password"
              required
            />
            <button type="submit" className="login-page-btn">
              Sign up
            </button>
          </form>
        </div>
        <div className="login">
          <form onSubmit={(e) => handleLogin(e)}>
            <label htmlFor="chk" aria-hidden="true">
              Login
            </label>
            <input
              type="text"
              name="userName"
              onChange={(e) => setUsername(e.target.value)}
              value={userName}
              placeholder={userNamePlaceholder}
              required
            />
            <input
              type="password"
              onChange={(e) => setUserPassword(e.target.value)}
              value={password}
              placeholder={passwordPlaceholder}
              required
            />
            <button type="submit" className="login-page-btn">
              Log in
            </button>
          </form>
        </div>
      </div>
    </div>
  );
}
