package com.javaclass.restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.javaclass.restaurant.entity.Staff;

public interface StaffRepo extends JpaRepository<Staff, Integer> {
	
	public Staff findByLoginID(String loginId);
}
