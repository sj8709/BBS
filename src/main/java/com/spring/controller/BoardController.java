package com.spring.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.spring.domain.BoardAttachVO;
import com.spring.domain.BoardVO;
import com.spring.domain.Criteria;
import com.spring.service.BoardService;
import com.spring.domain.PageDTO;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Log4j
@Controller
@AllArgsConstructor
@RequestMapping("/board/*")
public class BoardController {

	private BoardService service;
	
	@GetMapping("/register")
	public void register() {
	
	}
	
	@GetMapping("/list")
	public void list(Criteria cri, Model model ) {
		log.info("list: " + cri);
		model.addAttribute("list", service.getList(cri));
		//model.addAttribute("pageMaker", new PageDTO(cri, 123));
		
		int total = service.getTotal(cri);
		log.info("total : " + total);
		model.addAttribute("pageMaker", new PageDTO(cri, total));
	}
	
	@GetMapping({"/get", "/modify"})
	//화면 쪽으로 해당 번호의 게시물을 전달해야 하므로 model을 파라미터로 사용
	public void get(@RequestParam("bno") Long bno, @ModelAttribute("cri") Criteria cri, Model model) {
		log.info("/get or /modify");
		model.addAttribute("board", service.get(bno));
	}	
	
	
	// 등록 작업이 끝난 후 다시 목록 화면으로 이동하기 위해 String을 리턴 타입으로 지정(for redirect)
	// RedirectAttributes(rttr)의 경우 새롭게 등록된 게시물의 번호를 같이 전달하기 위해서 이용
	@PostMapping("/register")
	public String register(BoardVO board, RedirectAttributes rttr) {

		log.info("===============================================================================");
		log.info("register: " + board);
		if(board.getAttachList() != null) {
			board.getAttachList().forEach(attach -> log.info(attach));
		}
		
		log.info("===============================================================================");

		
		service.register(board);
		//redirect: 접투어를 사용하게 되면 spring mvc가 내부적으로 response.sendRedirect()를 처리해 줌
		rttr.addFlashAttribute("result", board.getBno());

		return "redirect:/board/list";
	}
	
	@PostMapping("/modify")
	public String modify(BoardVO board, @ModelAttribute("cri") Criteria cri, RedirectAttributes rttr) {
		log.info("modify : " +board);
		if(service.modify(board)) {
			rttr.addFlashAttribute("result", "success");
		}

		// 리다이렉트 시에 원래의 페이지로 이동하기 위해서 pageNum, amout 값을 가지고 이동하게 수정
		rttr.addAttribute("pageNum", cri.getPageNum());
		rttr.addAttribute("amount", cri.getAmount());
		rttr.addAttribute("type", cri.getType());
		rttr.addAttribute("keyword", cri.getKeyword());
		
		return "redirect:/board/list";
	}
	
	@PostMapping("/remove")
	public String remove(@RequestParam("bno") Long bno, @ModelAttribute("cri") Criteria cri, RedirectAttributes rttr) {
		log.info("remove......" + bno);
		
		List<BoardAttachVO> attachList = service.getAttachList(bno);
		if(service.remove(bno)) {
			
			//delete attach Files
			deleteFiles(attachList);
			
			rttr.addFlashAttribute("result", "success");
		}
		//위의 것들을 Criteria에 추가해준 getListLink()로 해결
		return "redirect:/board/list" + cri.getListLink();
	}
	
	// JSON 데이터 반환
	@GetMapping(value = "/getAttachList", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ResponseEntity<List<BoardAttachVO>> getAttachList(Long bno) {
		log.info("getAttachList " + bno);
		return new ResponseEntity<>(service.getAttachList(bno), HttpStatus.OK);
	}
	
	private void deleteFiles(List<BoardAttachVO> attachList) {
		if(attachList == null || attachList.size()==0) {
			return;
		}
		
		log.info("delete attach files............");
		log.info(attachList);
		
		attachList.forEach(attach -> {
			try {
				Path file = Paths.get("C:\\upload\\"+attach.getUploadPath()+"\\"+attach.getUuid()+"_"+attach.getFileName());
				Files.deleteIfExists(file);
				if(Files.probeContentType(file).startsWith("image")) {
					Path thumNail = Paths.get("C:\\upload\\"+attach.getUploadPath()+"\\s_"+attach.getUuid()+"_"+attach.getFileName());
					Files.delete(thumNail);
				}
				
			} catch(Exception e) {
				log.error("delete file error" + e.getMessage());
			}
		}); //end foreach
	}
}

