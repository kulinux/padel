import React from 'react';
import ReactDOM from 'react-dom';
import { Provider } from 'react-redux';
import { Teams } from './components/Tournament';
import store from './store'
import './styles/main.css';



ReactDOM.render(
  <Provider store={store}>
    <Teams/>
  </Provider>,
  document.getElementById('root'),
);
