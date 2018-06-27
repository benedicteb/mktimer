import {
  createStore
} from 'redux';
import {
  connect
} from 'react-redux';

import reducer from './reducers';

const store = createStore(reducer);

export default store;