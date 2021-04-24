package net.admins.controller;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import net.common.common.CommandMap;
import net.admins.service.BoardNoticeAdminsService;
import net.mwav.common.module.Common_Utils;
import net.mwav.common.module.Paging;
import net.mwav.common.module.PagingVO;

@Controller
public class BoardNoticeAdminsController {
	Logger log = Logger.getLogger(this.getClass());
	// HttpServletRequest request = null;
	// 자바에서 세션사용을 위해서는 아래와 같이 필요
	// 세션 관련 설정은 prehandle 에서 추후 지정(들어오는 url에 따라서)
	// HttpSession session = request.getSession();

	Common_Utils cou = new Common_Utils();

	String mode;
	String depth_1 = "Admins";
	String depth_2 = "NewsMr";
	String depth_3 = "";

	// - Controller > Service > ServiceImpl > DAO > SQL(XML) > Controller > JSP

	// 페이지별 세션지정 -> GET문
	/*
	 * 1. bnsForm : mode = SbnsForm /CommonApps/BoardNews/bnsForm.jsp 2. bnsList
	 * : mode = SbnsList /CommonApps/BoardNews/bnsList.jsp 3. bnsView : mode =
	 * SbnsView /CommonApps/BoardNews/bnsView.jsp 4. FrontNewsList : mode =
	 * SFbnsList /CommonApps/BoardNews/FrontNewsList.jsp 5. bnsUpdate : mode =
	 * SbnsUpdate /CommonApps/BoardNews/bnsForm.jsp
	 */
	@Resource(name = "boardNoticeAdminsService")
	private BoardNoticeAdminsService BoardNoticeAdminsService;

	// ///////////////////////////////////BoardNews/////////////////////////////////////

	/*
	 * ========================================등록================================
	 * ========
	 */
	// 1번 bnsForm : Form 입력만 가능 (뒤로가기, list)
	@RequestMapping(value = "/admin/boardNotice/ntmForm.mwav")
	// http://egloos.zum.com/nadostar/v/210497
	public ModelAndView insertNtmForm(CommandMap commandMap,
			HttpServletRequest request) throws Exception {

		ModelAndView mv = new ModelAndView("/Admins/SiteMgr/NoticeMgr/NtmForm");

		log.debug("인터셉터 테스트");
		BoardNoticeAdminsService.insertNtmForm(commandMap.getMap());

		String mm = "site";
		mv.addObject("mm", mm);
		mv.addObject("mode", "m_ntmForm");

		mv.addObject("depth_1", depth_1);
		mv.addObject("depth_2", depth_2);
		mv.addObject("depth_3", "NtmForm");

		// mv.addObject("insertBnsForm", insertBnsForm);
		// mv.addObject("IDX", commandMap.get("IDX"));

		return mv;
	}

	// 1번 bnsView : 수정/삭제가능
	@RequestMapping(value = "/admin/boardNotice/ntmView.mwav")
	public ModelAndView selectOneNtmView(CommandMap commandMap,
			HttpServletRequest request, HttpSession session) throws Exception {
		ModelAndView mv = new ModelAndView("/Admins/SiteMgr/NoticeMgr/NtmView");

		log.debug("인터셉터 테스트");
		System.out.println("테스트");
		Map<String, Object> selectOneNtmView = BoardNoticeAdminsService
				.selectOneNtmView(commandMap.getMap());

		if (selectOneNtmView != null && !selectOneNtmView.isEmpty()) {
			System.out.println("view 줄랭");

			String mm = "site";
			mv.addObject("mm", mm);
			mv.addObject("mode", "m_ntmView");

			mv.addObject("depth_1", depth_1);
			mv.addObject("depth_2", depth_2);
			mv.addObject("depth_3", "NtmView");

			mv.addObject("selectOneNtmView", selectOneNtmView);
		}

		return mv;
	}

	/*
	 * ========================================수정================================
	 * ========
	 */

	// 1번 bnsUpdate : 리스트 업데이트
	@RequestMapping(value = "/admin/boardNotice/ntmUpdate.mwav")
	public ModelAndView updateNtmForm(CommandMap commandMap,
			HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView("/Admins/SiteMgr/NoticeMgr/NtmForm");

		// 위의 view랑 동일하게 사용

		Map<String, Object> updateNtmForm = BoardNoticeAdminsService
				.updateNtmForm(commandMap.getMap());

		if (updateNtmForm != null && !updateNtmForm.isEmpty()) {
			System.out.println("view 줄랭");
			String mm = "site";
			mv.addObject("mm", mm);
			mv.addObject("mode", "m_nsmUpdate");

			mv.addObject("updateNtmForm", updateNtmForm);
		}
		return mv;
	}

	// 1번 bnsUpdate : 리스트 업데이트
	@RequestMapping(value = "/admin/boardNotice/ntmUpdatePro.mwav")
	public ModelAndView updateProNsmForm(CommandMap commandMap,
			HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView("/Admins/SiteMgr/NoticeMgr/NtmList");

		// 위의 view랑 동일하게 사용

		BoardNoticeAdminsService.updateProNtmForm(commandMap.getMap());
		
		String mm = "site";
		mv.addObject("mm", mm);
		mv.addObject("mode", "m_ntmList");
		
		return mv;
	}

	/*
	 * // 7번 bnsUpdatePro : 리스트 업데이트
	 * 
	 * @RequestMapping(value = "/board/bnsUpdatePro.mwav") public ModelAndView
	 * updatebnsform(CommandMap commandMap) throws Exception { ModelAndView mv =
	 * new ModelAndView("/Company/CompanyMasterPage_1");
	 * 
	 * boardService.updatebnsform(commandMap.getMap()); return mv; }
	 */

	/*
	 * ========================================리스트(SelectOne, SelectList
	 * 순)========================================
	 */
	// 1번 FrontNewsList : 메인페이지 앞단 5개씩 출력, 작성일 기준
	@RequestMapping(value = "/admin/boardNotice/ntmFrontList.mwav")
	public ModelAndView selectListNtmFrontList(CommandMap commandMap,
			HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView(
				"/Admins/SiteMgr/NoticeMgr/NtmFrontList");
		/*
		 * mode = "SFbnsList"; session.setAttribute("mode", mode); String
		 * sessiontest = (String) session.getAttribute(mode);
		 * 
		 * System.out.println("sessiontest=" + sessiontest);
		 */

		// * action-servlet.xml에서 위에 .jsp 설정해줘서 위의 CommonApps 부터 되는거
		cou.selectCommandMapList(commandMap); // 키 출력

		List<Map<String, Object>> selectListNtmFrontList = BoardNoticeAdminsService
				.selectListNtmFrontList(commandMap.getMap());

		if (selectListNtmFrontList != null && !selectListNtmFrontList.isEmpty()) {

			mode = "m_ntmFrontList";
			request.setAttribute("mode", mode);

			mv.addObject("selectListNtmFrontList", selectListNtmFrontList);
		}

		return mv;
	}

	// 2번 bnsList : 리스트
	@RequestMapping(value = "/admin/boardNotice/ntmList.mwav")
	public ModelAndView selectListNtmList(CommandMap commandMap,
			HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView("/Admins/SiteMgr/NoticeMgr/NtmList");

		String pageNum = (String) commandMap.get("pageNum");
		Paging paging = new Paging();
		if (pageNum == null || pageNum == "") {
			pageNum = "1";
		}
		int totalRow = BoardNoticeAdminsService.selectOneGetNtmTotalCount();
		System.out.println("totalRow=" + totalRow);

		// Paging pv = new Paging(pageNum, 10 , 10, totalCount);
		List<Map<String, Object>> selectListNtmList;
		PagingVO pagingVO = paging.setPagingInfo(totalRow, 5, pageNum); // 총 숫자,
																		// 한페이지에
																		// 노출 수
		commandMap.put("startRow", paging.getStartRow(pageNum)); // 시작 열
		commandMap.put("endRow", paging.getEndRow(pageNum)); // 끝 열
		if (totalRow > 0) {
			selectListNtmList = BoardNoticeAdminsService
					.selectListNtmList(commandMap.getMap());
			// selectboardList =
			// boardService.selectbnsList(commandMap.getMap());

		} else {
			selectListNtmList = Collections.emptyList();
		}
		System.out.println("찍히낭");
		String mm = "site";
		mv.addObject("mm", mm);
		mv.addObject("mode", "m_ntmList");

		mv.addObject("depth_1", depth_1);
		mv.addObject("depth_2", depth_2);
		mv.addObject("depth_3", "NtmList");

		mv.addObject("selectListNtmList", selectListNtmList);
		mv.addObject("pagingVO", pagingVO);
		mv.addObject("totalRow", totalRow);
		// mv.addObject("paging", pv.print());
		return mv;
	}

	
	
	/*
	 * ========================================삭제================================
	 * ========
	 */
	// 1번 bnsDelete
	@RequestMapping(value = "/admin/boardNotice/ntmDelete.mwav")
	public ModelAndView deleteNtmDelete(CommandMap commandMap,
			HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView(
				"/Admins/SiteMgr/NoticeMgr/NtmList");

		BoardNoticeAdminsService.deleteNtmDelete(commandMap.getMap());

		mv.addObject("depth_1", depth_1);
		mv.addObject("depth_2", depth_2);
		mv.addObject("depth_3", "NtmDelete");

		return mv;
	}

	
	
	/*
	 * =============================Promoter 공지사항 기능===============================
	 */
	
	// (1) Promoter 공지사항 리스트
	@RequestMapping(value = "/admins/promoter/boardmgr/PmtNoticeList.mwav")
	public ModelAndView selectListPmtNtmList(CommandMap commandMap,
			HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView("/Admins/Promoter/BoardMgr/PmtNoticeList");

		String pageNum = (String) commandMap.get("pageNum");
		Paging paging = new Paging();
		if (pageNum == null || pageNum == "") {
			pageNum = "1";
		}
		int totalRow = BoardNoticeAdminsService.selectOneGetPmtNtmTotalCount();
		System.out.println("totalRow=" + totalRow);

		List<Map<String, Object>> selectListPmtNtmList;
		PagingVO pagingVO = paging.setPagingInfo(totalRow, 5, pageNum); // 총 숫자,
																		// 한페이지에
																		// 노출 수
		commandMap.put("startRow", paging.getStartRow(pageNum)); // 시작 열
		commandMap.put("endRow", paging.getEndRow(pageNum)); // 끝 열
		
		if (totalRow > 0) {
			selectListPmtNtmList = BoardNoticeAdminsService.selectListPmtNtmList(commandMap.getMap());
		} else {
			selectListPmtNtmList = Collections.emptyList();
		}
		
		
		String mm = "site";
		mv.addObject("mm", mm);

		mv.addObject("selectListPmtNtmList", selectListPmtNtmList);
		mv.addObject("pagingVO", pagingVO);
		mv.addObject("totalRow", totalRow);

		return mv;
	}

	
	// (2) Promoter 공지사항 삭제
	@RequestMapping(value = "/admins/promoter/boardmgr/PmtNoticeDelete.mwav")
	public ModelAndView deletePmtNtmDelete(CommandMap commandMap,
			HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView("/Admins/Promoter/BoardMgr/PmtNoticeList");
		
		BoardNoticeAdminsService.deletePmtNtmDelete(commandMap.getMap());
		
		return mv;
	}	
	
	// Promoter 공지사항 등록
	@RequestMapping(value = "/admins/promoter/boardmgr/PmtNoticeForm.mwav")
	public ModelAndView insertPmtNtmForm(CommandMap commandMap,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		ModelAndView mv = new ModelAndView("/Admins/Promoter/BoardMgr/PmtNoticeForm");

		BoardNoticeAdminsService.insertPmtNtmForm(commandMap.getMap());
		
		
		String mm = "site";
		mv.addObject("mm", mm);


		// ip address 값  받아서 전달 (error : 전달 안됨. 추후 기능개발 필요. 우선 텍스트값을 insert함.)
		//String bpIpAddress = request.getHeader("X-Forwarded-For");
		//if (bpIpAddress == null) ip = request.getRemoteAddr();
		//String bpIpAddress = "hihi";
		//mv.addObject("bpIpAddress", bpIpAddress);
		
		response.sendRedirect("/admins/promoter/boardmgr/PmtNoticeList.mwav?pageNum=1"); // 공지사항 등록 후 리스트 화면으로 이동
		
		return mv;
	}

	// Promoter 공지사항 수정
	@RequestMapping(value = "/admins/promoter/boardmgr/PmtNoticeUpdatePro.mwav")
	public ModelAndView updateProPmtNtmForm(CommandMap commandMap,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView("/Admins/Promoter/BoardMgr/PmtNoticeList");

		BoardNoticeAdminsService.updateProPmtNtmForm(commandMap.getMap());
		
		
		String mm = "site";
		mv.addObject("mm", mm);
		
		response.sendRedirect("/admins/promoter/boardmgr/PmtNoticeList.mwav?pageNum=1"); // 공지사항 수정 후 리스트 화면으로 이동
		
		return mv;
	}	

	// Promoter 공지사항 수정
	@RequestMapping(value = "/admins/promoter/boardmgr/PmtNoticeUpdate.mwav")
	public ModelAndView updatePmtNtmForm(CommandMap commandMap,
			HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView("/Admins/Promoter/BoardMgr/PmtNoticeForm");


		Map<String, Object> updatePmtNtmForm = BoardNoticeAdminsService.updatePmtNtmForm(commandMap.getMap());

		if (updatePmtNtmForm != null && !updatePmtNtmForm.isEmpty()) {
			
			String mm = "site";
			mv.addObject("mm", mm);

			mv.addObject("updatePmtNtmForm", updatePmtNtmForm);
		}
		
		
		return mv;
	}
	
	// Promoter 공지사항 View
	@RequestMapping(value = "/admins/promoter/boardmgr/PmtNoticeView.mwav")
	public ModelAndView selectOnePmtNtmView(CommandMap commandMap,
			HttpServletRequest request, HttpSession session) throws Exception {
		ModelAndView mv = new ModelAndView("/Admins/Promoter/BoardMgr/PmtNoticeView");

		Map<String, Object> selectOnePmtNtmView = BoardNoticeAdminsService
				.selectOnePmtNtmView(commandMap.getMap());

		if (selectOnePmtNtmView != null && !selectOnePmtNtmView.isEmpty()) {
			
			String mm = "site";
			mv.addObject("mm", mm);
			
			mv.addObject("selectOnePmtNtmView", selectOnePmtNtmView);
		}

		return mv;
	}

	// Promoter 공지사항 노출/비노출 설정
	@RequestMapping(value = "/admins/promoter/boardmgr/PmtNoticeStatusUpdate.mwav")
	public ModelAndView updateNsmBnStatusNotice(CommandMap commandMap,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView("/Admins/Promoter/BoardMgr/PmtNoticeList");

		BoardNoticeAdminsService.updatePmtNoticeStatus(commandMap.getMap());

		String bpStatus = request.getParameter("bpStatus"); //웹에서 전달받은 값
		String boardPromoter_id = request.getParameter("boardPromoter_id"); //웹에서 전달받은 값
		
		
		String mm = "site";
		mv.addObject("mm", mm);
		
		
		mv.addObject("boardPromoter_id", boardPromoter_id);
		mv.addObject("bpStatus", bpStatus);

		response.sendRedirect("/admins/promoter/boardmgr/PmtNoticeList.mwav?pageNum=1"); // 공지사항 노출,비노출 설정 후 리스트 화면으로 이동
		
		return mv;
	}	

}