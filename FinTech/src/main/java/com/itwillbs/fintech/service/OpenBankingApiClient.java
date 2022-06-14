package com.itwillbs.fintech.service;

import java.util.Collections;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.itwillbs.fintech.vo.AccountSearchRequestVO;
import com.itwillbs.fintech.vo.AccountSearchResponseVO;
import com.itwillbs.fintech.vo.RequestTokenVO;
import com.itwillbs.fintech.vo.ResponseTokenVO;
import com.itwillbs.fintech.vo.UserInfoRequestVO;
import com.itwillbs.fintech.vo.UserInfoResponseVO;

@Service
public class OpenBankingApiClient {
	private String clientId = "4066d795-aa6e-4720-9383-931d1f60d1a9";
	private String clientSecret = "36b4a668-94ba-426d-a291-771405e498e4";
	private String redirectUri = "http://localhost:8080/fintech/callback_token";
	private String baseUrl = "https://testapi.openbanking.or.kr/v2.0";
	
	// REST 방식의 API 요청에 사용할 클래스 타입 변수 선언
	private RestTemplate restTemplate; // REST 방식 요청 및 응답에 사용되는 클래스
	private HttpHeaders httpHeaders; // 헤더 정보를 관리할 클래스

	// 헤더에 엑세스 토큰을 추가하는 setHeaderAccessToken() 메서드 정의
	// => 파라미터 : 엑세스토큰, 리턴타입 : HttpHeaders
	public HttpHeaders setHeaderAccessToken(String access_token) {
		// HttpHeaders 객체의 add() 메서드를 호출하여 "항목", "값" 형태로 파라미터 전달
		httpHeaders.add("Authorization", "Bearer " + access_token);
		return httpHeaders;
	}
	
	// 2.1.2. 토큰 발급 API 요청 - Access Token 가져오기
	// 엑세스토큰 발급 요청 작업을 수행할 requestToken() 메서드 정의
	public ResponseTokenVO requestToken(RequestTokenVO requestToken) {
		// REST 방식 요청에 필요한 객체 생성
		restTemplate = new RestTemplate();
		httpHeaders = new HttpHeaders();
		
		/*
		 * 요청 메시지 URL
		   HTTP URL : https://openapi.openbanking.or.kr/oauth/2.0/token
           HTTP Method : POST
           Content-Type : application/x-www-form-urlencoded; charset=UTF-8
		 */
		// 1. HTTP Header 오브젝트(정보) 생성
		httpHeaders.add("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
		// 2. HTTP Body 오브젝트(정보) 생성
		// 2-1. Body 에 추가할 데이터를 관리하는 RequestTokenVO 객체에 필요한 데이터 저장
		// => grant_type 값은 "authorization_code" 값 고정
		requestToken.setRequestToken(clientId, clientSecret, redirectUri, "authorization_code");
		
		// 헤더의 content-type 이 application/x-www-form-urlencoded 이므로 객체 저장이 불가능하며
		// 대신 객체 형태를 파라미터 형태로 변환하여 관리할 MultiValueMap 객체 사용해야함
		MultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
		// MultiValueMap 객체의 add() 메서드를 호출하여 키, 값 형태로 파라미터 데이터 저장
		parameters.add("code", requestToken.getCode());
		parameters.add("client_id", requestToken.getClient_id());
		parameters.add("client_secret", requestToken.getClient_secret());
		parameters.add("redirect_uri", requestToken.getRedirect_uri());
		parameters.add("grant_type", requestToken.getGrant_type());
		
		System.out.println(requestToken.getClient_id());
		System.out.println(requestToken.getClient_secret());
		System.out.println(requestToken.getRedirect_uri());
		
		// HttpHeader 와 HttpBody 오브젝트를 하나의 객체로 관리하기 위한 HttpEntity 객체 생성
		// => 제네릭타입은 파라미터 데이터를 관리하는 MultiValueMap<String, String> 타입 지정
		HttpEntity<MultiValueMap<String, String>> param = 
				new HttpEntity<MultiValueMap<String,String>>(parameters, httpHeaders);

		// 엑세스토큰 요청에 사용될 요청 URL 저장
		String requestUrl = "https://testapi.openbanking.or.kr/oauth/2.0/token";
		
		// RestTemplate 객체의 exchange() 메서드를 호출하여 REST 방식 요청 작업 수행하고
		// 리턴되는 결과값(응답데이터)의 바디 영역 데이터를 리턴
		// => exchange(요청URL, HttpMethod.요청메서드, HttpEntity 객체, 응답받을 객체의 클래스타입).getBody();
		// => 이 때, 리턴되는 데이터 타입은 응답받을 객체의 클래스타입으로 
		//    Body 데이터가 자동으로 저장되어 리턴됨
		return restTemplate.exchange(requestUrl, HttpMethod.POST, param, ResponseTokenVO.class).getBody(); 
	}

	// 사용자 정보 조회
	public UserInfoResponseVO findUser(UserInfoRequestVO userInfoRequestVO) {
		/// REST 방식 요청에 필요한 객체 생성
		restTemplate = new RestTemplate();
		httpHeaders = new HttpHeaders();
		
		// 2.2.1 사용자정보조회 API URL 주소 생성
		String url = baseUrl + "/user/me";
		
		// HttpHeaders 와 HttpBody 오브젝트를 하나의 객체로 관리하기 위한 HttpEntity 객체 생성
		// => 파라미터로 HttpHeaders 객체 전달을 위해 
		//    헤더 생성 작업을 수행하는 사용자 정의 메서드 setHeaderAccessToken() 호출
		//    (파라미터로 엑세스 토큰 전달 => UserInfoRequestVO 객체에 저장되어 있음)
		HttpEntity<String> openBankingUserInfoRequest = 
				new HttpEntity<String>(setHeaderAccessToken(userInfoRequestVO.getAccess_token()));
		
		// UriComponentsBuilder 클래스의 fromHttpUrl() 메서드를 호출하여 URL 파라미터 정보 생성
		// 1단계. UriComponentsBuilder.fromHttpUrl() 메서드를 호출하여 요청 URL 주소 전달
		// 2단계. 1단계에서 생성된 객체의 queryParam() 메서드를 호출하여 전달할 파라미터를
		//        키, 값 형식으로 전달
		// 3단계. 2단계에서 생성된 객체의 build() 메서드를 호출하여 UriComponents 객체 리턴(생성)
		// 위의 세 과정을 빌더 패턴(Builder Pattern)을 활용하여 하나의 문장으로 압축 가능
		// (자기 자신을 리턴하는 메서드 호출 후 연쇄적으로 메서드를 이어나가는 것)
		UriComponents uriBuilder = UriComponentsBuilder.fromHttpUrl(url)
				.queryParam("user_seq_no", userInfoRequestVO.getUser_seq_no())
				.build();
		
		// exchange() 메서드 파라미터 : UriBuilder 문자열로 변환, 요청방식, HttpEntity 객체,
		//                              응답데이터를 파싱하기 위한 클래스(.class 필수)
		// => 메서드 뒤에 .getBody() 메서드를 호출하여 body 데이터에 대한 파싱된 결과를 리턴받기
		return restTemplate.exchange(uriBuilder.toString(), HttpMethod.GET, openBankingUserInfoRequest, UserInfoResponseVO.class).getBody();
	}

	public AccountSearchResponseVO findAccount(AccountSearchRequestVO accountSearchRequestVO) {
		/// REST 방식 요청에 필요한 객체 생성
		restTemplate = new RestTemplate();
		httpHeaders = new HttpHeaders();
		
		// 2.2.1 사용자정보조회 API URL 주소 생성
		String url = baseUrl + "/account/list";

		httpHeaders.add("Authorization", "Bearer " + accountSearchRequestVO.getAccess_token());
		
		HttpEntity<String> openBankingAccountListRequest = new HttpEntity<String>(httpHeaders);
		
		UriComponents uriBuilder = UriComponentsBuilder.fromHttpUrl(url)
				.queryParam("user_seq_no", accountSearchRequestVO.getUser_seq_no())
				.queryParam("include_cancel_yn", accountSearchRequestVO.getInclude_cancel_yn())
				.queryParam("sort_order", accountSearchRequestVO.getSort_order())
				.build();
		
		return restTemplate.exchange(uriBuilder.toString(), HttpMethod.GET, openBankingAccountListRequest, AccountSearchResponseVO.class).getBody();
	}

}










