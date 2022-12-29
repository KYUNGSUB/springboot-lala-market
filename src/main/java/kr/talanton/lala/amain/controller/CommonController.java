package kr.talanton.lala.amain.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.log4j.Log4j2;

@Controller
@Log4j2
public class CommonController {
	@GetMapping("/accessError")
	public void accessDenied(Authentication auth, Model model) {
		log.info("access denied: " + auth);
		model.addAttribute("msg", "Access Denied");	// 금지된 이유
	}	// view page 이름 : /accessError.jsp

	@PreAuthorize("hasRole('ROLE_ADMIN')")	// 관리자만 접속 가능
	@GetMapping("/aindex")
	public String aindex() {
		log.info("aindex...");
		return "ahome";
	}
	
	@GetMapping("/customLogout")		// Logout 폼 요청
	public void logoutGet() {
		log.info("custom logout");
	}	// view page 이름은 url과 같다. /customLogout.jsp
}