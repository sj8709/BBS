package com.spring.mapper;

import java.util.List;

import com.spring.domain.ReplyVO;

public interface ReplyMapper {
	
	public List<ReplyVO> getList();

	public int insert(ReplyVO vo);
	
	public ReplyVO read(Long rno);
	
	public int delete(Long rno);
	
	public int update(ReplyVO vo);
}
