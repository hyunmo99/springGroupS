package com.spring.springGroupS.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.spring.springGroupS.vo.BoardVO;

public interface BoardDAO {

	List<BoardVO> getBoardList(@Param("startIndexNo") int startIndexNo, @Param("pageSize")int pageSize);

	int getTotRecCnt();

	int setBoardInput(@Param("vo") BoardVO vo);

	BoardVO getBoardContent(@Param("idx") int idx);
}
