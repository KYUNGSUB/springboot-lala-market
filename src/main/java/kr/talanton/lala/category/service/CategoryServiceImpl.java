package kr.talanton.lala.category.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.talanton.lala.category.dto.CategoryDTO;
import kr.talanton.lala.category.dto.MaxCount;
import kr.talanton.lala.category.dto.MoveRequest;
import kr.talanton.lala.category.dto.OptionInfo;
import kr.talanton.lala.category.entity.Category;
import kr.talanton.lala.category.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class CategoryServiceImpl implements CategoryService {	// 싱글톤으로 동작
	private final CategoryRepository repository;
	
	@Transactional
	@Override
	public List<CategoryDTO> getList() {	// 카테고리 목록을 데이터베이스로부터 가져온다.
		List<CategoryDTO> result = new ArrayList<>();
		List<Category> list = repository.getListFirst();	// 1차 카테고리 목록을 가져온다.
		for(Category category : list) {	// 2차 카테고리 정보를 가져온다.
			CategoryDTO dto = entityToDto(category);
			dto.setList(convertEntityListToDtoList(repository.getListSecond(category.getCode())));
			result.add(dto);
		}
		return result;
	}

	@Override
	public List<CategoryDTO> getSecondList(String parent) {	// 카테고리 목록을 데이터베이스로부터 가져온다.
		List<Category> sndList = repository.getListSecond(parent);
		return convertEntityListToDtoList(sndList);	// 2차 카테고리 목록을 가져온다.
	}
	
	private List<CategoryDTO> convertEntityListToDtoList(List<Category> sndList) {
		List<CategoryDTO> list = new ArrayList<>();
		for(Category category : sndList) {
			list.add(entityToDto(category));
		}
		return list;
	}

	@Override
	public String codeCheck(String code) {
		Optional<Category> result = repository.findById(code);
		if(result.isPresent()) {
			return "using";
		} else {
			return "not using";
		}
	}
	
	@Transactional
	@Override
	public void add(CategoryDTO dto) {
		if(dto.getParent() == null) {
			dto.setSeq(repository.getSeq1(dto.getStep()) + 1);
		} else {
			dto.setSeq(repository.getSeq2(dto.getStep(), dto.getParent()) + 1);
		}
		
		Category category = dtoToEntity(dto);
		repository.save(category);
	}
	
	@Transactional
	@Override
	public void moveCategory(MoveRequest mr) {
		int result = -1;
		int direction;	// 배치순서(seq)값을 1 증가(down), 1 감소(-1, up) 나타낸다.
		if(mr.getDirection().equals("up")) {	// upward
			direction = -1;	// up
		} else {
			direction = 1;	// down
		}
		Category other = null;	// 이동할 상대 카테고리
		if(mr.getStep() == 1) {	// 1차 카테고리
			other = repository.getFirstCategoryBySeq(mr.getSeq() + direction);
		} else {				// 2차 카테고리
			other = repository.getSecondCategoryBySeq(mr.getParent(), mr.getSeq() + direction);
		}
		// 현재 카테고리의 배치 순서를 변경
		Category cur = repository.getById(mr.getCode());
		cur.changeSeq(mr.getSeq() + direction);
		repository.save(cur);
		// 상대 카테고리의 배치 순서를 변경
		other.changeSeq(mr.getSeq());
		repository.save(other);
	}
	
	// 2차 카테고리 정보 변경 : 노출 여부(expose)
	@Transactional
	@Override
	public void modify(String code, boolean expose) {
		Category category = repository.getById(code);
		category.changeExpose(expose);
		repository.save(category);
	}

	// 1차 카테고리 정보 변경 : 노출 여부(expose), GNB 표시 여부(gnb)
	@Transactional
	@Override
	public void modify(String code, boolean expose, boolean gnb) {
		Category category = repository.getById(code);
		category.changeExpose(expose);
		category.changeGnb(gnb);
		repository.save(category);
	}
	
	// code값을 가지는 상품 카테고리 정보를 삭제한다.
	@Transactional
	@Override
	public int remove(String code) {
		int result = -1;
		// 카테고리 정보를 검색한다.
		Category category = repository.getById(code);	// 카테고리 정보 가져오기
		// 지정 카테고리 정보를 삭제
		repository.deleteById(code);			// 카테고리 삭제
		// 영향을 받은 카테고리에 대하여 seq 갱신
		if(category.getParent() == null) {
			result = repository.updateSequence1(category.getSeq());
		} else {
			result = repository.updateSequence2(category.getParent(), category.getSeq());
		}
		
		// 삭제하는 카테고리가 1차 카테고리이면, 산하의 2차 카테고리들도 삭제
		if(category.getStep() == 1) {	// 1차 카테고리면, 산하 2차 카테고리를 삭제
			result = repository.deleteSecondCategory(category.getCode());
		}
		return result;
	}
	
	// 카테고리 노출 여부(expose)와 GNB 노출 여부(gnb)를 데이터베이스로부터 가져온다.
	@Override
	public OptionInfo getOptionInfo(String code) {
		Object[] oArr = repository.getOptionInfo(code);
		return new OptionInfo((Boolean)oArr[0], (Boolean)oArr[1]);
	}
	
	@Transactional
	@Override
	public int getSeed(String parent) {
		Object[] oArr = repository.getMaxCount(parent);
		Object[] mArr = (Object[])oArr[0];
		if(mArr[0] == null) {
			return 1;
		}
		int count = ((BigInteger)mArr[1]).intValue();
		MaxCount mc = new MaxCount(Integer.parseInt(mArr[0].toString()), count);
		if(mc.getV1() == mc.getV2()) {
			return mc.getV1() + 1;
		}
//		String[] codes = repository.getCodes(parent, mc.getV2());
		List<Object[]> codes = repository.getCodes(parent);
		int[] codesNum = new int[mc.getV2()];
		for(int i = 0;i < codes.size();i++) {
			codesNum[i] = Integer.parseInt((String)codes.get(i)[0]);
		}
		Arrays.sort(codesNum);	// 오름차순으로 정렬
		for(int i = 0;i < codes.size();i++) {
			if((i+1) == codesNum[i])
				continue;
			return i + 1;
		}
		return -1;
	}
}