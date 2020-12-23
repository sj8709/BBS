package com.spring.domain;

import org.springframework.web.util.UriComponentsBuilder;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Criteria { // �˻��� ����(����¡ ó���� ���)

	//����¡ ó���� ���
	private int pageNum;
	private int amount;
	
	// �˻� ó���� ���
	private String type;
	private String keyword;
	
	// �⺻�� ���� 1~10����
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
	
	//UriComponentsBuilder�� ���� ���� �Ķ���͵��� �����ؼ� URL�� ���·� ����� �ִ� ���
	public String getListLink() {
		UriComponentsBuilder builder = UriComponentsBuilder.fromPath("")
				.queryParam("pageNum", this.pageNum)
				.queryParam("amount", this.getAmount())
				.queryParam("type", this.getType())
				.queryParam("keyword", this.getKeyword());
		return builder.toUriString();
	}
}
