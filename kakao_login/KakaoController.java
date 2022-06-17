package com.itwillbs.cono.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.itwillbs.cono.service.MainService;
import com.itwillbs.cono.service.Kakao_service;
import com.itwillbs.cono.vo.MemberDTO;

@Controller
public class KakaoController {
	@Autowired
	Kakao_service service;
	
	@RequestMapping(value = "/kakao_callback", method = RequestMethod.GET)
    public String redirectkakao(@RequestParam String code, HttpSession session) throws IOException {
        System.out.println("code:: " + code);

        // 접속토큰 get
        String kakaoToken = service.getReturnAccessToken(code);
        

        // 접속자 정보 get
        Map<String, Object> result = service.getUserInfo(kakaoToken);
        String id = (String) result.get("id");
        
        String userName = (String) result.get("nickname");
        String email = (String) result.get("email");
        String birth = (String) result.get("birth");
        String pass = id;

        // 분기
        MemberDTO member = new MemberDTO();
        member.setMember_id(email.split("@")[0]);
        member.setMember_pass(pass);
        member.setMember_nick(userName);
        member.setMember_email(email);
        member.setMember_birth(birth);
        member.setMember_phone("kakao");
        
        // 일치하는 snsId 없을 시 회원가입
        System.out.println(service.loginMember(member));
        if (service.loginMember(member) == null) {
            service.joinMember(member);
        }

        /* 로그아웃 처리 시, 사용할 토큰 값 */
        session.setAttribute("sId", email);
        session.setAttribute("member_nick", userName);

        return "redirect:/";

    }
}
