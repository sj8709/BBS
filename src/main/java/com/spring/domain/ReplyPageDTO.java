package com.spring.domain;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@AllArgsConstructor
@Getter
public class ReplyPageDTO {
	// 객체 생성 시에 편리하도록 @AllArgsConstructor를 이용해서 replyCnt와 list를 생성자의 파라미터로 처리함
	private int replyCnt;
	private List<ReplyVO> list;

}
