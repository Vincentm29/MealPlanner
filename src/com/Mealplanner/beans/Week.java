package com.Mealplanner.beans;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.joda.time.DateTime;

public class Week {
	private static Map<String, TreeMap<String, Day>> listAllDays = new HashMap<String, TreeMap<String, Day>>();

	public Map<String, Day> getNext7Days(String yearWeek) {
		TreeMap<String, Day> list7Days = new TreeMap<String, Day>();
		if (listAllDays.get(yearWeek) == null) {
			int year = Integer.parseInt(yearWeek.substring(0, 4));
			int week = Integer.parseInt(yearWeek.substring(4, 6));

			for (int i = 0; i < 7; i++) {
				DateTime date = new DateTime().withYear(year)
						.withWeekOfWeekyear(week).withDayOfWeek(1)
						.withMinuteOfHour(0).withSecondOfMinute(0)
						.withMillisOfSecond(0);
				Day day = new Day();
				day.setDate(date.plusDays(i));
				// day.setMeal();
				list7Days.put(date.plusDays(i).toString("YYYYMMdd"), day);
			}

			listAllDays.put(yearWeek, list7Days);
		} else {
			list7Days = listAllDays.get(yearWeek);
		}
		list7Days.keySet();
		return list7Days;
	}
}
