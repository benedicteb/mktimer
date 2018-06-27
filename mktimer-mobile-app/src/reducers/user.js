const defaultState = {
  loggedIn: false
};

export default function user(state = defaultState, action) {
  switch (action.type) {
    case "LOGIN_SUCCESSFULL":
      return {
        loggedIn: true,
        username: action.username
      };
    case "LOGGED_OUT":
      return {
        loggedIn: false
      };
    default:
      return state;
  }
}
