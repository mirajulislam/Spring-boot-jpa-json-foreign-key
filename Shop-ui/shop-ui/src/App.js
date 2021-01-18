import React from 'react';
import ReactDOM from 'react-dom';
import Footer from './Element/Footer'
import Header from './Element/Header';
import MainContent from './Element/MainContent';

function App(){
  const date=new Date(2021,1,6,11)
  const hours=date.getHours()
  let timeDay

  const styles = {
   fontSize:30
  }

  if(hours<12){
     timeDay    ="Good Morning"
     styles.color = "#FF7F50"
  }else if(hours>=12 && hours<17){
     timeDay    ="afternoon Morning"
     styles.color = "##40E0D0"
  }else{
    timeDay    ="Good Night"
    styles.color = "#DE3163"
  }
  return(
    <h1 style={styles}>{timeDay}</h1>
  )
}


export default App;
