package com.bs.lec16Pjt;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
	
	@RequestMapping("/")
	public String home(Model model) {
		System.out.println("home 컨트롤러 실행 --- ");
		model.addAttribute("key", "Home 컨트롤러 입니다.");
		
		return "home";
	}
	
	@RequestMapping("/login")
	public String login(Model model) {
		System.out.println("login 컨트롤러 실행 --- ");
		model.addAttribute("key", "Login 컨트롤러 입니다.");
		
		return "login";
	}

}
