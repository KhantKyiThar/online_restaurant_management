package com.javaclass.restaurant.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.ColumnDefault;

@Entity
public class FoodOrder {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(nullable = false)
	private int foodCount;

	@Column(nullable = true)
	private int totalPrice;

	@ManyToOne
	@JoinColumn(name = "food_id", referencedColumnName = "id")
	private Food food;

	@ManyToOne
	@JoinColumn(name = "staff_id", referencedColumnName = "id")
	private Staff staff;

	@Column(nullable = true)
	@ColumnDefault("CURRENT_TIMESTAMP")
	private LocalDateTime orderDate;

	@Column(nullable = false)
	@ColumnDefault("CURRENT_TIMESTAMP")
	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getFoodCount() {
		return foodCount;
	}

	public void setFoodCount(int foodCount) {
		this.foodCount = foodCount;
	}

	public int getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Food getFood() {
		return food;
	}

	public void setFood(Food food) {
		this.food = food;
	}

	public Staff getStaff() {
		return staff;
	}

	public void setStaff(Staff staff) {
		this.staff = staff;
	}

	public LocalDateTime getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(LocalDateTime orderDate) {
		this.orderDate = orderDate;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	@Override
	public String toString() {
		return "FoodOrder [id=" + id + ", foodCount=" + foodCount + ", totalPrice=" + totalPrice + ", food=" + food
				+ ", staff=" + staff + ", orderDate=" + orderDate + ", createdAt=" + createdAt + ", updatedAt="
				+ updatedAt + "]";
	}

}
