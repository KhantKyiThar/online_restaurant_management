package com.javaclass.restaurant.controller;

import java.io.IOException;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.javaclass.restaurant.entity.LoginRequest;
import com.javaclass.restaurant.entity.Staff;
import com.javaclass.restaurant.service.StaffService;

@RestController
@RequestMapping("/api")
public class LoginController {

	@Autowired
	StaffService staffService;
	
	@Autowired
	PasswordEncoder pwEncoder;
	
	@PostMapping("/login")
	public ResponseEntity<Staff> login(@Valid @RequestBody LoginRequest loginReq){
		Staff staff=staffService.checkLoginStaff(loginReq.getLoginId(), loginReq.getPassword());
		if(staff==null) {
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.ok().body(staff);
	}
	
	@GetMapping("/logout")
	public Boolean logout(HttpSession session) throws IOException{
		session.invalidate();
		return true;
	}
}
