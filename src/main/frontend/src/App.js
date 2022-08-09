import React from "react";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import Login from "./pages/Login";
import Register from "./pages/Register";
import Dashboard from "./pages/Dashboard";
import Meetings from "./pages/Meetings";
import Home from "./pages/Home";
import Navbar from "./components/Navbar";

function App() {
  return (
    <>
      <Login />
    </>
  );
}

export default App;
