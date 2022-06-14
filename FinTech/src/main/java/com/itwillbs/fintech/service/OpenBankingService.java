package com.itwillbs.fintech.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itwillbs.fintech.vo.AccountSearchRequestVO;
import com.itwillbs.fintech.vo.AccountSearchResponseVO;
import com.itwillbs.fintech.vo.RequestTokenVO;
import com.itwillbs.fintech.vo.ResponseTokenVO;
import com.itwillbs.fintech.vo.UserInfoRequestVO;
import com.itwillbs.fintech.vo.UserInfoResponseVO;

@Service
public class OpenBankingService { // OpenBankingController - OpenBankingApiClient 객체 중간자 역할
	// OpenBankingApiClient 객체 자동 주입
	@Autowired
	private OpenBankingApiClient openBankingApiClient;

	// 엑세스토큰 발급 요청을 위한 requestToken() 메서드 호출
	public ResponseTokenVO requestToken(RequestTokenVO requestToken) {
		return openBankingApiClient.requestToken(requestToken);
	}

	// 사용자 정보 조회
	public UserInfoResponseVO findUser(UserInfoRequestVO userInfoRequestVO) {
		return openBankingApiClient.findUser(userInfoRequestVO);
	}

	public AccountSearchResponseVO findAccount(AccountSearchRequestVO accountSearchRequestVO) {
		return openBankingApiClient.findAccount(accountSearchRequestVO);
	}
}












