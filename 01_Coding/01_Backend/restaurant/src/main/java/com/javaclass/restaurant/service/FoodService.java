package com.javaclass.restaurant.service;

import java.util.List;

import com.javaclass.restaurant.entity.Category;
import com.javaclass.restaurant.entity.Food;

public interface FoodService {

	public List<Food> getAll();	
	
	public Food get(int id);
	
	public Food create(Food food);
	
	public Food update(int id,Food food);
	
	public Food updateStock(int id,Food food);
	
	public boolean delete(int id);
	
	public List<Food> getAllByCategory(Category category);
	
	public Food getByFoodItem(String foodItem);

	

}
