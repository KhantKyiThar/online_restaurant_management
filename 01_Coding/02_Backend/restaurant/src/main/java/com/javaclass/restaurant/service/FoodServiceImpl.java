package com.javaclass.restaurant.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaclass.restaurant.entity.Category;
import com.javaclass.restaurant.entity.Food;
import com.javaclass.restaurant.repository.FoodRepo;

@Service
public class FoodServiceImpl implements FoodService {

	@Autowired
	FoodRepo foodRepo;

	@Override
	public List<Food> getAll() {
		return foodRepo.findAll();
	}

	@Override
	public List<Food> getAllByCategory(Category category) {
		return foodRepo.findByCategory(category);
	}

	@Override
	public Food get(int id) {
		return foodRepo.findById(id).orElse(null);
	}

	@Override
	public Food create(Food food) {
		food.setCreatedAt(LocalDateTime.now());
		return foodRepo.save(food);
	}

	@Override
	public Food update(int id, Food food) {
		Food toUpdateFood = get(id);
		if (toUpdateFood != null) {
			toUpdateFood.setFoodItem(food.getFoodItem());
			toUpdateFood.setImage(food.getImage());
			toUpdateFood.setPrice(food.getPrice());
			toUpdateFood.setStock(food.getStock());
			toUpdateFood.setCategory(food.getCategory());
			toUpdateFood.setUpdatedAt(LocalDateTime.now());
			foodRepo.save(toUpdateFood);
		}
		return toUpdateFood;
	}

	@Override
	public boolean delete(int id) {
		Food food = get(id);
		if (food != null) {
			foodRepo.deleteById(id);
			return true;
		}
		return false;
	}

	@Override
	public Food getByFoodItem(String foodItem) {
		return foodRepo.findByFoodItem(foodItem);
	}

}
