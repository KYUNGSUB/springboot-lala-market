package kr.talanton.lala.security.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import kr.talanton.lala.member.dto.AuthMemberDTO;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {
	private RedirectStrategy redirectStratgy = new DefaultRedirectStrategy();
    private PasswordEncoder passwordEncoder;

    public CustomLoginSuccessHandler(PasswordEncoder passwordEncoder){
        this.passwordEncoder = passwordEncoder;
    }

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		 log.info("--------------------------------------");
		 log.info("onAuthenticationSuccess");
		 
		 AuthMemberDTO authMember = (AuthMemberDTO)authentication.getPrincipal();
		 boolean fromSocial = authMember.getMember().isFromSocial();
		 log.info("Need Modify Member?" + fromSocial);
		 boolean passwordResult = passwordEncoder.matches("1111", authMember.getPassword());
		 if(fromSocial && passwordResult) {
			 redirectStratgy.sendRedirect(request, response, "/member/modify?from=social");
		 }
	}
}