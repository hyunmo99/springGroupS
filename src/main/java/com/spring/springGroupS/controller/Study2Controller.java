package com.spring.springGroupS.controller;

import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.springGroupS.service.Study2Service;
import com.spring.springGroupS.vo.ChartVO;
import com.spring.springGroupS.vo.CrimeVO;
import com.spring.springGroupS.vo.KakaoAddressVO;
import com.spring.springGroupS.vo.TransactionVO;

@Controller
@RequestMapping("/study2")
public class Study2Controller {
	
	@Autowired
	Study2Service study2Service;
	
	
	@GetMapping("/random/randomForm")
	public String randomFormGet() {
		return "study2/random/randomForm";
	}
	
	@ResponseBody
	@PostMapping("/random/randomCheck")
	public String randomCheckPost() {
		//((int)Math.random()*(최대값-최소값+1)) + 최소값;
		return ((int)(Math.random()*(99999999-10000000+1))+10000000)+ "";
	}
	
	@ResponseBody
	@PostMapping("/random/uuidCheck")
	public String uuidCheckPost() {
		//((int)Math.random()*(최대값-최소값+1)) + 최소값;
		return UUID.randomUUID().toString().substring(0, 8);
	}
	@ResponseBody
	@PostMapping("/random/alphaNumericCheck")
	public String alphaNumericCheckPost() {
		return RandomStringUtils.randomAlphanumeric(64);
	}
	//달력출력하기
	@GetMapping("/calendar/calendar")
	public String calendarGet() {
		study2Service.getCalendar();
		return "study2/calendar/calendar";
	}
	
	//validator 폼보기
	@GetMapping("/validator/validatorForm")
	public String validatorFormGet(Model model) {
		List<TransactionVO> vos = study2Service.getUserList();
		
		model.addAttribute("vos", vos);
		return "study2/validator/validatorForm";
	}
	
	//validator User회원가입절차
	@ResponseBody
	@PostMapping(value = "/validator/validatorForm", produces="application/text; charset=utf8" )
	public String validatorFormPost(@Validated TransactionVO vo, BindingResult br) {
		if(br.hasFieldErrors()) {
			System.out.println("error 발생");
			System.out.println("error 내역" + br);
			List<ObjectError> errorList = br.getAllErrors();
			String temp="";
			for(ObjectError error : errorList) {
				System.out.println("error : " + error);
				temp = error.getDefaultMessage();
				System.out.println("temp : " + temp);
			}
			return temp;
		}
		else {
			study2Service.setValidatorFormOk(vo);
			return "회원가입완료";
		}
	}
	//validator User회원 삭제
	@ResponseBody
	@PostMapping("/validator/validatorDeleteOk")
	public int validatorDeleteOkPost(int idx) {
		return study2Service.setvalidatorDeleteOk(idx);
	}
	@GetMapping("/transaction/transactionForm")
	public String transactionFormGet(Model model) {
		List<TransactionVO> vos = study2Service.getTransactionList();
		List<TransactionVO> vos2 = study2Service.getTransactionList2();
		
		model.addAttribute("vos", vos);
		model.addAttribute("vos2", vos2);
		
		return "study2/transaction/transactionForm";
	}
	// 트랜잭션 회원 각각 가입처리(user, user2)
	@Transactional
	@PostMapping("/transaction/transactionForm")
	public String transactionFormPost(TransactionVO vo ) {
		// BackEnd 체크 완료....가정...
		study2Service.setTransactionUser1Input(vo);
		study2Service.setTransactionUser2Input(vo);
		
		return "redirect:/message/transactionUserInputOk";
	}
	//회원가입처리를 한번에 처리하기
	@Transactional
	@ResponseBody
	@RequestMapping(value = "/transaction/transaction2", method = RequestMethod.POST, produces="application/text; charset=utf8")
	public String transaction2Post(@Validated TransactionVO vo, BindingResult bindingResult, Model model) {
		System.out.println("error : " + bindingResult.hasErrors());
		
		if(bindingResult.hasFieldErrors()) {
			List<ObjectError> errorList = bindingResult.getAllErrors();
			System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
			
			String temp = "";
			for(ObjectError error : errorList) {
				temp = error.getDefaultMessage();
				System.out.println("temp : " + temp);
			}
			return temp;
		}
		else {
			study2Service.setTransactionUserTotalInput(vo);
			return "두개 테이블에 모두 저장되었습니다";
		}
	}
	// 공공데이터 API(전국 강력범죄현황)
	@GetMapping("/dataApi/dataApiForm1")
	public String dataApiForm1Get(Model model) {
		return "study2/dataApi/dataApiForm1";
	}
	
	// 공공데이터 API(강력범죄발생현환 년도별 저장처리)
	@ResponseBody
	@PostMapping("/dataApi/saveCrimeCheck")
	public void saveCrimeCheckPost(CrimeVO vo) {
		study2Service.setSaveCrimeCheck(vo);
	}
	// 공공데이터 API(강력범죄발생현환 년도별 삭제처리)
	@ResponseBody
	@PostMapping("/dataApi/deleteCrimeCheck")
	public int deleteCrimeCheckPost(int year) {
		return study2Service.setDeleteCrimeCheck(year);
	}
	
	// 공공데이터 API(강력범죄발생현환 년도별 불러오기)
	@ResponseBody
	@PostMapping("/dataApi/dbListCrimeCheck")
	public List<CrimeVO> dbListCrimeCheckPost(int year) {
		List<CrimeVO> vos = study2Service.getDbListCrimeCheck(year);
		return vos;
	}
	
	@PostMapping("/dataApi/dataApiForm1")
	public String dataApiForm1Post(Model model, int year, String policeZone) {
		List<CrimeVO> vos = study2Service.getDataApiForm1(year, policeZone);
		CrimeVO analyzeVO = study2Service.getCrimeAnalyze(year, policeZone);
		System.out.println("analyzeVO : " + analyzeVO);
		model.addAttribute("year", year);
		model.addAttribute("policeZone", policeZone);
		model.addAttribute("vos", vos);
		model.addAttribute("analyzeVO", analyzeVO);
		
		return "study2/dataApi/dataApiForm1";
	}
	
	// 차트연습폼 보기
	@GetMapping("/chart/chartForm")
	public String chartFormGet(Model model, ChartVO vo,
			@RequestParam(name="part" , defaultValue="barV", required = false ) String part) {
		model.addAttribute("part", part);
		model.addAttribute("vo", vo);
		return "study2/chart/chartForm";
	}
	// 차트연습폼 보기2
	@RequestMapping(value = "/chart2/chart2Form", method = RequestMethod.GET)
	public String chart2FormGet(Model model,
			@RequestParam(name="part", defaultValue="barVChart", required=false) String part) {
		model.addAttribute("part", part);
		return "study2/chart2/chart2Form";
	}
	
	@RequestMapping(value = "/chart2/googleChart2", method = RequestMethod.POST)
	public String googleChart2Post(Model model, ChartVO vo) {
		//System.out.println("vo : " + vo);
		model.addAttribute("vo", vo);
		return "study2/chart2/chart2Form";
	}
	// 카카오 맵 보기
	@GetMapping("/kakao/kakaomap")
	public String kakaomapGet() {
		return "study2/kakao/kakaomap";
	}
	// kakaomap(클릭한 위치에 마커표시)
	@GetMapping("/kakao/kakaoEx2")
	public String kakaoEx2Get() {
		return "study2/kakao/kakaoEx2";
	}
	//kakaomap(클릭한 위치에 마커표시 DB저장)
	@ResponseBody
	@PostMapping("/kakao/kakaoEx2")
	public int kakaoEx2Post(KakaoAddressVO vo) {
		int res = 0;
		System.out.println("오류");
		KakaoAddressVO searchVO = study2Service.getKakaoAddressSearch(vo.getAddress());
		if(searchVO==null) res = study2Service.setKakaoAddressInput(vo);
		return res;
	}
	// kakaomap(MyDB에 저장된 장소 표시/이동하기)
	@GetMapping("/kakao/kakaoEx3")
	public String kakaoEx3Get(Model model, KakaoAddressVO vo,
			@RequestParam(name="address", defaultValue = "", required = false) String address) {
		
		if(address.equals("")) {
			vo.setAddress("그린아트");
			vo.setLatitude(36.6350116442859);
			vo.setLongitude(127.45952622309285);
		}
		else vo = study2Service.getKakaoAddressSearch(address);
		List<KakaoAddressVO> addressVos = study2Service.getKakaoAddressList();
		model.addAttribute("addressVos", addressVos);
		model.addAttribute("vo", vo);
		
		return "study2/kakao/kakaoEx3";
	}
	//kakaomap(검색한 장소를 DB에서 삭제하기)
	@ResponseBody
	@PostMapping("/kakao/kakaoAddressDelete")
	public int kakaoAddressDeletePost(String address) {
		return study2Service.setKakaoAddressDelete(address);
	}
//kakaomap(검색한 장소를 KakaoDB에서 검색하기)
	@GetMapping("/kakao/kakaoEx4")
	public String kakaoEx4Get(Model model, 
			@RequestParam(name="address", defaultValue = "", required = false) String address) {
		model.addAttribute("address", address);
		return "study2/kakao/kakaoEx4";
	}

}