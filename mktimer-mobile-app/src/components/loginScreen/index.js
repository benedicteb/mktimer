import React from "react";
import {Text, View, StyleSheet, TextInput, Button} from "react-native";

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: "#fff",
    alignItems: "center",
    justifyContent: "center"
  },
  header: {
    fontSize: 30,
    marginBottom: 30
  },
  usernameInput: {
    width: 200,
    height: 40,
    borderColor: "gray",
    borderWidth: 1,
    paddingLeft: 10,
    paddingRight: 10
  },
  fieldLabel: {},
  loginButton: {
    marginTop: 30
  }
});

const LoginScreen = ({user, onTextChange}) => (
  <View style={styles.container}>
    <Text style={styles.header}>Log in</Text>
    <Text style={styles.fieldLabel}>Username</Text>
    <TextInput style={styles.usernameInput} />
    <Text style={styles.fieldLabel}>Password</Text>
    <TextInput style={styles.usernameInput} />
    <Button style={styles.loginButton} title="Log in" />
  </View>
);

export default LoginScreen;
