package kr.talanton.lala.category.service;

import java.util.List;

import kr.talanton.lala.category.dto.CategoryDTO;
import kr.talanton.lala.category.dto.MoveRequest;
import kr.talanton.lala.category.dto.OptionInfo;
import kr.talanton.lala.category.entity.Category;

public interface CategoryService {
	List<CategoryDTO> getList();			// 카테고리 목록 정보 가져오기
	String codeCheck(String code);		// 카테고리 코드 중복 확인
	void add(CategoryDTO category);			// 카테고리 추가
	void moveCategory(MoveRequest mr);	// 카테고리 전시 순서 변경
	void modify(String code, boolean expose);	// 카테고리 노출여부 수정
	void modify(String code, boolean expose, boolean gnb);	// 카테고리 노출여부 수정
	int remove(String code);			// 카테고리 삭제
	OptionInfo getOptionInfo(String code);	// 카테고리 노출여부 정보 가져오기
	int getSeed(String parent);			// 2차 카테고리 코드를 생성하기 위한 값 가져오기
	List<CategoryDTO> getSecondList(String parent);	// 2차 카테고리 목록 가져오기
	
	default Category dtoToEntity(CategoryDTO dto) {
		Category entity = Category.builder()
				.code(dto.getCode())
				.name(dto.getName())
				.expose(dto.isExpose())
				.gnb(dto.isGnb())
				.step(dto.getStep())
				.seq(dto.getSeq())
				.parent(dto.getParent())
				.build();
		return entity;
	}
	
	
	default CategoryDTO entityToDto(Category entity) {
		CategoryDTO dto = CategoryDTO.builder()
				.code(entity.getCode())
				.name(entity.getName())
				.expose(entity.isExpose())
				.gnb(entity.isGnb())
				.step(entity.getStep())
				.seq(entity.getSeq())
				.parent(entity.getParent())
				.regDate(entity.getRegDate())
				.modDate(entity.getModDate())
				.build();
		return dto;
	}
}