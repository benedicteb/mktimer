import axios from "axios";

import config from "./config";

const getJwtToken = (username, password) =>
  new Promise((resolve, reject) => {
    axios
      .post(config.baseUrl + "/login", {
        username: username,
        password: password
      })
      .then(response => {
        console.log(response);
      })
      .then(error => {
        console.log(error);
      });
  });

export { getJwtToken };
