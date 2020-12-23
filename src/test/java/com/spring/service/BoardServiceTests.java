package com.spring.service;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.spring.domain.BoardVO;
import com.spring.domain.Criteria;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class BoardServiceTests {

	@Setter(onMethod_ = { @Autowired })
	private BoardService service;

//	@Test
//	public void testExist() {
//
//		log.info(service);
//		assertNotNull(service);
//	}
//	
//	@Test
//	public void testGetList() {
//		service.getList().forEach(board -> log.info(board));
//	}

	@Test
	public void testGetList() {
	service.getList(new Criteria(1, 10)).forEach(board -> log.info(board));
}
//	
//	@Test
//	public void testRegister() {
//		BoardVO board = new BoardVO();
//		board.setTitle("new title");
//		board.setContent("new content");
//		board.setWriter("newbie");
//		
//		service.register(board);
//		
//		log.info("new key number: " + board.getBno());
//	}
//	
//	
//	@Test
//	public void testGet() {
//		log.info(service.get(1L));
//	}
	
//	@Test
//	public void testUpdate() {
//		BoardVO board = service.get(1L);
//		if(board == null) {
//			return;
//		}
//		board.setTitle("update title");
//		log.info("modify result: " + service.modify(board));
//	}
//	
//	@Test
//	public void testDelete() {
//		log.info("remove reslult : " + service.remove(2L));
//	}

}
