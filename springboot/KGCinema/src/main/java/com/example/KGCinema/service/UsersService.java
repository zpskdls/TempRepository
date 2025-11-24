package com.example.KGCinema.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.KGCinema.entity.Users;
import com.example.KGCinema.repository.UsersRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsersService {
	@Autowired
	UsersRepository usersRepository;
	
	public String findNameById(String userId) {
	    if(userId == null) {
	        throw new IllegalArgumentException("userId가 null입니다.");
	    }
	    return usersRepository.findById(userId)
	                          .orElseThrow(() -> new RuntimeException("유저가 존재하지 않습니다."))
	                          .getName();
	}

}
