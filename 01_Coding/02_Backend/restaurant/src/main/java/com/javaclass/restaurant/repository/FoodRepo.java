package com.javaclass.restaurant.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.javaclass.restaurant.entity.Category;
import com.javaclass.restaurant.entity.Food;

@EnableJpaRepositories
public interface FoodRepo extends JpaRepository<Food, Integer>{
	
	public List<Food> findByCategory(Category category);
}
