package com.javaclass.restaurant.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaclass.restaurant.entity.FoodOrder;
import com.javaclass.restaurant.entity.Staff;
import com.javaclass.restaurant.repository.OrderRepo;

@Service
public class OrderServiceImpl implements OrderService {
	@Autowired
	OrderRepo orderRepo;

	@Override
	public List<FoodOrder> getAllFoodOrders() {
		return orderRepo.findAll();
	}

	@Override
	public FoodOrder get(int id) {
		return orderRepo.findById(id).orElse(null);
	}

	@Override
	public FoodOrder createOrder(FoodOrder foodOrder) {
		foodOrder.setCreatedAt(LocalDateTime.now());
		return orderRepo.save(foodOrder);
	}

	@Override
	public FoodOrder updateOrder(int id, FoodOrder foodOrder) {
		FoodOrder updateOrder = get(id);
		if (updateOrder != null) {
			updateOrder.setFoodCount(foodOrder.getFoodCount());
			updateOrder.setTotalPrice(foodOrder.getTotalPrice());
			updateOrder.setFood(foodOrder.getFood());
			updateOrder.setStaff(foodOrder.getStaff());
			updateOrder.setUpdatedAt(LocalDateTime.now());
			orderRepo.save(updateOrder);
		}
		return updateOrder;
	}

	@Override
	public boolean deleteOrder(int id) {
		orderRepo.deleteById(id);
		return true;
	}

	@Override
	public List<FoodOrder> getAllByStaff(Staff staff) {
		return orderRepo.findByStaff(staff);
	}
}
