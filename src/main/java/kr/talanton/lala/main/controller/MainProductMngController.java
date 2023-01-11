package kr.talanton.lala.main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.talanton.lala.main.dto.MainPmainDTO;
import kr.talanton.lala.main.service.MainProductMngService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller
@RequestMapping("/main")
@RequiredArgsConstructor
@Log4j2
public class MainProductMngController {
	private final MainProductMngService service;
	
	@GetMapping("/show")
	public void show(@ModelAttribute("kind") String kind, Model model) {
		log.info("메인 상품 관리(show : " + kind + ")");
		MainPmainDTO dto = service.getMainProductManagementInfo(kind);
		model.addAttribute("dto", dto);
	}
	
	@GetMapping("/modal")
	public void modal() {
		log.info("modal...");
	}
}