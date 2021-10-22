package com.dileep.restaurants.Model;

import java.io.Serializable;
import java.util.List;

public class Menu implements Serializable {
    private int restaurantId;
    private List<Categories> categoriesList;

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    public List<Categories> getCategoriesList() {
        return categoriesList;
    }

    public void setCategoriesList(List<Categories> categoriesList) {
        this.categoriesList = categoriesList;
    }
}
