package com.spring.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Criteria { // 검색의 기준(페이징 처리에 사용)

	//페이징 처리에 사용
	private int pageNum;
	private int amount;
	
	// 검색 처리에 사용
	private String type;
	private String keyword;
	
	// 기본값 설정 1~10까지
	public Criteria() {
		this(1,10);
	}
	
	public Criteria(int pageNum, int amount) {
		this.pageNum = pageNum;
		this.amount = amount;
	}
	
	public String[] getTypeArr() {
		return type == null? new String[] {}:type.split("");
	}
}
