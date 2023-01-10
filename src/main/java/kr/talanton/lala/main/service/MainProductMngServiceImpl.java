package kr.talanton.lala.main.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.stereotype.Service;

import kr.talanton.lala.main.dto.MainPlistDTO;
import kr.talanton.lala.main.dto.MainPmainDTO;
import kr.talanton.lala.main.entity.MainPlist;
import kr.talanton.lala.main.entity.MainPmain;
import kr.talanton.lala.main.repository.MainPmainRepository;
import kr.talanton.lala.product.entity.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class MainProductMngServiceImpl implements MainProductMngService {
	private final MainPmainRepository repository;
	
	@Override
	public MainPmainDTO getMainProductManagementInfo(String kind) {
		log.info("getMainProductManagementInfo()");
		Object[] result = repository.getMainProductManagementInfo(kind);
		if(result == null || result.length == 0) {
			return null;
		}
		Object[] cluster = (Object[])result[0];
		List<MainPlistDTO> itemList = new ArrayList<>();
		for(Object data : result) {
			Object[] en = (Object[])data;
			MainPlistDTO dto = entityToDTO((MainPmain)en[0], (MainPlist)en[1], (Product)en[2]);
			itemList.add(dto);
		}
		
		return entityToDTO((MainPmain)cluster[0], itemList);
	}

	
}