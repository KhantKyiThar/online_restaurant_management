package com.javaclass.restaurant.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaclass.restaurant.entity.FoodOrder;
import com.javaclass.restaurant.entity.Staff;
import com.javaclass.restaurant.repository.FoodRepo;
import com.javaclass.restaurant.repository.OrderRepo;
import com.javaclass.restaurant.repository.StaffRepo;

@Service
public class OrderServiceImpl implements OrderService {
	@Autowired
	private OrderRepo orderRepo;

	@Autowired
	FoodRepo foodRepo;

	@Autowired
	StaffRepo staffRepo;

	@Override
	public List<FoodOrder> getAllFoodOrders() {
		return orderRepo.findAll();
	}

	@Override
	public FoodOrder get(int id) {
		// TODO Auto-generated method stub
		return orderRepo.findById(id).orElse(null);
	}

	@Override
	public FoodOrder createOrder(FoodOrder foodOrder) {
//		Food food = foodRepo.findById(foodOrder.getFood().getId()).orElse(null);
//		if (food == null) {
//			return null;
//		}
//
//		Staff staff = staffRepo.findById(foodOrder.getStaff().getId()).orElse(null);
//		if (staff == null) {
//			return null;
//		}
		foodOrder.setCreatedAt(LocalDateTime.now());
		return orderRepo.save(foodOrder);
	}

	@Override
	public FoodOrder updateOrder(int id, FoodOrder foodOrder) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleteOrder(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<FoodOrder> getAllByFoodID(int foodId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<FoodOrder> getAllByStaff(Staff staff) {
		return orderRepo.findByStaff(staff);
	}

}
