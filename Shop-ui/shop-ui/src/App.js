import React from 'react';
import ReactDOM from 'react-dom';
import ContactCard from './component/ContactCard';

function App(){
  return(
    <div className="contacts">
      <ContactCard  contact={{name:"Mr. Watson",imgUrl:"http://placekitten.com/300/200",phone:"01788002266",email:"watson@gmail.com"}}
      />
      <ContactCard  contact={{name:"Mr. Watson",imgUrl:"http://placekitten.com/400/200",phone:"01788002266",email:"watson@gmail.com"}}
      />
      <ContactCard  contact={{name:"Mr. Watson",imgUrl:"http://placekitten.com/400/300",phone:"01788002266",email:"watson@gmail.com"}}
      />
      <ContactCard  contact={{name:"Mr. Watson",imgUrl:"http://placekitten.com/200/100",phone:"01788002266",email:"watson@gmail.com"}}
      />
  </div>
  )
}
export default App;
