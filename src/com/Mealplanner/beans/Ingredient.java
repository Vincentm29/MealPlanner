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
public class Ingredient implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ING_ID")
	private Long id;
	@Column(name = "ING_NAME")
	private String name;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ING_CATID")
	private IngredientCategory category;
	@Column(name = "ING_PRICE")
	private Double price;
	@Column(name = "ING_UNIT")
	private String priceUnit;

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getPriceUnit() {
		return priceUnit;
	}

	public void setPriceUnit(String priceUnit) {
		this.priceUnit = priceUnit;
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

	public IngredientCategory getCategory() {
		return category;
	}

	public void setCategory(IngredientCategory category) {
		this.category = category;
	}

	public static Comparator<Ingredient> IngredientNameComparator = new Comparator<Ingredient>() {

		public int compare(Ingredient ingredient1, Ingredient ingredient2) {

			String ingredientName1 = ingredient1.getName().toUpperCase();
			String ingredientName2 = ingredient2.getName().toUpperCase();

			// ascending order
			return ingredientName1.compareTo(ingredientName2);

			// descending order
			// return fruitName2.compareTo(fruitName1);
		}

	};
}
