import React from "react";
import { render } from "react-dom";
import { Provider, connect } from "react-redux";

import JssProvider from "react-jss/lib/JssProvider";
import { create } from "jss";
import { createGenerateClassName, jssPreset } from "@material-ui/core/styles";

import App from "./components/App";

import store from "./store";
import { mapDispatchToProps, mapStateToProps } from "./maps";

import normalize from "normalize.css";
import css from "./styles/main.css";

// Set where JSS from material-ui will be placed in html
const generateClassName = createGenerateClassName();
const jss = create(jssPreset());
jss.options.insertionPoint = "jss-insertion-point";

const StateAwareApp = connect(
  mapStateToProps,
  mapDispatchToProps
)(App);

render(
  <Provider store={store}>
    <JssProvider jss={jss} generateClassName={generateClassName}>
      <StateAwareApp />
    </JssProvider>
  </Provider>,
  document.getElementById("main")
);
