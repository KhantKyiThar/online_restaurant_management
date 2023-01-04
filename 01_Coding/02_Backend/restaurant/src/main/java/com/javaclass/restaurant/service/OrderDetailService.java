package com.javaclass.restaurant.service;

import java.util.List;

import com.javaclass.restaurant.entity.FoodOrder;
import com.javaclass.restaurant.entity.OrderDetail;

public interface OrderDetailService {

	public List<OrderDetail> getAll();

	public List<OrderDetail> getAllByFoodOrder(FoodOrder foodOrder);

	public OrderDetail get(int id);

	public OrderDetail createOrderDetail(OrderDetail orderDetail);

}
