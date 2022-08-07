import { Navigate, Route } from "react-router-dom";

function PrivateRoute({ component: Component, authed, ...rest }) {
  return (
    <Route
      {...rest}
      render={(props) =>
        authed === true ? (
          <Component {...rest} />
        ) : (
          <Navigate
            to={{
              pathname: "/",
              state: {
                from: props.location,
                message: "Please log in or sign up to continue",
              },
            }}
          />
        )
      }
    />
  );
}

export default PrivateRoute;
