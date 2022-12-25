package com.javaclass.restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.javaclass.restaurant.entity.FoodOrder;

@EnableJpaRepositories
public interface OrderRepo extends JpaRepository<FoodOrder, Integer>{

}
