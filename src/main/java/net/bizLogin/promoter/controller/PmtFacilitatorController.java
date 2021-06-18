package net.bizLogin.promoter.controller;

import net.bizLogin.promoter.service.PmtFacilitatorService;
import net.bizLogin.promoter.vo.BizPromoter_VO;
import net.bizLogin.promoter.vo.PmtFacilitatorSO;
import net.bizLogin.promoter.vo.PmtFacilitatorVO;
import net.common.common.CommandMap;
import net.common.common.Status;
import net.mwav.common.module.Common_Utils;
import net.mwav.common.module.ValidationLib;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;


/**
 *  해당 클래스에 대한 설명을 기입 <p>
 *  줄 구분을 위해서는 <p> 태그 사용
 * <pre>
 * {@code
 * // 예제코드 작성
 * 
 * }
 * </pre>
 * @author 저자1
 * @author 저자2
 * @since 작성 버전
 * @version 현재 버전
 * @see 참고해야할 package 혹은 method
 * @see java.util.Arrays#copyOf(byte[], int)
 * @see #method(String)
 */
@Controller
public class PmtFacilitatorController {
	Logger log = Logger.getLogger(this.getClass());


	String mode;
	@Resource(name = "pmtFacilitatorService")
	private PmtFacilitatorService pmtFacilitatorService;
	
	Common_Utils cu = new Common_Utils();

	/**
	 * <pre>
	 *     	프로모터 로그인 핸들러
	 * </pre>
	 * @param CommandMap
	 * @param param2
	 * @return return 값에 대한 설명(필수)
	 * @throws Exception 발생하는 예외에 대한 설명(필수)
	 * @see
	 * @since 21st/May/2021
	 * @version v1.0.0
	 */
	@RequestMapping(value = "/bizLogin/promoter/facilitator/pmtFacilitatorLogin.mwav",method = RequestMethod.POST)
	public ModelAndView selectBizPmtLogin(CommandMap commandMap, HttpServletRequest request, RedirectAttributes rtr) throws Exception {
		ModelAndView mv = new ModelAndView();
		boolean chkEmailYN = false;

		// Promoter 로그인 성공시 값을 가져옴
		BizPromoter_VO bizPromoterVo = pmtFacilitatorService.selectBizPmtLogin(commandMap.getMap());

		// 로그인 성공여부에 따라 페이지및 statusCode, msg 변경
		if(bizPromoterVo == null){
			log.info("프로모터 로그인 실패");
			mv.setViewName("redirect:/Promoter/Facilitator/PmtLogin.mwav");
			rtr.addFlashAttribute("msg", "비밀번호와 아이디를 확인해주세요");
		}else {

			// 로그인한 사용자가 이메일을 인증했는지 검증
			chkEmailYN = bizPromoterVo.getPmtCertifyDt() != null ? true : false;

			// 이메일 인증된 사용자의 경우
			if(chkEmailYN){
				log.info("프로모터 로그인 성공");
				mv.setViewName("redirect:/Promoter/Index");
				request.getSession().setAttribute("bizPromoter", bizPromoterVo);
			}else{
				// 이메일 인증이 안되어있는 사용자라면 인증 페이지로 redirect
				log.info("이메일 인증되지 않은 사용자 로그인");
				mv.setViewName("redirect:/Promoter/Facilitator/PmtCertifyPage.mwav");
				rtr.addFlashAttribute("msg", "이메일 인증이 필요합니다.");
				rtr.addFlashAttribute("promoter_id", bizPromoterVo.getPromoter_id());
				rtr.addFlashAttribute("pmtMail", bizPromoterVo.getPmtMail());

			}

		}
		return mv;
	}

	/**
	 * <pre>
	 *     	프로모터 이메일 인증
	 * </pre>
	 * @param initEmail 회원가입시 사용된 이메일
	 * @param changeEmail 변경할 이메일
	 * @param promoter_id 프로모터 PK
	 * @return ModelAndView
	 * @throws Exception
	 * @see
	 * @since 21st/May/2021
	 * @version v1.0.0
	 */
	@RequestMapping(value = "/bizLogin/promoter/facilitator/certifyEmail", method = RequestMethod.POST)
	public ModelAndView certifyEmail(@RequestParam(required = true) String initEmail
								   , @RequestParam(required = true) String changeEmail
								   , @RequestParam(required = true) String promoter_id
								   , HttpServletRequest request
								   , RedirectAttributes rtr) throws IOException {
		ModelAndView mv = new ModelAndView();

		// 초기 이메일과 변경된 이메일이 다르면, 변경된 이메일 DB 값을 수정한다.
		if(initEmail.equalsIgnoreCase(changeEmail)){
			// UPDATE PROMOTER EMAIL
			pmtFacilitatorService.updatePmtEmail(changeEmail, promoter_id);
		}

		// 입력된 이메일로 인증 메일을 발송한다.
		String serverUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
		boolean isOk = pmtFacilitatorService.sendCertifyMail(serverUrl, changeEmail, promoter_id);

		// 성공적으로 메일을 발송했다면 msg로 확인 하라고 알림
		if(isOk){
			mv.setViewName("redirect:/Promoter/Facilitator/PmtLogin.mwav");
			rtr.addAttribute("msg", "인증 메일을 발송하였습니다.\\n인증완료 후 다시 로그인해주세요.");
		}else{
			mv.setViewName("redirect:/Promoter/Facilitator/PmtCertifyPage.mwav");
			rtr.addAttribute("msg", "메일 발송 오류입니다.\\n다시 시도해주세요.");
		}

		return mv;
	}

	/**
	 * 메서드에 대한 설명
	 * <pre>
	 * </pre>
	 * @param CommandMap parameter에 대한 설명(필수)
	 * @param param2
	 * @return return 값에 대한 설명(필수)
	 * @throws Exception 발생하는 예외에 대한 설명(필수)
	 * @see 해당 메서드와 연관된 메서드
	 * @since 21st/Oct/2020
	 * @version v1.0.0
	 */
	@RequestMapping(value = "/bizLogin/promoter/facilitator/pmtForm.mwav", method = RequestMethod.POST)
	public ModelAndView pmtFacilitatorInsert(CommandMap commandMap, HttpServletRequest request) throws Exception {
		// 1. MaV의 View를 정의하여 성공시 로그인 페이지로 리다이렉트
		ModelAndView mv = new ModelAndView("redirect:/Promoter/Facilitator/PmtLogin.mwav");

		// 2. PromoterValueLog_tbl 로그를 위해 최초 IP를 map에 등록
		commandMap.put("pvlIpAddress", request.getRemoteAddr());

		// 3. 비즈니스 로직 실행
		final Map<String, Object> pmtFormResult = pmtFacilitatorService.insertPmtForm(commandMap);

		// 4. INSERT 한 promoter_id 를 가져옴
		final String pmtLoginId = (String) commandMap.getMap().get("pmtLoginId");
		String promoter_id = pmtFacilitatorService.selectOnePmtId(pmtLoginId);

		// 5. Model에 Attribute 등록
		// TODO : 등록 후 mode, mm 쿼리스트링 수정필요
		mv.addAllObjects(pmtFormResult);
		mv.addObject("mm", "firms");
		mv.addObject("mode", "m_stfForm");

		// 6. 이메일 발송
		String serverUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
		boolean isOk = pmtFacilitatorService.sendCertifyMail(serverUrl, (String)commandMap.get("pmtMail"), promoter_id);

		return mv;
	}

	/*
        return
            true : 중복된 아이디가 없음으로 가입이 가능함
            false: 이미 존재하는 아이디가 있으므로 가입이 불가능함
     */
	@RequestMapping(value="/bizLogin/promoter/facilitator/pmtLoginIdCheck.mwav")
	public @ResponseBody
	boolean selectOneMbrPmtIdCheck(String pmtLoginId) throws Exception {
		return pmtFacilitatorService.selectOnePmtLoginIdCheck(pmtLoginId);
	}
	
	@RequestMapping(value = "/Promoter/Index.mwav")
	public String index(CommandMap commandMap) throws Exception {
		return "/Promoter/Index";
	}
	
	@RequestMapping(value = "/promoter/kakaoLogin.mwav", method = RequestMethod.POST)
	public @ResponseBody String promoterKakaoLogin(@RequestBody PmtFacilitatorSO so, HttpServletRequest request) throws Exception {
		HttpSession session = request.getSession();
		so.setSpIpAddress(cu.getClientIP(request));
		PmtFacilitatorVO pmtFacilitatorVO = pmtFacilitatorService.joinSocialLogin(so);
		session.setAttribute("promoter", pmtFacilitatorVO);
		return pmtFacilitatorVO.getSpPromoterId();
	}
	
	@RequestMapping(value = "/promoter/kakaoLogout.mwav")
	@ResponseBody
	public Status promoterKakaoLogout(HttpServletRequest request, HttpSession session, HttpServletResponse response) throws Exception {
		Object obj = session.getAttribute("promoter");
		
		if (obj != null) {
			session.removeAttribute("promoter");
			session.invalidate();
			log.info("promoter session remove");
		} else {
			log.info("세션에 프로모터 로그인 정보가 없어 로그아웃하지 못하였습니다");
		}
		return Status.OK;
	}
}
