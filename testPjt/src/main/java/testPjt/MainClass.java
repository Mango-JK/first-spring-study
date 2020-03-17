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