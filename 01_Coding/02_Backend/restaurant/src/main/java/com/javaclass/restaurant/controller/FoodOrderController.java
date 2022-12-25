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

import com.javaclass.restaurant.entity.FoodOrder;
import com.javaclass.restaurant.service.OrderService;

@RestController
@RequestMapping("/api")

public class FoodOrderController {
	@Autowired
	OrderService orderService;
	
	@GetMapping("/order")
	public List<FoodOrder> getAllFoodOrders() {
		return orderService.getAllFoodOrders();
	}
	
	@PostMapping("/order/create")
	public FoodOrder createOrder(@Valid @RequestBody FoodOrder foodOrder) {
		return orderService.createOrder(foodOrder);
	}
	
	@PutMapping("/order/update/{id}")
	public ResponseEntity<FoodOrder> updateOrder(@PathVariable int id, @Valid @RequestBody FoodOrder foodOrder) {
		FoodOrder updateOrder = orderService.updateOrder(id, foodOrder);
		if(updateOrder == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(updateOrder);
	}
	
	@DeleteMapping(value ="/order/delete/{id}")
	public ResponseEntity<?> deleteOrder(@PathVariable int id) {
		FoodOrder delOrder = orderService.get(id);
		if(delOrder == null) {
			return ResponseEntity.notFound().build();
		}
		boolean isDeleted = orderService.deleteOrder(id);
		if (!isDeleted) {
			return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
		}

		return ResponseEntity.ok().build();
	}
	
}
