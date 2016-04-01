SELECT * FROM Meal m
JOIN MEAL_DISH md
ON m.MEAL_ID = md.MEAL_ID
JOIN Dish d
on md.DISH_ID = d.DISH_ID

JOIN DishIngredient di
ON d.DISH_ID = di.DISH_ID

JOIN Ingredient i
ON di.ING_ID = i.ING_ID

---------------------------------------------

SELECT i.ING_NAME, SUM(di.INGDISH_QUANTITY) as Quantity,di.INGDISH_UNIT FROM Meal m
JOIN MEAL_DISH md
ON m.MEAL_ID = md.MEAL_ID
JOIN Dish d
on md.DISH_ID = d.DISH_ID

JOIN DishIngredient di
ON d.DISH_ID = di.DISH_ID

JOIN Ingredient i
ON di.ING_ID = i.ING_ID
GROUP BY i.ING_NAME, di.INGDISH_UNIT
ORDER BY 1,2,3