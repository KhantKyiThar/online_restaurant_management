package com.javaclass.restaurant.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaclass.restaurant.entity.FoodOrder;
import com.javaclass.restaurant.entity.OrderDetail;
import com.javaclass.restaurant.repository.OrderDetailRepo;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {

	@Autowired
	OrderDetailRepo orderDetailRepo;

	@Override
	public List<OrderDetail> getAll() {
		return orderDetailRepo.findAll();
	}

	@Override
	public OrderDetail get(int id) {
		return orderDetailRepo.findById(id).orElse(null);
	}

	@Override
	public List<OrderDetail> getAllByFoodOrder(FoodOrder foodOrder) {
		return orderDetailRepo.findByFoodOrder(foodOrder);
	}

	@Override
	public OrderDetail createOrderDetail(OrderDetail orderDetail) {
		
		return orderDetailRepo.save(orderDetail);
	}
}
