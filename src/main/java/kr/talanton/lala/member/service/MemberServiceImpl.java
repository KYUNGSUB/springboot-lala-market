package kr.talanton.lala.member.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import kr.talanton.lala.member.dto.MemberDTO;
import kr.talanton.lala.member.entity.Member;
import kr.talanton.lala.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class MemberServiceImpl implements MemberService {
	private final MemberRepository memberRepository;
	private final PasswordEncoder pwencoder;

	@Override
	public boolean idCheck(String userid) {
		log.info("idcheck..." + userid);
		
		Optional<Member> member = memberRepository.findById(userid);
		return member.isEmpty();
	}

	@Transactional
	@Override
	public void join(MemberDTO memberDTO) {
		String encPw = pwencoder.encode(memberDTO.getPassword());
		memberDTO.setPassword(encPw);
		Member member = dtoToEntity(memberDTO);
		memberRepository.save(member);
	}
}