## 🚩 Spring 개념잡기               start _ 2020-03-17



##### 자바 스프링 프레임워크(renew ver.) - 신입 프로그래머를 위한 강좌

인프런 스프링 기초 개념 강좌를 수강하며 실습하는 파일들을 기록하는 Repository 입니다.





## :heavy_check_mark: 4강 ( pom.xml 정의, applicationContext, Bean )

### 1. 기본 프로젝트 생성

​	1-1 기본 Maven 프로젝트 생성 ( new -> Maven Project )



​	1-2 pom.xml 작성



```java
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>
  <groupId>spring4</groupId>
  <artifactId>testPjt</artifactId>
  <version>0.0.1-SNAPSHOT</version>
  
  	<dependencies>
		<dependency>
			<groupId>org.springframework</groupId>
			<artifactId>spring-context</artifactId>
			<version>4.1.0.RELEASE</version>
		</dependency>
	</dependencies>

	<build>
		<plugins>
			<plugin>
				<artifactId>maven-compiler-plugin</artifactId>
				<version>3.1</version>
				<configuration>
					<source>1.8</source>
					<target>1.8</target>
					<encoding>utf-8</encoding>
				</configuration>
			</plugin>
		</plugins>
	</build>
  
</project>
```



이곳에서 기본 pom.xml파일에 

두 가지,  org.springframework와 maven-compiler-plugin을 추가해주었다.



:soon:   plugin을 추가해준 뒤 [ Maven -> Update Project ] 를 진행해준다.

:soon:  ​ 가장 기본의 Maven 프로젝트 src/main/ java와 resources 생성되며

:soon:   이후 테스트에 사용할 src/test/ java와 resources 생성된다.



java 폴더는 JAVA 파일들이 위치하는 곳이고 

​	resources 는 자원을 관리하는 폴더로 스프링 설정 파일(XML) 또는 프로퍼티 파일 등을 관리







### 2. applicationContext.xml 작성



```java
<?xml version="1.0" encoding="UTF-8"?>

<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
	   http://www.springframework.org/schema/beans/spring-beans-3.1.xsd">
	
		<bean id="tWalk" class="testPjt.TransportationWalk"/>
	   	
</beans>

```



Spring에서 **bean**을 사용해보기 위해 testPjt.TransportationWalk 클래스를 "tWalk" 빈 지정

-->	이후 Main에서 사용해보았다.





```java
package testPjt;

import org.springframework.context.support.GenericXmlApplicationContext;

public class MainClass {

	public static void main(String[] args) {
	
//		### applicationContext와 bean을 사용하지 않은 경우 ###
//		TransportationWalk transportationwalk = new TransportationWalk();
//		transportationwalk.move();
		
		
//		### bean과 applicationContext를 사용한 경우
		GenericXmlApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationContext.xml");
		TransportationWalk transportationWalk = ctx.getBean("tWalk", TransportationWalk.class);
		transportationWalk.move();
		
		ctx.close();
	}
}
```





## :heavy_check_mark: 6강 ( DI_Dependency Injection )



### * applicationContext Bean

Spring에서 Bean을 생성하고 이용하기 위해서 applicationContext파일을 사용했다.

여기에서 Bean을 생성할 때, Dao를 하나 생성한다면



```
<bean id="studentDao" class="ems.member.dao.StudentDao"></bean>
```

이런식으로 생성해줄 수 있을 것이다.



이제 이 studentDao를 사용하는 registerService 또는 modifyService와 같이

Dao객체를 이용할 때에는 < constructor-arg> 를 사용한다.



```

<bean id="registerService" class="ems.member.service.StudentRegisterService">
	<constructor-arg ref="studentDao"></constructor-arg>
</bean>

<bean id="modifyService" class="ems.member.service.StudentModifyService">
	<constructor-arg ref="studentDao"></constructor-arg>
</bean>

```





## 다양한 의존성 객체 주입 방법

#### 1. 생성자를 이용한 객체 주입



public StudentRegisterService(StudentDao studentDao) {

​	this.studentDao = studentDao;

}



```java
# applicationContext.xml...
<bean id="studentDao" class="ems.member.dao.StudentDao"></bean>

<bean id="registerService" class="ems.member.service.StudentRegisterService">
	<constructor-arg ref="studentDao"></constructor-arg>
</bean>
```



```java
# Main에서...
GenericXmlApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationContext.xml");

StudentRegisterService registerService = ctx.getBean("registerService, StudentRegisterService.class")
```







## :heavy_check_mark: 9강 ( 의존 객체 자동 주입 )



#### 의존 객체 자동 주입이란 ?

```
스프링 설정 파일에서 의존 객체를 주입할 때 <constroctor-org> 또는 <property> 태그로 의존 대상 객체를 명시하지 않아도 스프링 컨테이너가 자동으로 필요한 의존 대상 객체를 찾아서 의존 대상 객체가 필요한 객체에 주입해 주는 기능이다.

구현 방법은 @Autowired와 @Resource 어노테이션을 이용해서 쉽게 구현할 수 있다.
```



### @Autowired

- 주입하려고 하는 **객체의 타입이 일치**하는 객체를 자동으로 주입한다.



@Autowired로 의존 객체를 주입시켜주기 위해 xml 파일을 수정한다.



<context:annotation-config /> 코드 추가,  <beans>코드 조금 추가

```java
						[ 기존의 xml 파일 ]

<?xml version="1.0" encoding="UTF-8"?>

<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://www.springframework.org/schema/beans 
 		http://www.springframework.org/schema/beans/spring-beans.xsd">

	<bean id="wordDao" class="com.word.dao.WordDao" />
	
	<bean id="registerService" class="com.word.service.WordRegisterService">
		<constructor-arg ref="wordDao" />
	</bean>
	
	<bean id="searchService" class="com.word.service.WordSearchService">
		<constructor-arg ref="wordDao" />
	</bean>
	
</beans>
```



@Autowired 를 사용하기 위해 변경된 xml

```java
				[ annotation-config태그 추가, constructor 제거 ]

<?xml version="1.0" encoding="UTF-8"?>

<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:context="http://www.springframework.org/schema/context"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://www.springframework.org/schema/beans 
 		http://www.springframework.org/schema/beans/spring-beans.xsd 
 		http://www.springframework.org/schema/context 
 		http://www.springframework.org/schema/context/spring-context.xsd">

	<context:annotation-config />

	<bean id="wordDao" class="com.word.dao.WordDao" >
	</bean>
	
	<bean id="registerService" class="com.word.service.WordRegisterServiceUseAutowired" />
	
	<bean id="searchService" class="com.word.service.WordSearchServiceUseAutowired" />
	
</beans>
```





@Autowired를 적용한 Service 예시

```java
package com.word.service;

import com.word.WordSet;
import com.word.dao.WordDao;

public class WordRegisterService {

	private WordDao wordDao;
	
	public WordRegisterService(WordDao wordDao) {
		this.wordDao = wordDao;
	}
	
	public void register(WordSet wordSet) {
		String wordKey = wordSet.getWordKey();
		if(verify(wordKey)) {
			wordDao.insert(wordSet);
		} else {
			System.out.println("The word has already registered.");
		}
	}
	
	public boolean verify(String wordKey){
		WordSet wordSet = wordDao.select(wordKey);
		return wordSet == null ? true : false;
	}
	
	public void setWordDao(WordDao wordDao) {
		this.wordDao = wordDao;
	}
	
}
```



@Autowired를 적용한 소스파일

```java
package com.word.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.word.WordSet;
import com.word.dao.WordDao;

public class WordRegisterServiceUseAutowired {

    ###
       	Autowired적용, * 기본 생성자를 만들어줘야 한다. 
    ###
        
	@Autowired
	private WordDao wordDao;
	
	public WordRegisterServiceUseAutowired() {
		// TODO Auto-generated constructor stub
	}
	
	public WordRegisterServiceUseAutowired(WordDao wordDao) {
		this.wordDao = wordDao;
	}
	
	public void register(WordSet wordSet) {
		String wordKey = wordSet.getWordKey();
		if(verify(wordKey)) {
			wordDao.insert(wordSet);
		} else {
			System.out.println("The word has already registered.");
		}
	}
	
	public boolean verify(String wordKey){
		WordSet wordSet = wordDao.select(wordKey);
		return wordSet == null ? true : false;
	}
	
	public void setWordDao(WordDao wordDao) {
		this.wordDao = wordDao;
	}
	
}
```





## :heavy_check_mark: 12강 ( 어노테이션을 이용한 스프링 설정 - I )



### :heavy_exclamation_mark: XML 파일을 Java 파일로 변경하기



**@Configuration** 어노테이션을 사용해준다  :arrow_right:  Java 파일을 스프링 xml 설정파일처럼 변경



**@Bean** 어노테이션을 이용하여 Bean 객체를 설정해준다.



```java
import ems.member.dao.StudentDao;

@Configuration
public class MemberConfig {

     /*
    	<bean id="studentDao clas="cms.member.dao.StudentDao" />
    */
	@Bean
	public StudentDao studentDao(){
		return new StudentDao();
	}
    
    
    /*
    	<bean id="registerService" class="cms.member.service.StudentRegisterService">
    		<constructor-arg ref="studentDao"></constructor-arg>
    	</bean>
    */
    @Bean
	public StudentRegisterService registerService(){
        return new StudentRegisterService(studentDao());
    } 
    
    /*
    <bean id="dataBaseConnectionInfoDev" class="ems.member.DataBaseConnectionInfo">
		<property name="jdbcUrl" value="jdbc:oracle:thin:@localhost:1521:xe" />
		<property name="userId" value="scott" />
		<property name="userPw" value="tiger" />
	</bean>
    */
    @Bean
	public DataBaseConnectionInfo dataBaseConnectionInfoDev() {
		DataBaseConnectionInfo infoDev = new DataBaseConnectionInfo();
		infoDev.setJdbcUrl("jdbc:oracle:thin:@localhost:1521:xe");
		infoDev.setUserId("scott");
		infoDev.setUserPw("tiger");
		
		return infoDev;
	}
}
```





### :dart:  제작한 Java Configuration 파일 사용하기

```java
/*
	GenericXmlApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationContext.xml");
*/

AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(MemberConfig1.class);
```





## :heavy_check_mark: 13강 ( 웹 프로그래밍 설계 모델 )

​      **MVC 모델 1 방식과 MVC 모델 2 방식**



**모델 1 방식**

- JSP로 Service와 Dao 모두 구현
- 소스에 JSP, Java, Html 태그 코드가 모두 섞여있어 개발은 빠르나 유지보수에 좋지 않음



**모델 2 방식**

- WAS(웹 어플리케이션 서버)에서 Controller와 Service, DAO, View를 모두 분리함
- Controller를 만들고, Service에 기능을 구현하여 따로 만듦.
- DAO를 통해 데이터베이스와 데이터를 요청하며 주고받고
- View를 이용해 화면을 구현함





![Spring 설계구조](C:\Users\user\Desktop\Spring\git\Spring 설계구조.PNG)



**DispatcherServlet**

1. HandlerMapping 에게 요청을 던져 Controller를 선택받는다.
2. HandlerAdapter 에게 Controller에서 적합한 Method를 선택받는다.
3. ViewResolver 에게 가장 적합한 JSP 페이지를 선택받는다.
4. View에 응답을 생성한다.



**HandlerMapping** : 요청을 받아 Controller를 선택해준다.



**HandlerAdapter** : 요청을 받아 해당하는 Controller의 Method를 찾아준다.



**ViewResolver**  : 요청에 가장 적합한 JSP 페이지를 선택해준다.





### DispatcherServlet 설정

web.xml에 서블릿을 매핑시켜준다.

 ( WEB-INF 폴더의 web.xml 파일을 만들고, < servlet> 태그와 <servlet-mapping 태그를 이용한다.)



![DispatcherServlet 사용](C:\Users\user\Desktop\Spring\git\DispatcherServlet 사용.PNG)





:soon:   servlet-context.xml ( 스프링 설정 파일)에 

​						**< annotation-driven />** 태그를 넣어주면

​	해당 Controller를 찾아간다. ( 클래스 **@Controller** 정의 )



:soon:  Controller에서 해당 요청을 처리할 Service는 RequestMapping으로 찾아간다. ( 처리할 메소드를 찾아감 )

​					**@RequestMapping("/success")**



```java
@RequestMapping("/success")
public String success(Model model){
	model.setAttribute("tempData", "model has data!!");
}
```

- 개발자는 Model 객체에 데이터를 담아서 Dispatcher Servlet에 전달할 수 있다.
- DispatcherServlet에 전달된 Model 데이터는 View에서 가공되어 클라이언트한테 응답처리 된다.





> 사용자의 모든 요청을 DispatcherServlet이 받은 후 HandlerMapping 객체에 Controller 객체 검색을 요청한다. 그러면 HandlerMapping 객체는 프로젝트에 존재하는 모든 Controller 객체를 검색한다. HandlerMapping 객체가 Controller 객체를 검색해서 DispatcherServlet 객체에 알려주면 DispatcherServlet 객체는 다시 HandlerAdapter 객체에 사용자의 요청에 부합하는 메소드 검색을 요청한다. 그러면 HandlerAdapter 객체는 사용자의 요청에 부합하는 메소드를 찾아서 해당 Controller 객체의 메소드를 실행한다. Controller 객체의 메소드가 실행된 후 Controller 객체는 HandlerAdapter 객체에 ModelAndView 객체를 반환하는데 ModelAndView 객체에는 사용자 응답에 필요한 데이터 정보와 뷰 정보가 담겨있다. 다음으로 HandlerAdapter 객체는 ModelAndView 객체를 다시 DispatcherServlet 객체에 반환한다.





![MVC 폴더 수동제작](C:\Users\user\Desktop\Spring\git\MVC 폴더 수동제작.PNG)

 		**STS를 사용하지 않고 만드는 MVC 프로젝트 폴더 구조**













