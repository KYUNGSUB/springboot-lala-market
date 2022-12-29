package kr.talanton.lala.category.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OptionInfo {
	private boolean expose;	// 카테고리 노출 여부
	private boolean gnb;	// GNB 노출 여부
}