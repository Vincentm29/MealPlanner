package com.Mealplanner.beans;

import java.util.List;

import org.joda.time.DateTime;

import com.Mealplanner.dao.MealDao;

public class Day {
	private DateTime date;
	private List<Meal> listMeal;

	public DateTime getDate() {
		return date;
	}

	public void setDate(DateTime date) {
		this.date = date;
	}

	public List<Meal> getListMeal() {
		return listMeal;
	}

	public void setListMeal(List<Meal> listMeal) {
		this.listMeal = listMeal;
	}

	public void setMeal(MealDao mealDao) {

		String YYYYMMDD = date.toString("YYYYMMdd");
		// this.listMeal = mealDao.lister();
		this.listMeal = mealDao.getMealPerDay(YYYYMMDD);
	}
}
