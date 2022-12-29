package kr.talanton.lala.member.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;

import kr.talanton.lala.common.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class Member extends BaseEntity {
	@Id
	@Column(length=50, nullable=false)
	private String userid;
	@Column(length=64, nullable=false)
	private String password;
	@Column(length=100, nullable=false)
	private String name;
	@Column(length=20)
	private String email;
	@Column(length=15)
	private String mobile;
	private boolean marketing;
	private boolean rcv;
	private boolean enabled;
	@Column(name="visited", columnDefinition="int default 0")
	private int visited;
	@Column(name="pursuit", columnDefinition="int default 0")
	private int pursuit;
	@Column(name="accum", columnDefinition="int default 0")
	private int accum;
	private boolean fromSocial;
	
	@ElementCollection(fetch = FetchType.LAZY)
    @Builder.Default
    private Set<MemberRole> roleSet = new HashSet<>();

    public void addMemberRole(MemberRole clubMemberRole){
        roleSet.add(clubMemberRole);
    }

	public void setMarketing(boolean marketing) {
		this.marketing = marketing;
	}
}