const genTextChangeDispatch = (eventType, dispatch) => event => {
  dispatch({
    type: eventType,
    value: event.target.value
  });
};

export { genTextChangeDispatch };
