package com.javaclass.restaurant.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.javaclass.restaurant.entity.FoodOrder;
import com.javaclass.restaurant.entity.Staff;
import com.javaclass.restaurant.service.OrderService;
import com.javaclass.restaurant.service.StaffService;

@RestController
@RequestMapping("/api/staff")
public class StaffController {

	@Autowired
	OrderService orderService;

	@AutoConfigureOrder
	StaffService staffService;

	@PostMapping("/order/add")
	public FoodOrder createOrder(@Valid @RequestBody FoodOrder foodOrder) {
		return orderService.createOrder(foodOrder);
	}

	@GetMapping("/order/{order_id}")
	public FoodOrder getByFoodID(@PathVariable int id) {
		return orderService.get(id);
	}

	@GetMapping("/order/{staff_id}")
	public ResponseEntity<?> getOrderByStaffId(@PathVariable int staffId) {

		Staff staff = staffService.get(staffId);
		if (staff == null) {
			return ResponseEntity.notFound().build();
		}

		List<FoodOrder> orderList = orderService.getAllByStaff(staff);
		return ResponseEntity.ok().body(orderList);
	}

}
