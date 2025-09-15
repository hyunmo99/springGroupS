package com.spring.springGroupS.service;

import java.util.List;

import com.spring.springGroupS.vo.BoardVO;

public interface BoardService {

	List<BoardVO> getBoardList(int startIndexNo, int pageSize);

	int getTotRecCnt();

	int setBoardInput(BoardVO vo);

	BoardVO getBoardContent(int idx);

}
