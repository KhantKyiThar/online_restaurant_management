package com.javaclass.restaurant.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.javaclass.restaurant.entity.Category;
import com.javaclass.restaurant.entity.Food;
import com.javaclass.restaurant.entity.Staff;
import com.javaclass.restaurant.service.CategoryService;
import com.javaclass.restaurant.service.FoodService;
import com.javaclass.restaurant.service.OrderService;
import com.javaclass.restaurant.service.StaffService;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

	@Autowired
	FoodService foodService;

	@Autowired
	StaffService staffService;
	
	@Autowired
	CategoryService categoryService;
	
	@Autowired
	OrderService orderService;

	@GetMapping("/food")
	List<Food> getFoodAll() {
		return foodService.getAll();
	}

	@PostMapping("/food/create")
	public Food foodCreate(@Valid @RequestBody Food food) {
		return foodService.create(food);
	}

	@PutMapping("/food/update/{id}")
	public ResponseEntity<Food> updateFood(@PathVariable int id, @Valid @RequestBody Food food) {
		Food updatedFood = foodService.update(id, food);
		if (updatedFood == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(updatedFood);
	}

	@DeleteMapping(value = "/food/delete/{id}")
	public ResponseEntity<?> deleteFood(@PathVariable int id) {
		Food food = foodService.get(id);
		if (food == null) {
			return ResponseEntity.notFound().build();
		}
		// String image=food.getImage();
		boolean isDeleted = foodService.delete(id);
		if (!isDeleted) {
			return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
		}

		return ResponseEntity.ok().build();
	}

	@GetMapping("/staff")
	List<Staff> getStaffAll() {
		return staffService.getAll();
	}

	@PostMapping("/staff/create")
	public Staff staffCreate(@Valid @RequestBody Staff staff) {
		return staffService.create(staff);
	}

	@PutMapping("/staff/update/{id}")
	public ResponseEntity<Staff> updateStaff(@PathVariable int id, @Valid @RequestBody Staff staff) {
		Staff updatedStaff = staffService.update(id, staff);
		if (updatedStaff == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(updatedStaff);
	}

	@DeleteMapping(value = "/staff/delete/{id}")
	public ResponseEntity<?> deleteStaff(@PathVariable int id) {
		Staff staff = staffService.get(id);
		if (staff == null) {
			return ResponseEntity.notFound().build();
		}
		boolean isDeleted = staffService.delete(id);
		if (!isDeleted) {
			return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
		}

		return ResponseEntity.ok().build();
	}
	
	//Category
	@GetMapping("/category")
	List<Category> getAll() {
		return categoryService.getAll();
	}

	@PostMapping("/category/create")
	public Category create(@Valid @RequestBody Category category) {
		return categoryService.create(category);
	}
	
	@PutMapping("/category/update/{id}")
	public ResponseEntity<Category> update(@PathVariable int id, @Valid @RequestBody Category category) {
		Category updateCategory = categoryService.update(id, category);
		if(updateCategory == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(updateCategory);
	}
	
	@DeleteMapping(value ="/category/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable int id) {
		Category delCategory = categoryService.get(id);
		if(delCategory == null) {
			return ResponseEntity.notFound().build();
		}
		boolean isDeleted = categoryService.delete(id);
		if (!isDeleted) {
			return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
		}

		return ResponseEntity.ok().build();
	}
	
	
}
