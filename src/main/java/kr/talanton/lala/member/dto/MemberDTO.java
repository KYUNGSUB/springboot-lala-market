package kr.talanton.lala.member.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberDTO {
	private String userid;
	private String password;
	private String name;
	private String email;	// email = mid + "@" + host
	private String mobile;
	private boolean marketing;
	private boolean rcv;
	private boolean enabled;
	private int visited;
	private int pursuit;
	private int accum;
	private boolean fromSocial;
	private LocalDateTime regDate;
	private LocalDateTime modDate;
}