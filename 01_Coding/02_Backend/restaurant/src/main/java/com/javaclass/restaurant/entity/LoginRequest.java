package com.javaclass.restaurant.entity;

import javax.validation.constraints.NotBlank;

public class LoginRequest {

	@NotBlank(message="Required")
	private String loginId;
	
	@NotBlank(message="Required")
	private String password;

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
	
}
