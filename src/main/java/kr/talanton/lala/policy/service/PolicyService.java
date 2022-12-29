package kr.talanton.lala.policy.service;

import java.util.List;

import kr.talanton.lala.policy.dto.PolicyDTO;
import kr.talanton.lala.policy.dto.PolicyUpdateForm;
import kr.talanton.lala.policy.entity.Policy;

public interface PolicyService {
	List<PolicyDTO> getList();
	void modify(PolicyUpdateForm form);
	
	default PolicyDTO entityToDto(Policy entity) {
		PolicyDTO dto = PolicyDTO.builder()
				.pid(entity.getPid())
				.code(entity.getCode())
				.category(entity.getCategory())
				.name(entity.getName())
				.value(entity.getValue())
				.regDate(entity.getRegDate())
				.modDate(entity.getModDate())
				.build();
		return dto;
	}
}