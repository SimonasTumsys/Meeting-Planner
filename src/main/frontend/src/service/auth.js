import { useNavigate } from "react-router-dom";

class Auth {
  constructor() {
    this.authenticated = false;
  }

  authLogin() {
    this.authenticated = true;
  }

  authLogout() {
    this.authenticated = false;
    localStorage.removeItem("jwt");
  }

  isAuthenticated() {
    return this.authenticated;
  }
}

export default new Auth();
