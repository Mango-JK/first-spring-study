package ems.member.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.brms.member.dao.MemberDao;
import com.brms.member.service.MemberRegisterService;

@Configuration
public class MemberConfig {

	@Bean
	public MemberDao memberDao() {
		return new MemberDao();
	}
	
	@Bean
	public MemberRegisterService registerService() {
		return new MemberRegisterService(memberDao());
	}
	
	
}
