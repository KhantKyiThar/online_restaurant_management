package com.javaclass.restaurant.service;

import java.util.List;

import com.javaclass.restaurant.entity.Staff;

public interface StaffService {

	public List<Staff> getAll();

	public Staff get(int id);

	public Staff create(Staff food);

	public Staff update(int id, Staff food);

	public boolean delete(int id);
	
	public Staff checkLoginStaff(String loginId, String password);
	
}
