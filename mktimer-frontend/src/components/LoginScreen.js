import React from "react";
import PropTypes from "prop-types";

import Input from "@material-ui/core/Input";
import InputLabel from "@material-ui/core/InputLabel";
import FormHelperText from "@material-ui/core/FormHelperText";
import FormControl from "@material-ui/core/FormControl";
import Button from "@material-ui/core/Button";

const LoginScreen = ({
  onUsernameTextChange,
  onPasswordTextChange,
  onLoginButtonClick,
  username,
  password
}) => (
  <div className="login-form">
    <div>
      <FormControl className="form-control">
        <InputLabel htmlFor="username">Username</InputLabel>
        <Input id="username" onChange={onUsernameTextChange} />
      </FormControl>
    </div>
    <div>
      <FormControl className="form-control">
        <InputLabel htmlFor="password">Password</InputLabel>
        <Input id="password" type="password" onChange={onPasswordTextChange} />
      </FormControl>
    </div>
    <Button
      className="login-button"
      variant="contained"
      color="primary"
      onClick={() => onLoginButtonClick(username, password)}
    >
      Log in
    </Button>
  </div>
);

export default LoginScreen;
