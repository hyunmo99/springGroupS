package com.spring.springGroupS.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.spring.springGroupS.common.Pagenation;
import com.spring.springGroupS.service.BoardService;
import com.spring.springGroupS.vo.BoardVO;

@Controller
@RequestMapping("/board")
public class BoardController {
	 
	@Autowired
	BoardService boardService;
	
	@Autowired
	Pagenation pagenation;
	
	@GetMapping("/boardList")
	public String boardListGet(Model model,
				@RequestParam(name="pag", defaultValue = "1", required = false) int pag,
				@RequestParam(name="pageSize", defaultValue = "10", required = false) int pageSize
			) {
		
		
		int totRecCnt = boardService.getTotRecCnt();
		int totPage = (totRecCnt % pageSize) == 0 ? totRecCnt / pageSize : (totRecCnt / pageSize) + 1;
		int startIndexNo = (pag - 1) * pageSize;
		int curScrStartNo = totRecCnt - startIndexNo;
		
		int blockSize = 3;
		int curBlock = (pag - 1) / blockSize;
		int lastBlock = (totPage - 1) / blockSize;	
		List<BoardVO> vos = boardService.getBoardList(startIndexNo, pageSize);
		
		model.addAttribute("pag", pag);
		model.addAttribute("pageSize", pageSize);
		//model.addAttribute("totRecCnt", totRecCnt);
		model.addAttribute("totPage", totPage);
		model.addAttribute("curScrStartNo", curScrStartNo);
		model.addAttribute("blockSize", blockSize);
		model.addAttribute("curBlock", curBlock);
		model.addAttribute("lastBlock", lastBlock);
		
		model.addAttribute("vos", vos);
		return "board/boardList";
	}
	
	// 게시글 등록 폼보기
	@GetMapping("/boardInput")
	public String boardInputGet() {
		return "board/boardInput";
	}
	// 게시글 DB에 등록하기
	@PostMapping("/boardInput")
	public String boardInputPost(BoardVO vo) {
		int res = boardService.setBoardInput(vo);
		
		if(res != 0) return "redirect:/message/boardInputOk";
		else return "redirect:/message/boardInputNo";
	}
	// 게시글 등록 폼보기
	@GetMapping("/boardContent")
	public String boardContentGet(Model model, int idx, int pag, int pageSize) {
		BoardVO vo = boardService.getBoardContent(idx);
		model.addAttribute("vo", vo);
		model.addAttribute("pag", pag);
		model.addAttribute("pageSize", pageSize);
		return "board/boardContent";
	}
}
