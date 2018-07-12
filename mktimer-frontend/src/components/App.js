import React from "react";

import TopBar from "./TopBar";
import LoginScreen from "./loginScreen";

const App = ({ user, loginFormDispatch, loginFormProps }) => (
  <div>
    <TopBar />
    {user.loggedIn ? (
      <p>logged in</p>
    ) : (
      <LoginScreen
        onUsernameTextChange={loginFormDispatch.onUsernameTextChange}
        onPasswordTextChange={loginFormDispatch.onPasswordTextChange}
        onLoginButtonClick={loginFormDispatch.onLoginButtonClick}
        username={loginFormProps.username}
        password={loginFormProps.password}
      />
    )}
  </div>
);

export default App;
