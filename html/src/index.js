import React from 'react';
import ReactDOM from 'react-dom';
import { Provider } from 'react-redux';
import { Teams } from './components/Teams';
import { App } from './containers/App';
import store from './store'
import './styles/main.css';



ReactDOM.render(
  <Provider store={store}>
    <App/>
  </Provider>,
  document.getElementById('root'),
);
