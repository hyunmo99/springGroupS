package com.spring.springGroupS.vo;

import lombok.Data;

@Data
public class ReviewVO {
	// review테이블의 필드정의
  private int idx;
  private String part;
  private int partIdx;
  private String mid;
  private String nickName;
  private int star;
  private String content;
  private String rDate;
  
  // reviewReply테이블의 필드정의
  private int replyIdx;
  private int reviewIdx;	// 앞에 정의한 idx가 reviewIdx이므로 이줄은 생략처리해도 되지만, 이곳에선 사용하기로 한다.
  private String replyMid;
  private String replyNickName;
  private String replyRDate;
  private String replyContent;
	
}
