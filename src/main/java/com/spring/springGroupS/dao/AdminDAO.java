package com.spring.springGroupS.dao;

import org.apache.ibatis.annotations.Param;

public interface AdminDAO {

	int setMemberLevelChange(@Param("level") int level, @Param("idx")int idx);

}
