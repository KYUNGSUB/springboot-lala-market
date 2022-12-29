package kr.talanton.lala.member.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.talanton.lala.member.dto.MemberDTO;
import kr.talanton.lala.member.service.MemberService;
import kr.talanton.lala.terms.service.TermsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
@Log4j2
public class MemberController {
	private final TermsService service;
	private final MemberService memberService;
	
	@GetMapping("/login")
	public void login() {
		log.info("login...");
	}
	
	@PostMapping("idCheck")
	public @ResponseBody String idCheck(String userid) {
		log.info("idCheck..." + userid);
		if(memberService.idCheck(userid)) {
			return "ok";
		}
		else {
			return "nok";
		}
	}
	
	@GetMapping("/join")
	public String join(@CookieValue String terms, HttpServletResponse response, Model model) {
		log.info("join: cookie=" + terms);
		if(terms != null) {
			Cookie newCookie = new Cookie("terms", terms);
			newCookie.setMaxAge(0);
			response.addCookie(newCookie);
			model.addAttribute("marketing", terms);
			return "/member/join";
		} else {
			return "redirect:/";
		}
	}
	
	@PostMapping("/join")
	public String join(MemberDTO member, RedirectAttributes rttr) {
		log.info("================================");
		log.info("register: " + member);
		memberService.join(member);
		rttr.addFlashAttribute("result", member.getUserid());
		return "redirect:/member/login";
	}
	
	@GetMapping("/terms")
	public void terms(Model model) {
		model.addAttribute("list", service.getList());	// 뷰로 약관 목록을 전달
	}
	
	@PostMapping("/terms")
	public String termsPro(String[] agreement, HttpServletResponse response) {
		Cookie cookie = new Cookie("terms", agreement[0]);
		response.addCookie(cookie);
		return "redirect:/member/join";
	}
}