import React from 'react';
import ReactDOM from 'react-dom';
import Joke from './Joke';

function App(){
  return(
      <div>
        <Joke 
          answer="What Is your name ? as as as ds " 
        />
        <Joke 
          question="What Is your name ?" 
          answer="My name is raj"
        />

        <Joke 
          question="What Is your name ?" 
          answer="My name is raj"
        />

        <Joke 
          question="What Is your name ?" 
          answer="My name is raj"
        />

        <Joke 
          question="What Is your name ?"
          answer="My name is raj"
        />
      </div>
  )
}
export default App;
