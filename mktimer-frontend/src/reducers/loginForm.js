const defaultState = {
  username: "",
  password: ""
};

export default function loginForm(state = defaultState, action) {
  switch (action.type) {
    case "USERNAME_TEXT_CHANGED":
      return {
        username: action.value,
        password: state.password
      };
    case "PASSWORD_TEXT_CHANGED":
      return {
        username: state.username,
        password: action.value
      };
    case "LOGIN_FORM_CLEARED":
      return {
        username: "",
        password: ""
      };
    default:
      return state;
  }
}
