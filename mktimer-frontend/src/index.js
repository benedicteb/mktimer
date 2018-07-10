import React from "react";
import ReactDOM from "react-dom";
import {Provider, connect} from "react-redux";

import store from "./store";
import {mapDispatchToProps, mapStateToProps} from "./maps";

const App = ({}) => (
  <div>
    <h1>Hello from React!</h1>
    <p>Test 2</p>
  </div>
);

const StateAwareApp = connect(
  mapStateToProps,
  mapDispatchToProps
)(App);

ReactDOM.render(
  <Provider store={store}>
    <StateAwareApp />
  </Provider>,
  document.getElementById("main")
);
