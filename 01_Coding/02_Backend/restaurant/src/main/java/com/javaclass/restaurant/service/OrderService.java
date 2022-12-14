package com.javaclass.restaurant.service;

import java.util.List;

import com.javaclass.restaurant.entity.FoodOrder;
import com.javaclass.restaurant.entity.Staff;

public interface OrderService {
	
	public List<FoodOrder> getAllFoodOrders();
	
	public List<FoodOrder> getAllByStaff(Staff staff);
	
	public FoodOrder get(int id);
	
	public FoodOrder createOrder(FoodOrder foodOrder);
	
	public FoodOrder updateOrder(int id, FoodOrder foodOrder);
	
	public boolean deleteOrder(int id);

}
