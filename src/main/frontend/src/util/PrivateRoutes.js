import { Outlet, Navigate } from "react-router-dom";

const PrivateRoutes = () => {
  const isToken = () => {
    return localStorage.getItem("jwt") ? true : false;
  };
  return isToken() ? <Outlet /> : <Navigate to="/" />;
};

export default PrivateRoutes;
