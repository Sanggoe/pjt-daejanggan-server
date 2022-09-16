import { useEffect, useState } from 'react';
import './App.css';
import DataTest from './DataTest';
import Login from './Login';
import Menu from './Menu';
import TestButton from './TestButton';


function App() {

  return (
    <Menu />
    );
}// <TestButton/>
// <Login/> 

export default App;

/*
const heavyWork = () => {
  console.log('무거운 작업');
  return ["홍길동", "김민수"]
}

function App() {

  const [names, setNames] = useState(() => {
    return heavyWork();
  });
  const [input, setInput] = useState("");

  const handleInputChange = (e) => {
    setInput(e.target.value);
  }

  const handleUpload2 = (e) => {
    setNames([input, ...names])
  }
  const handleUpload = (e) => {
    setNames((prevState) => {
      console.log(prevState);
      return [input, ...prevState];
    });
  };



  return (
    <div>
      <input type="text" value={input} onChange={handleInputChange}/>
      <button onClick={handleUpload}>Update</button>
      {names.map((name, index) => {
        return <p key={index}>{name}</p>;
      })}
    </div>
  )
}
export default App;
*/


/**
function App() {
  const [count, setCount] = useState(1);
  const [name, setName] = useState('');

  const handleCountUpdate = () => {
    setCount(count + 1);
  };

  const handleInputChange = (e) => {
    setName(e.target.value);
  };

  useEffect(() => {
    console.log('처음 마운팅 될 때만');
  }, [])

  useEffect(() => {
    console.log('렌더링 될 때마다');
  })

  useEffect(() => {
    console.log('count 변화');
  }, [count])

  useEffect(() => {
    console.log('name 변화');
  }, [name])

  return (
    <div>
      <button onClick={handleCountUpdate}>Update</button>
      <span>count: {count}</span>
      <input type="text" value={name} onChange={handleInputChange}/>
      <span>name : {name}</span>
    </div>
  );
}

export default App;





const Timer = (props) => {
  useEffect(() => {
    const timer = setInterval(() => {
      console.log("Timer 돌아가는 중..");
    }, 1000)

    return () => {
      clearInterval(timer);
      console.log("Timer 종료!!")
    }
  }, []);

  return (
    <div>
      <span>타이머 시작! 콘솔.</span>
    </div>
  )
}

function App() {
  const [showTimer, setShowTimer] = useState(false);

  return (
    <div>
      {showTimer && <Timer/>}
      <button onClick={() => setShowTimer(!showTimer)}>Toggle Timer</button>
    </div>
  );
}

export default App;
*/