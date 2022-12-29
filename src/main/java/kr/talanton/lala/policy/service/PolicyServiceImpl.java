package kr.talanton.lala.policy.service;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.talanton.lala.policy.dto.PolicyDTO;
import kr.talanton.lala.policy.dto.PolicyUpdateForm;
import kr.talanton.lala.policy.entity.Policy;
import kr.talanton.lala.policy.repository.PolicyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
public class PolicyServiceImpl implements PolicyService {
	private final PolicyRepository repository;

	@Override
	public List<PolicyDTO> getList() {
		log.info("getList...");

		List<Policy> result = repository.findAll();
		Function<Policy, PolicyDTO> fn = (entity -> entityToDto(entity));
		return result.stream().map(fn).collect(Collectors.toList());
	}

	@Override
	@Transactional
	public void modify(PolicyUpdateForm form) {
		// code=1
		processing(1, "배송정책", "기본배송료", form.getShopping());
		processing(2, "배송정책", "무료 배송", form.getFree());
		processing(3, "포인트 정책", "가입 포인트", form.getSubscription());
		processing(4, "포인트 정책", "구매 포인트", form.getPursuit());
		processing(5, "주문 취소 정책", "무통장 입금 시", form.getPeriod());
		processing(6, "공통 배송 안내 등록", "PC", form.getDpc());
		processing(7, "공통 배송 안내 등록", "Mobile", form.getDmobile());
		processing(8, "공통 교환 및 반품 안내 등록", "PC", form.getEpc());
		processing(9, "공통 교환 및 반품 안내 등록", "Mobile", form.getEmobile());
		processing2(10, "배송정책", "배송불가 지역", form.getPost());
	}
	
	private void processing(int code, String category, String name, String value) {
		log.info("code="+code + ", value=" + value);
		Policy policy = Policy.builder()
				.code(code)
				.category(category)
				.name(name)
				.value(value)
				.build();
		repository.save(policy);
	}

	// 배송 불가 지역 처리
	private void processing2(int code, String category, String name, String[] values) {
		log.info("code=" + code + ", values=" + Arrays.toString(values));
		repository.deleteByCode(code);
		if(values == null) {
			return;
		}
		
		for(String value : values) {
			Policy policy = Policy.builder()
					.code(code)
					.category(category)
					.name(name)
					.value(value)
					.build();
			repository.save(policy);
		}
	}
}