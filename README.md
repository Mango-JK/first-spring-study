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





