package kr.talanton.lala.category.controller;

import java.util.List;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import kr.talanton.lala.category.dto.CategoryDTO;
import kr.talanton.lala.category.dto.MoveRequest;
import kr.talanton.lala.category.dto.OptionInfo;
import kr.talanton.lala.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller
@RequestMapping("/category")
@RequiredArgsConstructor
@Log4j2
public class CategoryController {
	private final CategoryService service;
	
	@GetMapping("/show")
	public String show(Model model) {
		model.addAttribute("list", service.getList());
		return "category/view";
	}
	
	@PostMapping("/show")
	public @ResponseBody String showPost() {
		List<CategoryDTO> list = service.getList();	// 카테고리 목록 정보를 가져온다.
		Gson gson = new Gson();
		JsonElement userObj = gson.toJsonTree(list);
		JsonObject jsonObj = new JsonObject();
		jsonObj.addProperty("result", "success");
		jsonObj.add("data", userObj);
		return jsonObj.toString();
	}
	
	@PostMapping("/add")
	public String add(CategoryDTO category) {
		if(category.getParent() == null) {
			category.setStep(1);
		} else {
			category.setStep(2);	// 2차
			category.setParent(category.getParent());
		}
		service.add(category);
		return "redirect:/category/show";
	}
	
	@PostMapping("/check")
	public @ResponseBody String check(String code) {
		log.info("category check: code=" + code);
		return service.codeCheck(code);
	}
	
	@PostMapping("/move")
	public String move(MoveRequest mr) {
		service.moveCategory(mr);
		return "redirect:/category/show";
	}
	
	@PostMapping("/modify")
	public String modify(String code, @Param("expose") String exposeStr, @Param("gnb") String gnbStr) {
		boolean expose = exposeStr.equals("yes") ? true: false;
		if(gnbStr == null) {	// 2차 카테고리 정보 변경 -> 노출 여부 변경
			service.modify(code, expose);	// 메소드 오버로딩 적용
		} else {	// 1차 카테고리 정보 변경 -> 노출 여부 및 GNB 노출여부 변경
			boolean gnb = gnbStr.equals("yes") ? true: false;
			service.modify(code, expose, gnb);
		}
		return "redirect:/category/show";
	}
	
	@PostMapping("/remove")
	public String remove(String code) {
		service.remove(code);
		return "redirect:/category/show";
	}
	
	@PostMapping("/getOption")
	public @ResponseBody String getOption(String code, Model model) {
		OptionInfo option = service.getOptionInfo(code);
		Gson gson = new Gson();	// Gson 라이브러리를 사용
		JsonObject jsonObj = new JsonObject();	// JsonObject 객체를 생성
		jsonObj.addProperty("expose", option.isExpose());	// 필드로 저장
		jsonObj.addProperty("gnb", option.isGnb());
		return gson.toJson(jsonObj);
	}
	
	@PostMapping("/getSeed")
	public @ResponseBody String getSeed(String code, Model model) {
		log.info("code = " + code);
		return String.valueOf(service.getSeed(code));
	}
}