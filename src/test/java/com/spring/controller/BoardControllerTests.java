package com.spring.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
// MockMvc=가짜 mvc(가짜로 URL과 파라미터 등을 브라우저 사용하는 것처럼 만들어서 Controller를 실행해 볼 수 있음)
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration //Servlet의 ServletContext를 이용하기 위한 어노테이션
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring/root-context.xml",
	"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"})
@Log4j
public class BoardControllerTests {

	@Setter(onMethod_ = {@Autowired})
	private WebApplicationContext ctx;
	
	private MockMvc mockMvc;
	
	@Before //모든 테스트전에 매번 실행되는 어노테이션
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
	}
	
	@Test
	public void testList() throws Exception {
		
		log.info(mockMvc.perform(MockMvcRequestBuilders.get("/board/list")
				.param("pageNum", "1")
				.param("amount", "50"))
				.andReturn()
				.getModelAndView()
				.getModelMap());
	}
//	
//	@Test
//	public void testGet() throws Exception{
//		log.info(mockMvc.perform(MockMvcRequestBuilders
//				.get("/board/get")
//				.param("bno", "1"))
//				.andReturn()
//				.getModelAndView().getModelMap());
//	}
//	
//	@Test
//	public void testRegiser() throws Exception{
//		
//		String resultPage = mockMvc.perform(MockMvcRequestBuilders.post("/board/register")
//				.param("title", "test new title")
//				.param("content", "test new content")
//				.param("writer", "user00"))
//				.andReturn().getModelAndView().getViewName();
//		
//		log.info(resultPage);
//	}
//	
//	@Test
//	public void testModify() throws Exception {
//		
//			String resultPage = mockMvc.perform(MockMvcRequestBuilders.post("/board/modify")
//					.param("bno", "1")
//					.param("title", "update new title")
//					.param("content", "update new content")
//					.param("writer", "user00"))
//					.andReturn().getModelAndView().getViewName();
//			
//			log.info(resultPage);
//	}
//	
//	@Test
//	public void testRemove() throws Exception {
//		String resultPage = mockMvc.perform(MockMvcRequestBuilders.post("/board/remove")
//				.param("bno", "36"))
//				.andReturn().getModelAndView().getViewName();
//		log.info(resultPage);
//	}

}
