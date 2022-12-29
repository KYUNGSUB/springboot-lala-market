package kr.talanton.lala.banner.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import kr.talanton.lala.attach.entity.AttachFile;
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
public class BannerMapping {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long mid;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private AttachFile attach;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Banner banner;
	
	@Column(length=100)
	private String url;
	
	@Column(length=100)
	private String alt;
	
	@Column(length=10)
	private String target;
	
	@Column(columnDefinition = "int default 0")
	private int loginBefore;
}