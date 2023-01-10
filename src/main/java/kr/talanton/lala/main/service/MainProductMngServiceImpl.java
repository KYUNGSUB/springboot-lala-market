package kr.talanton.lala.main.service;

import org.springframework.stereotype.Service;

import kr.talanton.lala.main.dto.MainPmainDTO;
import kr.talanton.lala.main.entity.MainPmain;
import kr.talanton.lala.main.repository.MainPmainRepository;
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
		MainPmain main = repository.getMainProductManagementInfo(kind);
		return entityToDTO(main);
	}
}