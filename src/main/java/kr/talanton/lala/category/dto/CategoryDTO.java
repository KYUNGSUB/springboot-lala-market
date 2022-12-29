package kr.talanton.lala.category.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryDTO {
	private String name;	// 카테고리 이름
	private String code;	// 카테고리 코드 (primary Key)
	private boolean expose;	// 카테고리 노출 여부
	private boolean gnb;	// GNB 진열 여부
	private int step;		// 단계(1차 또는 2차)
	private int seq;		// 배치 순서
	private String parent;	// 1차 카테고리 코드(2차 카테고리 경우)
	private LocalDateTime regDate;
	private LocalDateTime modDate;
	private List<CategoryDTO> list;	// 자식 카테고리 목록
}