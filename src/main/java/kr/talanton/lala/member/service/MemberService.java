package kr.talanton.lala.member.service;

import java.util.HashSet;

import kr.talanton.lala.member.dto.MemberDTO;
import kr.talanton.lala.member.entity.Member;
import kr.talanton.lala.member.entity.MemberRole;

public interface MemberService {
	boolean idCheck(String userid);
	void join(MemberDTO member);
	
	default Member dtoToEntity(MemberDTO dto) {
		Member member = Member.builder()
				.userid(dto.getUserid())
				.password(dto.getPassword())
				.name(dto.getName())
				.enabled(false)
				.email(dto.getEmail())
				.mobile("000-0000-0000")
				.marketing(dto.isMarketing())
				.rcv(dto.isRcv())
				.visited(0)
				.pursuit(0)
				.accum(0)
				.fromSocial(false)
				.roleSet(new HashSet<MemberRole>())
				.build();
		member.addMemberRole(MemberRole.USER);
		return member;
	}
}