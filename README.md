# Amsong-checker site Project

암송 체킹 사이트 구축 프로젝트



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

