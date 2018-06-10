import React, {Component} from 'react'
import {connect} from 'react-redux'
import uuidv1 from 'uuid'
import {addTeam} from '../../actions'

const mapDispatchToProps = dispatch => {
  return {
      addTeam: team => dispatch(addTeam(team))
  };
};

class AddTeamFormConnected extends Component {
  constructor() {
    super();

    this.state = {
      name: ''
    };

    this.handleSubmit = this.handleSubmit.bind(this);
    this.handleOnChange = this.handleOnChange.bind(this);
  }

  handleOnChange(event) {
      this.setState({[event.target.id]: event.target.value});
  }

  handleSubmit(event) {
    event.preventDefault();
    const {name} = this.state;

    console.log('name', name);
    const id = uuidv1();
    this.props.addTeam({id, name});
    this.setState({name: ''});
  }

  render() {
    const { name } = this.state;
    return (
      <form onSubmit={this.handleSubmit}>
        <label htmlFor="name">Name</label>
        <input id="name" value={name} onChange={this.handleOnChange}/>
        <button type="submit">Add Team</button>
      </form>
    );
  }
}


export const AddTeamForm = connect(null, mapDispatchToProps)(AddTeamFormConnected);

export default AddTeamForm;