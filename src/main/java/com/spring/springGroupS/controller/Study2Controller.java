package com.spring.springGroupS.controller;

import java.io.File;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
import org.springframework.web.multipart.MultipartFile;

import com.spring.springGroupS.service.Study2Service;
import com.spring.springGroupS.vo.ChartVO;
import com.spring.springGroupS.vo.CrimeVO;
import com.spring.springGroupS.vo.KakaoAddressVO;
import com.spring.springGroupS.vo.QrCodeVO;
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
	
	// 날씨 API 폼
	@GetMapping("/weather/weatherForm")
	public String weatherFormGet(Model model) {
		List<KakaoAddressVO> jiyukVos = study2Service.getKakaoAddressList();
		System.out.println("jiyukVos : " + jiyukVos);
		model.addAttribute("jiyukVos", jiyukVos);
		return "study2/weather/weatherForm";
	}
	// QR Code 연습 폼
	@GetMapping("/qrCode/qrCodeForm")
	public String qrCodeCreateGet() {
		return "study2/qrCode/qrCodeForm";
	}
	
	// QR Code 생성하기
	@ResponseBody
	@PostMapping("/qrCode/qrCodeCreate")
	public String qrCodeCreatePost(HttpServletRequest request, HttpSession session, QrCodeVO vo) {
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/qrCode/");
		vo.setFlag(0);		vo.setMid((String) session.getAttribute("sMid"));
		return study2Service.setQrCodeCreate(realPath, vo);
	}
	
	// QR Code 개인정보 QR 코드로 생성하기 폼보기
	@GetMapping("/qrCode/qrCodeEx1")
	public String qrCodeEx1Get() {
		return "study2/qrCode/qrCodeEx1";
	}
	
	// QR Code 개인정보 QR 코드 생성
	@ResponseBody
	@PostMapping("/qrCode/qrCodeEx1")
	public String qrCodeEx1Post(HttpServletRequest request, QrCodeVO vo) {
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/qrCode/");
		vo.setFlag(1);
		return study2Service.setQrCodeCreate(realPath, vo);
	}
	
	// QR Code 소개사이트 주소 생성하기 폼보기
	@GetMapping("/qrCode/qrCodeEx2")
	public String qrCodeEx2Get() {
		return "study2/qrCode/qrCodeEx2";
	}
	
	// QR Code 소개사이트 주소 생성하기
	@ResponseBody
	@PostMapping("/qrCode/qrCodeEx2")
	public String qrCodeEx2Post(HttpServletRequest request, QrCodeVO vo, HttpSession session) {
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/qrCode/");
		vo.setFlag(2);
		vo.setMid((String) session.getAttribute("sMid"));
		return study2Service.setQrCodeCreate(realPath, vo);
	}
	
	// QR Code 티켓예매 폼보기
	@GetMapping("/qrCode/qrCodeEx3")
	public String qrCodeEx3Get() {
		return "study2/qrCode/qrCodeEx3";
	}
	// QR Code 티켓예매 생성하기
	@ResponseBody
	@PostMapping("/qrCode/qrCodeEx3")
	public String qrCodeEx3Post(HttpServletRequest request, QrCodeVO vo) {
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/qrCode/");
		vo.setFlag(3);
		return study2Service.setQrCodeCreate(realPath, vo);
	}
	// QR Code 티켓예매 폼보기(DB저장 검색)
	@GetMapping("/qrCode/qrCodeEx4")
	public String qrCodeEx4Get() {
		return "study2/qrCode/qrCodeEx4";
	}
	
	// QR Code 티켓예매 생성하기(DB저장 검색)
	@ResponseBody
	@PostMapping(value = "/qrCode/qrCodeEx4")
	public String qrCodeEx4Post(HttpServletRequest request, QrCodeVO vo) {
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/qrCode/");
		vo.setFlag(4);
		return study2Service.setQrCodeCreate(realPath, vo);
	}
	
	// QR Code명 검색하기(DB저장 검색)
	@ResponseBody
	@RequestMapping(value = "/qrCode/qrCodeSearch", method = RequestMethod.POST)
	public QrCodeVO qrCodeSearchPost(String qrCode) {
		return study2Service.getQrCodeSearch(qrCode);
	}
	
	// 썸네일 연습 폼보기
	@RequestMapping(value = "/thumbnail/thumbnailForm", method = RequestMethod.GET)
	public String thumbnailFormGet() {
		return "study2/thumbnail/thumbnailForm";
	}
	
	// 썸네일 연습 사진처리
	@ResponseBody
	@PostMapping("/thumbnail/thumbnailForm")
	public String thumbnailFormPost(MultipartFile file, HttpSession session, HttpServletRequest request) {
		String mid = (String) session.getAttribute("sMid");
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/thumbnail/");
		return study2Service.setThumbnailCreate(file, mid, realPath);
	}
	
	// 썸네일 전체 리스트 이미지 보기
	@RequestMapping(value = "/thumbnail/thumbnailResult", method = RequestMethod.GET)
	public String thumbnailResultGet(Model model, HttpServletRequest request) {
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/thumbnail/");
		String[] files = new File(realPath).list();
		
		model.addAttribute("files", files);
		model.addAttribute("fileCount", (files.length / 2));
		
		return "study2/thumbnail/thumbnailResult";
	}
	
	// 썸네일 이미지 삭제처리(1개파일삭제)
	@ResponseBody
	@RequestMapping(value = "/thumbnail/thumbnailDelete", method = RequestMethod.POST)
	public int thumbDeletePost(HttpServletRequest request, String file) {
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/thumbnail/");
		
		int res = 0;
		File sName = new File(realPath + file);
		File fName = new File(realPath + file.substring(2));
		if(fName.exists()) {
			sName.delete();
			fName.delete();
			res = 1;
		}
		return res;
	}
	
  // 썸네일 이미지 삭제처리(전체파일삭제)
	@ResponseBody
	@RequestMapping(value = "/thumbnail/thumbnailDeleteAll", method = RequestMethod.POST)
	public int thumbnailDeleteAllPost(HttpServletRequest request, String file) {
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/thumbnail/");
		
		int res = 0;
		File targetFolder = new File(realPath);
		if(!targetFolder.exists()) return res;
		
		File[] files = targetFolder.listFiles();
		
		if(files.length != 0) {
			for(File f : files) {
				if(!f.isDirectory()) f.delete();
			}
			res = 1;
		}
		return res;
	}
}