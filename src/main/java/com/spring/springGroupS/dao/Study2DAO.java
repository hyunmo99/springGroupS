package com.spring.springGroupS.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.spring.springGroupS.vo.CrimeVO;
import com.spring.springGroupS.vo.KakaoAddressVO;
import com.spring.springGroupS.vo.TransactionVO;

public interface Study2DAO {

	List<TransactionVO> getUserList();

	int setValidatorFormOk(@Param("vo") TransactionVO vo);

	int setvalidatorDeleteOk(@Param("idx") int idx);

	List<TransactionVO> getTransactionList();

	List<TransactionVO> getTransactionList2();

	void setTransactionUser1Input(@Param("vo") TransactionVO vo);
	
	void setTransactionUser2Input(@Param("vo") TransactionVO vo);

	void setTransactionUserTotalInput(@Param("vo") TransactionVO vo);

	void setSaveCrimeCheck(@Param("vo") CrimeVO vo);

	int setDeleteCrimeCheck(@Param("year") int year);

	List<CrimeVO> getDbListCrimeCheck(@Param("year") int year);

	List<CrimeVO> getDataApiForm1(@Param("year")int year, @Param("policeZone")String policeZone);

	CrimeVO getCrimeAnalyze(@Param("year")int year, @Param("policeZone")String policeZone);

	KakaoAddressVO getKakaoAddressSearch(@Param("address") String address);

	int setKakaoAddressInput(@Param("vo") KakaoAddressVO vo);

	List<KakaoAddressVO> getKakaoAddressList();

	int setKakaoAddressDelete(@Param("address") String address);
}
