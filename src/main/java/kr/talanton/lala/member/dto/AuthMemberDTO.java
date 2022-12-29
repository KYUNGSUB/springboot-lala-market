package kr.talanton.lala.member.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

@Getter
@Setter
@ToString
public class AuthMemberDTO extends User implements OAuth2User {
	private String name;
	private MemberDTO member;
    private Map<String, Object> attr;

    public AuthMemberDTO(MemberDTO member,
                             Collection<? extends GrantedAuthority> authorities, Map<String, Object> attr) {
        this(member, authorities);
        this.attr = attr;
    }

    public AuthMemberDTO(MemberDTO member,
                             Collection<? extends GrantedAuthority> authorities) {
        super(member.getUserid(), member.getPassword(), authorities);
        this.member = member;
        this.name = member.getName();
    }
    
    @Override
    public Map<String, Object> getAttributes() {
        return this.attr;
    }

	@Override
	public String getName() {
		return name;
	}
}