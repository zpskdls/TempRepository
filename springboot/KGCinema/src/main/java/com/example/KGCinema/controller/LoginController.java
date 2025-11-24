package com.example.KGCinema.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;

@RestController
public class LoginController {
	
	@PostMapping("/login")
	public String login(
			@RequestParam("user_id") String user_id, HttpSession session) {

		// 세션에 값 저장
		session.setAttribute("user_id", user_id);
		System.out.println(user_id + " 세션 저장");

		// null을 반환하면 클라이언트에서 문제가 될 수 있음 → 문자열 반환 권장
		return "login-success";
	}
	
	@GetMapping("/login/user")
    public Map<String, Object> getCurrentUser(HttpSession session) {
        Map<String, Object> map = new HashMap<>();
        String userId = (String) session.getAttribute("user_id");
        if (userId != null) {
            map.put("user_id", userId);
            map.put("rt", "OK");
        } else {
            map.put("rt", "NOT_LOGGED_IN");
        }
        return map;
    }
}
