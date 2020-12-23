package com.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	//ȭ�� ������ �ش� ��ȣ�� �Խù��� �����ؾ� �ϹǷ� model�� �Ķ���ͷ� ���
	public void get(@RequestParam("bno") Long bno, @ModelAttribute("cri") Criteria cri, Model model) {
		log.info("/get or /modify");
		model.addAttribute("board", service.get(bno));
	}	
	
	
	// ��� �۾��� ���� �� �ٽ� ��� ȭ������ �̵��ϱ� ���� String�� ���� Ÿ������ ����(for redirect)
	// RedirectAttributes(rttr)�� ��� ���Ӱ� ��ϵ� �Խù��� ��ȣ�� ���� �����ϱ� ���ؼ� �̿�
	@PostMapping("/register")
	public String register(BoardVO board, RedirectAttributes rttr) {

		log.info("register: " + board);

		service.register(board);
		//redirect: ����� ����ϰ� �Ǹ� spring mvc�� ���������� response.sendRedirect()�� ó���� ��
		rttr.addFlashAttribute("result", board.getBno());

		return "redirect:/board/list";
	}
	
	@PostMapping("/modify")
	public String modify(BoardVO board, @ModelAttribute("cri") Criteria cri, RedirectAttributes rttr) {
		log.info("modify : " +board);
		if(service.modify(board)) {
			rttr.addFlashAttribute("result", "success");
		}

		// �����̷�Ʈ �ÿ� ������ �������� �̵��ϱ� ���ؼ� pageNum, amout ���� ������ �̵��ϰ� ����
		rttr.addAttribute("pageNum", cri.getPageNum());
		rttr.addAttribute("amount", cri.getAmount());
		
		return "redirect:/board/list";
	}
	
	@PostMapping("/remove")
	public String remove(@RequestParam("bno") Long bno, @ModelAttribute("cri") Criteria cri, RedirectAttributes rttr) {
		log.info("remove......" + bno);
		if(service.remove(bno)) {
			rttr.addFlashAttribute("result", "success");
		}

		rttr.addAttribute("pageNum", cri.getPageNum());
		rttr.addAttribute("amount", cri.getAmount());
		
		return "redirect:/board/list";
	}
}

