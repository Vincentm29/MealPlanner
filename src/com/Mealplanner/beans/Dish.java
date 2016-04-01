package com.Mealplanner.beans;

import java.io.Serializable;
import java.util.Comparator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Dish implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "DISH_ID")
	private Long id;
	@Column(name = "DISH_NAME")
	private String name;
	@Column(name = "DISH_CATEGORY")
	private String category;
	@Column(name = "DISH_NBPEOPLE")
	private Long numberOfPeople;

	/*
	 * @OneToMany(fetch = FetchType.LAZY)
	 * 
	 * @JoinColumn(name = "INGDISH_ID") private List<DishIngredient>
	 * listDishIngredients;
	 */
	@Column(name = "DISH_COOKING_TIME")
	private Long cookingTime;
	@Column(name = "DISH_PREP_TIME")
	private Long preparationTime;
	@Column(name = "DISH_PRICE_RANGE")
	private String priceRange;
	@Column(name = "DISH_PRICE")
	private Double dishPrice;

	public Double getDishPrice() {
		return dishPrice;
	}

	public void setDishPrice(Double dishPrice) {
		this.dishPrice = dishPrice;
	}

	public Long getCookingTime() {
		return cookingTime;
	}

	public void setCookingTime(Long cookingTime) {
		this.cookingTime = cookingTime;
	}

	public Long getPreparationTime() {
		return preparationTime;
	}

	public void setPreparationTime(Long preparationTime) {
		this.preparationTime = preparationTime;
	}

	public String getPriceRange() {
		return priceRange;
	}

	public void setPriceRange(String priceRange) {
		this.priceRange = priceRange;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Long getNumberOfPeople() {
		return numberOfPeople;
	}

	public void setNumberOfPeople(Long numberOfPeople) {
		this.numberOfPeople = numberOfPeople;
	}

	/*
	 * public List<DishIngredient> getListDishIngredients() { return
	 * listDishIngredients; }
	 * 
	 * public void setListDishIngredients(List<DishIngredient>
	 * listDishIngredients) { this.listDishIngredients = listDishIngredients; }
	 */

	public static Comparator<Dish> DishNameComparator = new Comparator<Dish>() {

		public int compare(Dish dish1, Dish dish2) {

			String dishName1 = dish1.getName().toUpperCase();
			String dishName2 = dish2.getName().toUpperCase();

			// ascending order
			return dishName1.compareTo(dishName2);

			// descending order
			// return fruitName2.compareTo(fruitName1);
		}

	};

}
