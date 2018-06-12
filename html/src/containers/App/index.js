import React from 'react';
import {Teams} from '../../components/Teams';
import {Panel} from '../../components/Panel';
import 'bootstrap/dist/css/bootstrap.min.css';


export const App = () => (
  <div className="container-fluid">
    <div className="row">
      <div className="col hero">
        <div className="hero-text">
          <h1>
          Padel again, and again....
          </h1>
        </div>
      </div>
    </div>
    <div className="row tournament">
      <div className="col-10">
        <Panel/>
      </div>
      <div className="col-2">
        <Teams/>
      </div>
    </div>
  </div>
);
