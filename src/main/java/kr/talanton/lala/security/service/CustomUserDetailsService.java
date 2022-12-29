package kr.talanton.lala.security.service;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import kr.talanton.lala.member.dto.AuthMemberDTO;
import kr.talanton.lala.member.dto.MemberDTO;
import kr.talanton.lala.member.entity.Member;
import kr.talanton.lala.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
	private final MemberRepository memberRepository;
	
	@Override
	public UserDetails loadUserByUsername(String userid) throws UsernameNotFoundException {
		log.info("ClubUserDetailsService loadUserByUsername " + userid);
        Optional<Member> result = memberRepository.findByUserid(userid, false);
        if(result.isEmpty()){
            throw new UsernameNotFoundException("Check User Userid or from Social ");
        }

        Member member = result.get();
        log.info("-----------------------------");
        log.info(member);
        
        AuthMemberDTO clubAuthMember = new AuthMemberDTO(
                entityToDTO(member),
                member.getRoleSet().stream()
                        .map(role -> new SimpleGrantedAuthority("ROLE_"+role.name()))
                        .collect(Collectors.toSet())
        );
        return clubAuthMember;
	}

	private MemberDTO entityToDTO(Member member) {
		MemberDTO dto = MemberDTO.builder()
				.userid(member.getUserid())
				.password(member.getPassword())
				.name(member.getName())
				.email(member.getEmail())
				.mobile(member.getMobile())
				.marketing(member.isMarketing())
				.rcv(member.isRcv())
				.enabled(member.isEnabled())
				.visited(member.getVisited())
				.pursuit(member.getPursuit())
				.accum(member.getAccum())
				.fromSocial(member.isFromSocial())
				.regDate(member.getRegDate())
				.modDate(member.getModDate())
				.build();
		return dto;
	}
}