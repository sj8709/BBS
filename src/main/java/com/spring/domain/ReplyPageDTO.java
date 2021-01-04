package com.spring.domain;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@AllArgsConstructor
@Getter
public class ReplyPageDTO {
	// ��ü ���� �ÿ� ���ϵ��� @AllArgsConstructor�� �̿��ؼ� replyCnt�� list�� �������� �Ķ���ͷ� ó����
	private int replyCnt;
	private List<ReplyVO> list;

}
