package com.Mealplanner.beans;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import org.eclipse.persistence.annotations.Convert;
import org.eclipse.persistence.annotations.Converter;
import org.joda.time.DateTime;

import com.Mealplanner.dao.JodaDateTimeConverter;

@Entity
public class Meal {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "MEAL_ID")
	private Long id;
	@Column(columnDefinition = "TIMESTAMP", name = "MEAL_DATE")
	@Converter(name = "dateTimeConverter", converterClass = JodaDateTimeConverter.class)
	@Convert("dateTimeConverter")
	private DateTime date;
	@Column(name = "MEAL_DAY")
	private String day;
	@Column(name = "MEAL_TYPE")
	private String mealType;
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "MEAL_DISH", joinColumns = @JoinColumn(name = "MEAL_ID", referencedColumnName = "MEAL_ID"), inverseJoinColumns = @JoinColumn(name = "DISH_ID", referencedColumnName = "DISH_ID"))
	private List<Dish> listDishes;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public DateTime getDate() {
		return date;
	}

	public void setDate(DateTime date) {
		this.date = date;
		this.day = (date.toString("YYYYMMdd"));
	}

	public void setDate(DateTime date, String mealType) {
		if (mealType.equals("Breakfast")) {
			date = date.withHourOfDay(9);
		} else if (mealType.equals("Lunch")) {
			date = date.withHourOfDay(12);
		} else if (mealType.equals("Dinner")) {
			date = date.withHourOfDay(19);
		} else {
			date = date.withHourOfDay(0);
		}
		this.date = date;
		this.day = (date.toString("YYYYMMdd"));
	}

	public String getMealType() {
		return mealType;
	}

	public void setMealType(String mealType) {
		this.mealType = mealType;
	}

	public List<Dish> getListDishes() {
		return listDishes;
	}

	public void setListDishes(List<Dish> listDishes) {
		this.listDishes = listDishes;
	}

	public void addDish(Dish dish) {
		List<Dish> listDishes = new ArrayList<Dish>();
		listDishes.add(dish);
		this.listDishes = listDishes;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

}
