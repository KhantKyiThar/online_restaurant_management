package com.javaclass.restaurant.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.javaclass.restaurant.entity.FoodOrder;
import com.javaclass.restaurant.entity.OrderDetail;
import com.javaclass.restaurant.entity.Staff;
import com.javaclass.restaurant.service.OrderDetailService;
import com.javaclass.restaurant.service.OrderService;
import com.javaclass.restaurant.service.StaffService;

@RestController
@RequestMapping("/api/staff")
public class StaffController {

	@Autowired
	OrderService orderService;

	@Autowired
	StaffService staffService;
	
	@Autowired
	OrderDetailService orderDetailService;

	@PostMapping("/order/add")
	public FoodOrder createOrder(@Valid @RequestBody FoodOrder foodOrder) {
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
