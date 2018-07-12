const defaultState = {
  loggedIn: false
};

export default function user(state = defaultState, action) {
  switch (action.type) {
    case "USER_LOGGED_IN":
      return {
        loggedIn: true,
        username: action.value.username,
        token: action.value.token
      };
    default:
      return state;
  }
}
