package com.javaclass.restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.javaclass.restaurant.entity.Category;

@EnableJpaRepositories
public interface CategoryRepo extends JpaRepository<Category, Integer>{

}
