package com.Mealplanner.beans;

import java.io.Serializable;
import java.util.Comparator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class DishIngredient implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "INGDISH_ID")
	private Long id;
	@Column(name = "INGDISH_UNIT")
	private String unit;
	@Column(name = "INGDISH_QUANTITY")
	private Double quantity;

	@Column(name = "INGDISH_COMMENT")
	private String comment;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ING_ID")
	private Ingredient ingredient;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DISH_ID")
	private Dish dish;

	public Dish getDish() {
		return dish;
	}

	public void setDish(Dish dish) {
		this.dish = dish;
	}

	public Ingredient getIngredient() {
		return ingredient;
	}

	public void setIngredient(Ingredient ingredient) {
		this.ingredient = ingredient;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getName() {
		return ingredient.getName();
	}

	public String getCategory() {
		return ingredient.getCategory().getName();
	}

	public static Comparator<DishIngredient> IngredientNameComparator = new Comparator<DishIngredient>() {

		public int compare(DishIngredient ingredient1,
				DishIngredient ingredient2) {

			String ingredientName1 = ingredient1.getName().toUpperCase();
			String ingredientName2 = ingredient2.getName().toUpperCase();

			// ascending order
			return ingredientName1.compareTo(ingredientName2);

			// descending order
			// return fruitName2.compareTo(fruitName1);
		}

	};
}
