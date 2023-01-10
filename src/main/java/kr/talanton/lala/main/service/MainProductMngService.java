package kr.talanton.lala.main.service;

import kr.talanton.lala.main.dto.MainPmainDTO;
import kr.talanton.lala.main.entity.MainPmain;

public interface MainProductMngService {
	MainPmainDTO getMainProductManagementInfo(String kind);
	
	default MainPmainDTO entityToDTO(MainPmain entity) {
		return null;
	}
}