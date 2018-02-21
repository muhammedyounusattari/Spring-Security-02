package younus.attari.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MainClassController {

	@RequestMapping(value={"/loginUrl","/hello","/"})
	public String loginRequest(HttpServletRequest request){
		System.out.println("this is from /login request");
		return "login";
	}
	
	@RequestMapping(value="/logoutUrl", method=RequestMethod.GET)
	public String logoutRequest(){
		System.out.println("this is from /logutUrl request");
		return "redirect:/loginUrl";
	}
}
