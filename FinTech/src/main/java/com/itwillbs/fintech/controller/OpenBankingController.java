package com.itwillbs.fintech.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.itwillbs.fintech.service.OpenBankingService;
import com.itwillbs.fintech.vo.RequestTokenVO;
import com.itwillbs.fintech.vo.ResponseTokenVO;
import com.itwillbs.fintech.vo.UserInfoRequestVO;
import com.itwillbs.fintech.vo.UserInfoResponseVO;
import com.itwillbs.fintech.vo.AccountSearchRequestVO;
import com.itwillbs.fintech.vo.AccountSearchResponseVO;

@Controller
public class OpenBankingController {
	private String clientId = "4066d795-aa6e-4720-9383-931d1f60d1a9";
	private String clientSecret = "36b4a668-94ba-426d-a291-771405e498e4";
	
	// OpenBankingService 객체 자동 주입
	@Autowired
	private OpenBankingService openBankingService;
	
	@RequestMapping(value = "/callback", method = RequestMethod.GET)
	public String getToken(@ModelAttribute RequestTokenVO requestToken, Model model) {
		// OAuth 인증 완료 후 전송되는 인증코드(code)를 자동으로 RequestTokenVO 객체에 저장
		System.out.println("인증코드 : " + requestToken.getCode());
		
		// 응답데이터로 전달받은 인증코드를 사용하여 엑세스토큰 발급 받기
		// OpenBankingService 객체의 requestToken() 메서드를 호출하여 엑세스토큰 발급 요청
		// => 파라미터 : RequestTokenVO 객체, 리턴타입 : ResponseTokenVO 객체(responseToken)
		ResponseTokenVO responseToken = openBankingService.requestToken(requestToken);
		
		responseToken.setAccess_token("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiIxMTAxMDAyMjkwIiwic2NvcGUiOlsiaW5xdWlyeSIsImxvZ2luIiwidHJhbnNmZXIiXSwiaXNzIjoiaHR0cHM6Ly93d3cub3BlbmJhbmtpbmcub3Iua3IiLCJleHAiOjE2NjI5NjI1NTYsImp0aSI6IjI2YTFiM2Q4LTRhMDQtNDM4Ny05OTVhLWVhMGQ4OGMwYTJhYyJ9.yeOMnT3cyDjIfMWZn-3RYlDxZuM_JdWRfzZsrqH7sqE");
		responseToken.setUser_seq_no("1101002290");
		
		// bank_main.jsp 페이지로 포워딩
		// => 이 때, 전달받은 토큰 정보(ResponseTokenVO 객체)를 함께 전달
		model.addAttribute("responseToken", responseToken);
		return "bank_main";
	}
	
	// 사용자 정보 조회
	@RequestMapping(value = "/userInfo", method = RequestMethod.GET)
	public String getUserInfo(@ModelAttribute UserInfoRequestVO userInfoRequestVO, Model model) {
		// Service 객체의 findUser() 메서드를 호출하여 사용자 정보 조회
		// => 파라미터 : UserInfoRequestVO, 리턴타입 UserInfoResponseVO
		UserInfoResponseVO userInfo = openBankingService.findUser(userInfoRequestVO);
		
		// Model 객체에 UserInfoResponseVO 객체와 엑세스토큰 저장
		model.addAttribute("userInfo", userInfo);
		model.addAttribute("access_token", userInfoRequestVO.getAccess_token());
		
		return "account/user_info";
	}
	
	// 등록계좌 조회
	@RequestMapping(value = "/accountList", method = RequestMethod.GET)
	public String getAccountList(@ModelAttribute AccountSearchRequestVO accountSearchRequestVO, Model model) {
		// Service 객체의 findAccount() 메서드를 호출하여 사용자 정보 조회
		// => 파라미터 : AccountSearchRequestVO, 리턴타입 AccountSearchResponseVO
		AccountSearchResponseVO accountList = openBankingService.findAccount(accountSearchRequestVO);
		
		// Model 객체에 AccountSearchResponseVO 객체와 엑세스토큰 저장
		model.addAttribute("accountList", accountList);
		model.addAttribute("access_token", accountSearchRequestVO.getAccess_token());
		
		return "account/list";
	}
		
	
}










