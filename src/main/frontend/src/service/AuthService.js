const axios = require("axios");

const checkIfAuthenticated = (setUser, setAuth) => {
  axios.get("/meeting/get").then((response) => {
    console.log("cia yra tavo response " + response);
  });
};

export default checkIfAuthenticated;
