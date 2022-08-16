# Amsong-checker site Project

암송 체킹 사이트 구축 프로젝트



## 학습을 위한 선행 예제 프로젝트 진행

### 환경 구축

* 참고 내용
  * [Spring Boot와 React 연동하기- blog](https://velog.io/@u-nij/Spring-Boot-React.js-%EA%B0%9C%EB%B0%9C%ED%99%98%EA%B2%BD-%EC%84%B8%ED%8C%85)
  * [스프링 부트에서 리액트 설치 - Youtube](https://www.youtube.com/watch?v=1sw8UTWC8kc&t=319s)

* [spring 프로젝트 생성](https://start.spring.io/) 및 기본 의존성 추가 (Spring Web... 등)

* react 프로젝트 생성

  * [node.js 설치](https://nodejs.org/ko/)
  * bash에서 `npm install` 설치
  * 생성할 react 프로젝트 경로로 이동 및 `create-react-app .`명령어로 프로젝트를 생성
    * `src/main/react-front`

* spring, react 포트 연동

  * package.json 파일에 proxy 추가

  ```json
  "proxy": "http://localhost:8080",
  ```

  * build.gradle 파일에 의존성 추가

  ```groovy
  def frontendDir = "$projectDir/src/main/frontend"
  
  sourceSets {
  	main {
  		resources { srcDirs = ["$projectDir/src/main/resources"]
  		}
  	}
  }
  
  processResources { dependsOn "copyReactBuildFiles" }
  
  task installReact(type: Exec) {
  	workingDir "$frontendDir"
  	inputs.dir "$frontendDir"
  	group = BasePlugin.BUILD_GROUP
  	if (System.getProperty('os.name').toLowerCase(Locale.ROOT).contains('windows')) {
  		commandLine "npm.cmd", "audit", "fix"
  		commandLine 'npm.cmd', 'install' }
  	else {
  		commandLine "npm", "audit", "fix" commandLine 'npm', 'install'
  	}
  }
  
  task buildReact(type: Exec) {
  	dependsOn "installReact"
  	workingDir "$frontendDir"
  	inputs.dir "$frontendDir"
  	group = BasePlugin.BUILD_GROUP
  	if (System.getProperty('os.name').toLowerCase(Locale.ROOT).contains('windows')) {
  		commandLine "npm.cmd", "run-script", "build"
  	} else {
  		commandLine "npm", "run-script", "build"
  	}
  }
  
  task copyReactBuildFiles(type: Copy) {
  	dependsOn "buildReact"
  	from "$frontendDir/build"
  	into "$projectDir/src/main/resources/static"
  }
  ```

  * front를 빌드한 다음에 back을 빌드하도록 하는 목적?



### CORS (Cross Origin Resource Sharing)

* 서버와 클라이언트가 동일한 IP주소에서 동작하고 있다면 resource를 제약 없이 서로 공유할 수 있지만, 만약 다른 도메인에 있다면 원칙적으로 어떤 데이터도 주고받을 수 없도록 하는 매커니즘
* 해당 관련 에러를 막을 목적으로 Spring과 React의 연동을 위해 추가 proxy 설정이 필요하다.



### 변수 적용

* let name 하면 변수 초기화
* { 변수이름 } 형태로 값을 넘겨주면 된다.
* 값이 변경되어도 새로고침을 해야 렌더된다.

```jsx
import logo from './logo.svg';
import './App.css';

function App() {
  let num = 1;

  return (
    <div className="App">
      <header className="App-header">
        <div> {num} </div>
      </header>
    </div>
  );
}

export default App; 
```



### useState

* useState 쓸 때는 let 대신 const를 사용해줘야 한다.
* 상단에 React, {useState} 를 import 해서 사용해야 한다.
* 바로 실행하지 말아야 하고, 반복문과 if문 안에서 정의하면 안된다.
* 즉, 값이 변경되는 순간 자동으로 페이지가 다시 render 된다고 생각하면 된다.

```jsx
import React, { useState } from 'react'
import logo from './logo.svg';
import './App.css';

function App() {

  const [num, setNum] = useState(0); // 이런식으로 쓴다

  // setTimeout(() => { setNum(num += 1) }, 1000); 1초마다 증가 코드

  return (
    <div className="App">0
      <header className="App-header">
        <div> {num} </div>
        <button onClick={ () => { setNum(num += 1) } }> 버튼 </button>
      </header>
    </div>
  );
}

export default App; 
```



### Style 적용

* style 직접 적용시, { { } } 처럼 중괄호를 2개 써서 객체 형식으로 해주어야 한다.
* 또는 css 파일을 만들어 className이라는 속성으로 넣어줘야 한다.

```jsx
import React, { useState } from 'react'
import logo from './logo.svg';
import './App.css';

function App() {

  const [num, setNum] = useState(1);

  return (
    <div className="App">
      <header className="App-header">
        <img src={logo} className="App-logo" alt="logo" />
        <div style={{color:"blue"}}>{num}</div>
        <div className='number'>{num}</div>
      </header>
    </div>
  );
}

export default App; 
```

```css
.number {
  color: red;
}
```



### 숫자 기록, 리스팅

* 현재 숫자 출력
* 버튼 눌러 증가, 감소 구현
* 저장 버튼 눌러 현재 숫자 저장 (저장시 숫자 초기화)
* 리스트로 저장된 숫자 보여주기

```jsx
import React, { useState } from 'react'
import './App.css';

function App() {

  const [num, setNum] = useState(0);
  const [numList, setNumList] = useState([]);

  function numRecording() { // 
    setNumList([...numList, num]) // js6 문법, numList.append(num)과 같은 의미의 코드로 보면 될 듯
    setNum(0)
  }

  return (
    <div className="App">
      <div>현재 숫자 : {num}</div>
      <button onClick={() => { setNum(num + 1) }}>숫자 증가</button>
      <button onClick={() => { setNum(num - 1) }}>숫자 감소</button>
      <button onClick={numRecording}>저장된 숫자</button>
      <h1>저장된 숫자</h1>
      <ul>
        {numList.map((num) => ( // num = numList.
          <li>{num}</li>
        ))}
      </ul>
    </div>
  );
}

export default App; 
```



### 함수 분리

* 지금 코드야 짧으니 함수 하나에 다 때려박지만, 나중 되면 또 많아질테니 컴포넌트들로 나누어 코딩!
* 매개변수를 받아올 때 **( { props } )** 형태로 받아와야 한다.
* 넘겨줄 때는 **key = { value }** 형태로 넘겨주며, 여기엔 변수나 배열, 함수도 넘겨줄 수 있다.

```jsx
import React, { useState } from 'react'
import './App.css';

const RecordForm = ({ numList, setNumList }) => { // (1) 매개변수 받아와야 함

  const [num, setNum] = useState(0);

  function numRecording() {
    setNumList([...numList, num]);
    setNum(0);
  }

  function recordingInit() {
    setNumList([])
  }

  return (
    <div>
      <div>현재 숫자 : {num}</div>
      <button onClick={() => setNum(num + 1)}>숫자 증가</button>
      <button onClick={() => setNum(num - 1)}>숫자 감소</button>
      <button onClick={() => setNum(0)}>숫자 초기화</button>
      <hr />
      <button onClick={numRecording }>숫자 기록</button>
      <button onClick={() => setNumList([])}>기록 초기화</button>
    </div>
  )
}

const RecordList = ({ numList }) => { // 배열 출력하는 부분 코드
  return (
    <div>
      <h2>기록된 숫자</h2>
      {numList.length > 0 ?
        <ul>
          {numList.map((num) => (
            <li>{num}</li>))}
        </ul>
        : <div>기록 없음</div>}
    </div>)
}

const App = () => {

  const [numList, setNumList] = useState([]);

  return (
    <div style={{
      margin: "40px auto",
      width: "800px",
      textAlign: "center"
    }}>
      <RecordForm numList={numList} setNumList={setNumList} />
      <RecordList numList={numList} />
    </div>
  ) // (1) 매개변수 위와 같이 넘겨줄 수 있음
}

export default App; 
```



### Input 태그

* 수정 버튼을 누르면 text 창이 뜨고 완료 버튼을 누르면 수정된 text가 출력이 되도록
* 페이지의 변경 없이 수정 되는 것이 포인트!
* 각 변수가 어떻게 넘겨지는지, 어떤게 반환 되는지 잘 살펴보자.

```jsx
import React, { useState } from 'react'

const App = () => {

  const [text, setText] = useState("") // 출력될 text 선언
  const [edit, setEdit] = useState(false); // 토글 판단할 edit 선언

  let content = <div>
    {text} <button onClick={() => setEdit(true)}> 수정하기 </button>
  </div>

  if (edit) { // 토글 edit이 true이면
    content = <div>
      <input type="text"
        value={text}
        onChange={(e) => {
          console.log(e.target.value) // 콘솔 로그 보기 코드
          setText(e.target.value)
        }}
      />
      <button onClick={() => setEdit(false)}> 수정완료 </button>
    </div>
  } // 토글 edit을 다시 false로 변경

  return (
    <div>
      {content}
    </div>
  );
}

export default App;
```



## Todo (Learning Curve)

* [Spring - React 프로젝트 연동](https://www.youtube.com/watch?v=1sw8UTWC8kc&t=319s) V
* [React 사용법 간단히 배우기 - 예제 프로젝트](youtube.com/watch?v=lKYp2QQFYK4&list=PLZzruF3-_clvT8YG7aErccPAncirfPWav&index=5) (진행중)
* [블로그 같이 켜놓고 따라해보기 - 예제 프로젝트](https://velog.io/@u-nij/Spring-Boot-React.js-%EA%B0%9C%EB%B0%9C%ED%99%98%EA%B2%BD-%EC%84%B8%ED%8C%85)
* [React MUI 사용법 간단히 배우기 - 예제 프로젝트](https://www.youtube.com/watch?v=or3np70c7zU&t=346s)

* 위에 다 완료 되었으면..
* [Amsong-checker 프로젝트 View MUI 응용해서 UI 만들어가기 시작]
* [Spring 서버단에서 데이터를 넘겨받아 React front 단에서 출력해보는 예제 따라해보기]
* [Mongo DB로 하면 어떨지, 그리고 그 DB에 담길 Data 상의해보기]

