package com.Mealplanner.beans;

import java.io.Serializable;
import java.util.Comparator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "IngredientCategory")
public class IngredientCategory implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "CAT_ING_ID")
	private Long id;
	@Column(name = "CAT_ING_NAME")
	private String name;

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

	public static Comparator<IngredientCategory> IngredientCategoryNameComparator = new Comparator<IngredientCategory>() {

		public int compare(IngredientCategory Category1,
				IngredientCategory Category2) {

			String CategoryName1 = Category1.getName().toUpperCase();
			String CategoryName2 = Category2.getName().toUpperCase();

			// ascending order
			return CategoryName1.compareTo(CategoryName2);

			// descending order
			// return fruitName2.compareTo(fruitName1);
		}

	};
}
