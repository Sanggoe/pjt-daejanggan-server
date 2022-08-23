import { useEffect, useState } from 'react';
import './App.css';
import Login from './Login';
import Menu from './Menu';


function App() {
  
  function setScreenSize() {
    let vh = window.innerHeight * 0.01;
    document.documentElement.style.setProperty("--vh", `${vh}px`);
  }
  useEffect(() => {
    setScreenSize();
  });

  return (
    <Menu/>
    );
}// <Login/>

export default App;
