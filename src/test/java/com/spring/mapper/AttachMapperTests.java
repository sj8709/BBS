package com.spring.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.spring.domain.BoardAttachVO;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class AttachMapperTests {
	
	@Setter(onMethod_ = @Autowired)
	private BoardAttachMapper mapper;
	
	@Test
	public void testInsert() {
		BoardAttachVO board = new BoardAttachVO();
		board.setUuid("new title");
		board.setFileName("new content");
		board.setFileType(true);
		board.setUploadPath("test path");
		board.setBno(53L);
		
		mapper.insert(board);
		log.info(board);
	}
}
