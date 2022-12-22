package com.javaclass.restaurant.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.javaclass.restaurant.entity.Food;
import com.javaclass.restaurant.entity.FoodOrder;
import com.javaclass.restaurant.entity.Staff;
import com.javaclass.restaurant.repository.FoodRepo;
import com.javaclass.restaurant.repository.OrderRepo;
import com.javaclass.restaurant.repository.StaffRepo;

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
		Food food = foodRepo.findById(foodOrder.getFood().getId()).orElse(null);
		if (food == null) {
			return null;
		}
		
		Staff staff = staffRepo.findById(foodOrder.getStaff().getId()).orElse(null);
		if (staff == null) {
			return null;
		}
		return null;
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
	public List<FoodOrder> getAllByStaffID(int staffId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
