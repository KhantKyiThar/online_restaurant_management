package com.javaclass.restaurant.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.javaclass.restaurant.entity.Category;
import com.javaclass.restaurant.service.CategoryService;

@RestController
public class CategoryController {
	@Autowired 
	CategoryService categoryService;	
	
	@GetMapping("/api/category")
	public List<Category> getAll() {
		return categoryService.getAll();
	}
}
