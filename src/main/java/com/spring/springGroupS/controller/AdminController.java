package com.spring.springGroupS.controller;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.springGroupS.common.Pagenation;
import com.spring.springGroupS.service.AdminService;
import com.spring.springGroupS.service.MemberService;
import com.spring.springGroupS.vo.ComplaintVO;
import com.spring.springGroupS.vo.MemberVO;
import com.spring.springGroupS.vo.PageVO;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	AdminService adminService;
	
	@Autowired
	MemberService memberService;
	
	@Autowired
	Pagenation pagenation;
	
		
	@GetMapping("/adminMain")
	public String adminMainGet() {
		return "admin/adminMain";
	}
	
	@GetMapping("/adminLeft")
	public String adminLeftGet() {
		return "admin/adminLeft";
	}
	
	@GetMapping("/adminContent")
	public String adminContentGet() {
		return "admin/adminContent";
	}
	
	@GetMapping("/member/adMemberList")
	public String adMemberListGet(Model model, PageVO pageVO) {
		// 페이징 처리하기
		pageVO.setSection("member");
		
		System.out.println("pageVO.Level" + pageVO.getLevel());
		System.out.println("pageVO.Level" + pageVO.getPageSize());
		System.out.println("pageVO.Level" + pageVO.getPag());
		pageVO = pagenation.pagenation(pageVO);
		List<MemberVO> vos = memberService.getMemberList(pageVO.getPag(), pageVO.getPageSize(), pageVO.getLevel());
		
		// 페이징처리 변수 넘겨주기
		System.out.println("vos : " + vos);
		model.addAttribute("vos", vos);
		model.addAttribute("pageVO", pageVO);
		return "admin/member/adMemberList";
	}
	
	//회원 등급 변경 처리
	@ResponseBody
	@PostMapping("/member/memberLevelChange")
	public int memberLevelChangePost(int level, int idx) {
		return adminService.setMemberLevelChange(level, idx);
	}
	// 선택한 회원들 등급 변경 처리
	@ResponseBody
	@PostMapping("/member/memberLevelSelectChange")
	public int memberLevelSelectChangePost(String idxSelectArray, int levelSelect) {
		return adminService.setMemberLevelSelectChange(idxSelectArray, levelSelect);
	}
	
	// 신고리스트 폼보기
	@GetMapping("/complaint/complaintList")
	public String complaintListGet(Model model, PageVO pageVO) {
		pageVO.setSection("complaint");
		pageVO = pagenation.pagenation(pageVO);
		List<ComplaintVO> vos = adminService.getComplaintList(pageVO.getStartIndexNo(), pageVO.getPageSize());
		model.addAttribute("vos", vos);
		return "admin/complaint/complaintList";
	}
	
	// 신고리스트 상세보기
	@GetMapping("/complaint/complaintContent")
	public String complaintContentGet(Model model, int partIdx) {
		System.out.println(partIdx);
		ComplaintVO vo = adminService.getComplaintSearch(partIdx);
		model.addAttribute("vo", vo);
		return "admin/complaint/complaintContent";
	}
	
	// 신고내역자료 '취소/감추기/삭제'
	@ResponseBody
	@PostMapping("/complaint/complaintProcess")
	public int complaintProcessPost(ComplaintVO vo) {
		int res = 0;
		
		if(vo.getComplaintSw().equals("D")) {
			res = adminService.setComplaintDelete(vo.getPartIdx(), vo.getPart());
			vo.setComplaintSw("처리완료(D)");
			
		}
		else {
			if(vo.getComplaintSw().equals("H")) {
				res = adminService.setComplaintProcess(vo.getPartIdx(), "HI");
				vo.setComplaintSw("처리중(H)");
			}
			else {
				res =adminService.setComplaintProcess(vo.getPartIdx(), "NO");
				 vo.setComplaintSw("처리중(S)");
			}
		}
		if(res != 0) adminService.setComplaintProcessOk(vo.getIdx(), vo.getComplaintSw());
		
		return res;
	}
	
	@GetMapping("/etc/fileManagement")
	public String fileManagementGet(Model model, HttpServletRequest request,
			@RequestParam(name="part", defaultValue = "fileUpload", required = false) String part
			) {
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/"+part+"/");
		System.out.println("realPath : " + realPath);
		String[] files = new File(realPath).list();
		model.addAttribute("part", part);
		model.addAttribute("files", files);
		model.addAttribute("fileCount", files.length);
		return "admin/etc/fileManagement";
	}
}
