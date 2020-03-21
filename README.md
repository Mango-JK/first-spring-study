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

여기에서 Bean을 생성할 때, Dao를 하나 생성하다면



```
<bean id="studentDao" class="ems.member.dao.StudentDao"></bean>
```

이런식으로 생성해줄 수 있을 것이다.



이제 이 studentDao를 사용하는 registerService 또는 modifyService와 같이

Dao객체를 이용할 때에는 <constructor-arg>를 사용한다.



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

AnnotationConfigApplicationContext ctx = new AnnotaionConfigApplicationContext(MemberConfig1.class);
```









