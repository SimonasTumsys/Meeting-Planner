import React, { useEffect, useState } from "react";
import {
  BrowserRouter as Router,
  Routes,
  Route,
  Switch,
  Navigate,
} from "react-router-dom";
import Login from "./pages/Login";
import Register from "./pages/Register";
import Dashboard from "./pages/Dashboard";
import Home from "./pages/Home";
import Navbar from "./components/Navbar";
import auth from "./service/auth";
import PrivateRoutes from "./util/PrivateRoutes";
import Calendar from "./pages/MeetingCalendar";

const App = () => {
  return (
    <>
      <Router>
        <Navbar />
        <Routes>
          <Route path="/home" element={<Home />} />
          <Route path="/login" element={<Login />} />
          <Route element={<PrivateRoutes />}>
            <Route path="/dashboard" element={<Dashboard />} />
            <Route path="/calendar" element={<Calendar />} />
          </Route>
          <Route path="*" element={<Navigate to="/home" />} />
        </Routes>
      </Router>
    </>
  );
};

export default App;
