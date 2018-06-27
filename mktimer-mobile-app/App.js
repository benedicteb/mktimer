import React from "react";
import {StyleSheet, Text, View} from "react-native";
import {Provider, connect} from "react-redux";
import {Font} from "expo";

import store from "./src/store";
import App from "./src";

const mapDispatchToProps = dispatch => ({});

const mapStateToProps = state => ({
  user: state.user
});

const StateAwareApp = connect(
  mapStateToProps,
  mapDispatchToProps
)(App);

export default class MyApp extends React.Component {
  componentDidMount() {
    Font.loadAsync({
      Roboto: require("./assets/fonts/Roboto-Regular.ttf")
    });
  }

  render() {
    return (
      <Provider store={store}>
        <StateAwareApp />
      </Provider>
    );
  }
}
