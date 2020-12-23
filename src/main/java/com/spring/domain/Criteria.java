package com.spring.domain;

import org.springframework.web.util.UriComponentsBuilder;

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
	
	//UriComponentsBuilder는 여러 개의 파라미터들을 연결해서 URL의 형태로 만들어 주는 기능
	public String getListLink() {
		UriComponentsBuilder builder = UriComponentsBuilder.fromPath("")
				.queryParam("pageNum", this.pageNum)
				.queryParam("amount", this.getAmount())
				.queryParam("type", this.getType())
				.queryParam("keyword", this.getKeyword());
		return builder.toUriString();
	}
}
