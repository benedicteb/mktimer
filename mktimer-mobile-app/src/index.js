import React from "react";
import {StyleSheet, Text, View} from "react-native";

import {COLOR, ThemeProvider} from "react-native-material-ui";

import LoginScreen from "./components/loginScreen";
import HomeScreen from "./components/homeScreen";

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: "#fff",
    alignItems: "center",
    justifyContent: "center"
  }
});

const uiTheme = {
  palette: {
    primaryColor: COLOR.green500
  },
  toolbar: {
    container: {
      height: 50
    }
  }
};

const App = ({user}) => (
  <ThemeProvider uiTheme={uiTheme}>
    user.loggedIn ? <HomeScreen user={user} /> : <LoginScreen user={user} />
  </ThemeProvider>
);

export default App;
