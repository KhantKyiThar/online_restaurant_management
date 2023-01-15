package com.javaclass.restaurant.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.javaclass.restaurant.entity.Food;
import com.javaclass.restaurant.entity.FoodOrder;
import com.javaclass.restaurant.entity.OrderDetail;
import com.javaclass.restaurant.entity.Staff;
import com.javaclass.restaurant.service.OrderDetailService;
import com.javaclass.restaurant.service.FoodService;
import com.javaclass.restaurant.service.OrderService;
import com.javaclass.restaurant.service.StaffService;

@RestController
@RequestMapping("/api/staff")
public class StaffController {

	@Autowired
	FoodService foodService;

	@Autowired
	OrderService orderService;

	@Autowired
	StaffService staffService;

	@Autowired
	OrderDetailService orderDetailService;

	@GetMapping("/profile")
	public ResponseEntity<Staff> getProfile(@RequestParam int id) {
		Staff staff = staffService.get(id);
		if (staff == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(staff);
	}

	@PutMapping("/food/update/{id}")
	public ResponseEntity<Food> updateFood(@PathVariable int id, @Valid @RequestBody Food food) {
		Food updatedFood = foodService.updateStock(id, food);
		if (updatedFood == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(updatedFood);
	}

	@PostMapping("/order/add")
	public FoodOrder createOrder(@Valid @RequestBody FoodOrder foodOrder) {
		
		Staff updateStaff = foodOrder.getStaff();
		staffService.updateDeleteable(updateStaff.getId(), updateStaff);
		return orderService.createOrder(foodOrder);
		
	}

	@GetMapping("/order/{staff_id}")
	public ResponseEntity<?> getOrderByStaffId(@PathVariable("staff_id") int staffId) {

		Staff staff = staffService.get(staffId);
		if (staff == null) {
			return ResponseEntity.badRequest().body("Staff ID is invalid");
		}

		List<FoodOrder> orderList = orderService.getAllByStaff(staff);
		return ResponseEntity.ok().body(orderList);
	}

	@GetMapping("/order/detail/{order_id}")
	public ResponseEntity<?> getOrderDetailByOrderId(@PathVariable("order_id") int orderId) {

		FoodOrder foodOrder = orderService.get(orderId);
		if (foodOrder == null) {
			return ResponseEntity.badRequest().body("Order ID is invalid");
		}

		List<OrderDetail> orderDetailList = orderDetailService.getAllByFoodOrder(foodOrder);
		return ResponseEntity.ok().body(orderDetailList);
	}

	@PostMapping("/order/detail")
	public OrderDetail createOrderDetail(@Valid @RequestBody OrderDetail orderDetail) {
		return orderDetailService.createOrderDetail(orderDetail);
	}

}
