package com.javaclass.restaurant.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.javaclass.restaurant.entity.Food;
import com.javaclass.restaurant.service.FoodService;

@RestController
public class FoodController {
	@Autowired
	private FoodService foodService;
	
	//Get
		@GetMapping(path = "/api/food")
		public List<Food> getAll() {
			return foodService.getAll();
		}
}
