package kr.talanton.lala.member.repository;

import java.util.HashSet;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import kr.talanton.lala.member.entity.Member;
import kr.talanton.lala.member.entity.MemberRole;

@SpringBootTest
public class MemberRepositoryTests {
	@Autowired
	private MemberRepository repository;

	@Autowired
	private PasswordEncoder passwordEncoder;

//	@Test
	public void insertDummies() {
		//1 - 80까지는 USER만 지정
		//81- 90까지는 USER,MANAGER
		//91- 100까지는 USER,MANAGER,ADMIN

		IntStream.rangeClosed(0,9).forEach(i -> {
			Member member = Member.builder()
                  .userid("user" + i)
                  .password(  passwordEncoder.encode("!pw0"+i) )
                  .name("사용자"+i)
                  .enabled(false)
                  .mobile("010-2222-333"+i)
                  .email("user"+i+"@zerock.org")
                  .fromSocial(false)
                  .roleSet(new HashSet<MemberRole>())
                  .build();
  
          //default role
          member.addMemberRole(MemberRole.USER);
          if(i >= 6){
              member.addMemberRole(MemberRole.MANAGER);
          }
          if(i >= 8){
              member.addMemberRole(MemberRole.ADMIN);
          }
          repository.save(member);
      });
  }
}