import { combineReducers } from "redux";

import user from "./user";
import loginForm from "./loginForm";

export default combineReducers({
  user,
  loginForm
});
