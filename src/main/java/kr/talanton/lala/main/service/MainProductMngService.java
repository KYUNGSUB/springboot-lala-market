package kr.talanton.lala.main.service;

import java.util.List;

import kr.talanton.lala.main.dto.MainPlistDTO;
import kr.talanton.lala.main.dto.MainPmainDTO;
import kr.talanton.lala.main.entity.MainPlist;
import kr.talanton.lala.main.entity.MainPmain;
import kr.talanton.lala.product.dto.ProductDTO;
import kr.talanton.lala.product.entity.Product;

public interface MainProductMngService {
	MainPmainDTO getMainProductManagementInfo(String kind);
	
	default MainPmainDTO entityToDTO(MainPmain entity, List<MainPlistDTO> list) {
		MainPmainDTO dto = MainPmainDTO.builder()
				.kind(entity.getKind())
				.title(entity.getTitle())
				.display(entity.getDisplay())
				.list(list)
				.regDate(entity.getRegDate())
				.modDate(entity.getModDate())
				.build();
		return dto;
	}
	
	default MainPlistDTO entityToDTO(MainPmain mainPmain, MainPlist mainPlist, Product product) {
		ProductDTO productDTO = ProductDTO.builder()
				.pid(product.getPid())
				.c2_name(product.getCategory2().getName())
				.name(product.getName())
				.price(product.getPrice())
				.deposit(product.getDeposit())
				.readCount(product.getReadcount())
				.build();
		MainPlistDTO dto = MainPlistDTO.builder()
				.lid(mainPlist.getLid())
				.kind(mainPmain.getKind())
				.position(mainPlist.getPosition())
				.product(productDTO)
				.regDate(mainPlist.getRegDate())
				.modDate(mainPlist.getModDate())
				.build();
		return dto;
	}
}