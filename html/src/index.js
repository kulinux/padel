import React from 'react';
import ReactDOM from 'react-dom';
import { Provider } from 'react-redux';
import { Teams } from './components/Tournament';
import store from './store'
import {addTeam} from './components/Tournament/actions'
import './styles/main.css';

window.store = store;
window.addTeam = addTeam;


ReactDOM.render(
  <Provider store={store}>
    <Teams/>
  </Provider>,
  document.getElementById('root'),
);
