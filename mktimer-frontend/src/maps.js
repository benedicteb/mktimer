import { genTextChangeDispatch } from "./utils";
import { getJwtToken } from "./mktimer";

const mapDispatchToProps = dispatch => ({
  loginFormDispatch: {
    onUsernameTextChange: genTextChangeDispatch(
      "USERNAME_TEXT_CHANGED",
      dispatch
    ),
    onPasswordTextChange: genTextChangeDispatch(
      "PASSWORD_TEXT_CHANGED",
      dispatch
    ),
    onLoginButtonClick: (username, password) => {
      getJwtToken(username, password);
    }
  }
});

const mapStateToProps = state => ({
  user: state.user,
  loginFormProps: state.loginForm
});

export { mapDispatchToProps, mapStateToProps };
