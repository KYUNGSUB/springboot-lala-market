package kr.talanton.lala.policy.dto;

import lombok.Data;

@Data
public class PolicyUpdateForm {
	private String shopping;
	private String free;
	private String[] post;
	private String subscription;
	private String pursuit;
	private String period;
	private String dpc;
	private String dmobile;
	private String epc;
	private String emobile;
}