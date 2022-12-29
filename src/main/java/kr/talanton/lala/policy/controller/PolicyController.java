package kr.talanton.lala.policy.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.talanton.lala.policy.dto.PolicyUpdateForm;
import kr.talanton.lala.policy.service.PolicyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller
@RequestMapping("/policy")
@Log4j2
@RequiredArgsConstructor
public class PolicyController {
	private final PolicyService service;
	
	@GetMapping("/manage")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public void manage(Model model) {
		log.info("/policy/manage");
		
		model.addAttribute("list", service.getList());
	}
	
	@PostMapping("/manage")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String manage(PolicyUpdateForm form) {
		log.info("manage..." + form);
		
		service.modify(form);
		return "redirect:/policy/manage";
	}
}