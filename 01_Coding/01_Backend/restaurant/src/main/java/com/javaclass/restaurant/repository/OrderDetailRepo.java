package com.javaclass.restaurant.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.javaclass.restaurant.entity.FoodOrder;
import com.javaclass.restaurant.entity.OrderDetail;

@EnableJpaRepositories
public interface OrderDetailRepo extends JpaRepository<OrderDetail, Integer>{
	
	public List<OrderDetail> findByFoodOrder(FoodOrder foodOrder);
	
	
}