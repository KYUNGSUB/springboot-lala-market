package kr.talanton.lala.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import kr.talanton.lala.security.filter.ApiCheckFilter;
import kr.talanton.lala.security.filter.ApiLoginFailHandler;
import kr.talanton.lala.security.filter.ApiLoginFilter;
import kr.talanton.lala.security.handler.CustomLoginSuccessHandler;
import kr.talanton.lala.security.service.CustomUserDetailsService;
import kr.talanton.lala.security.util.JWTUtil;
import lombok.extern.log4j.Log4j2;

@Configuration
@Log4j2
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
    private CustomUserDetailsService userDetailsService;
	
	@Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
	
	@Override
    protected void configure(HttpSecurity http) throws Exception {
		http.formLogin().loginPage("/member/login"); //인가/인증에 문제시 로그인 화면
//        http.csrf().disable();
        http.logout().logoutUrl("/member/logout");
        http.oauth2Login().loginPage("/member/login").successHandler(successHandler());
        http.rememberMe().tokenValiditySeconds(60*60*7).userDetailsService(userDetailsService);  //7days
        
        http.addFilterBefore(apiCheckFilter(), UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(apiLoginFilter(), UsernamePasswordAuthenticationFilter.class);
    }
	
	@Bean
    public CustomLoginSuccessHandler successHandler() {
        return new CustomLoginSuccessHandler(passwordEncoder());
    }

	@Bean
    public ApiCheckFilter apiCheckFilter() {
        return new ApiCheckFilter("/notes/**/*", jwtUtil());
    }

	@Bean
    public ApiLoginFilter apiLoginFilter() throws Exception{
        ApiLoginFilter apiLoginFilter =  new ApiLoginFilter("/api/login", jwtUtil());
        apiLoginFilter.setAuthenticationManager(authenticationManager());
        apiLoginFilter.setAuthenticationFailureHandler(new ApiLoginFailHandler());
        return apiLoginFilter;
    }

	@Bean
    public JWTUtil jwtUtil() {
        return new JWTUtil();
    }
}