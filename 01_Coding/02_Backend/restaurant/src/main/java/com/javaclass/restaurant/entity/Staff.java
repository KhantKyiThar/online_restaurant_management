package com.javaclass.restaurant.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Staff {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(length = 100, nullable = false)
	private String name;
	
	
	@Column(length = 100, nullable = false)
	private String loginId;
	
	@Column(length = 100, nullable = false)
	private String password;
	
	@Column(length = 30, nullable = false)
	private String phone;
	
	@Column(columnDefinition = "ENUM('Male', 'Female')")
	@Enumerated(EnumType.STRING)
	private Gender gender;
	
	@Column(columnDefinition = "ENUM('Admin', 'Manager', 'Staff')")
	@Enumerated(EnumType.STRING)
	private StaffType StaffType;
	
	@Column(nullable = true)
	private LocalDate joinedDate;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public StaffType getStaffType() {
		return StaffType;
	}

	public void setStaffType(StaffType staffType) {
		StaffType = staffType;
	}

	public LocalDate getJoinedDate() {
		return joinedDate;
	}

	public void setJoinedDate(LocalDate joinedDate) {
		this.joinedDate = joinedDate;
	}

	@Override
	public String toString() {
		return "Staff [id=" + id + ", name=" + name + ", loginId=" + loginId + ", password=" + password + ", phone="
				+ phone + ", gender=" + gender + ", StaffType=" + StaffType + ", joinedDate=" + joinedDate + "]";
	}
	
	

}