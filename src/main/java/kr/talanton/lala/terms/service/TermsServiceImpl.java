package kr.talanton.lala.terms.service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import kr.talanton.lala.terms.dto.TermsDTO;
import kr.talanton.lala.terms.entity.Terms;
import kr.talanton.lala.terms.repository.TermsRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TermsServiceImpl implements TermsService {
	private TermsRepository repository;

	@Override
	public List<TermsDTO> getList() {
		List<Terms> list = repository.findAll();
		Function<Terms, TermsDTO> fn = (entity -> entityToDto(entity));
		return list.stream().map(fn).collect(Collectors.toList());
	}

	@Override
	public void add(TermsDTO termsDTO) {
		Terms terms = dtoToEntity(termsDTO);
		repository.save(terms);
	}

	@Override
	public void modify(TermsDTO termsDTO) {
		Terms terms = repository.getOne(termsDTO.getTid());
		terms.changeTitle(termsDTO.getTitle());
		terms.changeContent(termsDTO.getContent());
		terms.changeExpose(termsDTO.isExpose());
		terms.changeMandatory(termsDTO.isMandatory());
		repository.save(terms);
	}

	@Override
	public void remove(Long tid) {
		repository.deleteById(tid);
	}
}