package kr.talanton.lala.product.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.talanton.lala.category.service.CategoryService;
import kr.talanton.lala.common.dto.PageRequestDTO;
import kr.talanton.lala.common.utils.Common;
import kr.talanton.lala.product.dto.ProductPageRequestDTO;
import kr.talanton.lala.product.dto.UploadProductForm;
import kr.talanton.lala.product.service.ProductService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller
@RequestMapping("/product/*")
@AllArgsConstructor
@Log4j2
public class ProductController {
	private CategoryService categoryService;
	
	private ProductService productService;
	
//	private PolicyService policyService;

	@GetMapping("/register")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public void register(Model model) {
		log.info("/product/register...");
		model.addAttribute("cList", categoryService.getSecondList(Common.STYLE_SHOP_CATEGORY));
	}

	@PostMapping("/register")
	@PreAuthorize("hasRole('ROLE_ADMIN')") 	// 관리자만 상품 등록 가능
	public String registerAction(UploadProductForm form, RedirectAttributes rttr) {
		log.info("registerAction..." + form);
		productService.register(form);
		rttr.addFlashAttribute("result", form.getPid()); // 추가된 상품 번호를 출력
		return "redirect:/product/list";
	}

	@GetMapping("/list")
	public void list(ProductPageRequestDTO productPageRequestDTO, Model model) {
		log.info("list: " + productPageRequestDTO);
		model.addAttribute("result", productService.getList(productPageRequestDTO));
	}
	
	@GetMapping("/slist")
	public void styleList(ProductPageRequestDTO requestDTO, Model model) {
		log.info("list: " + requestDTO);
		model.addAttribute("result", productService.getList(requestDTO));
	}
}