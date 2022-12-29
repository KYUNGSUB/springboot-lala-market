package kr.talanton.lala.terms.service;

import java.util.List;

import kr.talanton.lala.terms.dto.TermsDTO;
import kr.talanton.lala.terms.entity.Terms;

public interface TermsService {
	List<TermsDTO> getList();
	void add(TermsDTO terms);
	void modify(TermsDTO terms);
	void remove(Long tid);
	
	default TermsDTO entityToDto(Terms entity) {
		TermsDTO dto = TermsDTO.builder()
				.tid(entity.getTid())
				.title(entity.getTitle())
				.content(entity.getContent())
				.expose(entity.isExpose())
				.mandatory(entity.isMandatory())
				.regDate(entity.getRegDate())
				.modDate(entity.getModDate())
				.build();
		return dto;
	}
	
	default Terms dtoToEntity(TermsDTO dto) {
		Terms entity = Terms.builder()
				.tid(dto.getTid())
				.title(dto.getTitle())
				.content(dto.getContent())
				.expose(dto.isExpose())
				.mandatory(dto.isMandatory())
				.build();
		return entity;
	}
}