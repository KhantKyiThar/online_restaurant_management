package com.javaclass.restaurant.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.javaclass.restaurant.entity.FoodOrder;
import com.javaclass.restaurant.entity.Staff;

@EnableJpaRepositories
public interface OrderRepo extends JpaRepository<FoodOrder, Integer>{

	public List<FoodOrder> findByStaff(Staff staff);
	
}
