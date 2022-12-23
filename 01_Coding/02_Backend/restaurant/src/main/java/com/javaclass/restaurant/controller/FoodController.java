package com.javaclass.restaurant.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.javaclass.restaurant.entity.Category;
import com.javaclass.restaurant.entity.Food;
import com.javaclass.restaurant.service.CategoryService;
import com.javaclass.restaurant.service.FoodService;
import com.javaclass.restaurant.service.StorageService;

@RestController
@RequestMapping("/api")
public class FoodController {
	@Autowired
	FoodService foodService;
	
	@Autowired
	StorageService storageService;
	
	@Autowired
	CategoryService categoryService;
	
	//Get
	@GetMapping("/food")
	public List<Food> getAll() {
		return foodService.getAll();
	}
		
	@GetMapping("/food/category/{category_id}")
	public ResponseEntity<?> getFoodByCategory(@PathVariable("category_id") int categoryID){
		Category category=categoryService.get(categoryID);
		if(category==null) {
			return ResponseEntity.badRequest().body("Category ID is invalid");
		}
		List<Food> foodList=foodService.getAllByCategory(category);
		return ResponseEntity.ok().body(foodList);
	}
	
	@GetMapping("/images/{fileType}/{fileName}")
	public ResponseEntity<?> getImage(@PathVariable("fileType") String fileType,@PathVariable("fileName") String fileName)throws IOException{
		MediaType contentType=MediaType.IMAGE_PNG;
		switch(fileType) {
		case "jpg":
			contentType=MediaType.IMAGE_JPEG;
			break;
		case "png":
			contentType=MediaType.IMAGE_PNG;
			break;
		default:
			return ResponseEntity.badRequest().body("Unsupported File Type");
		}
		byte[] fileBytes=storageService.load(fileName);
		if(fileBytes==null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().contentType(contentType).body(fileBytes);
	}
}
