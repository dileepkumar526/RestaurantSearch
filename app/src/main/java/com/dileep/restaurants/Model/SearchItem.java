package com.dileep.restaurants.Model;

import java.io.Serializable;

public class SearchItem implements Serializable {
    private String image_path;
    private String search_item_title;
    private String search_item_desc;
    private int restaurant_id;
    private Restaurant restaurant;
    private String menuItemNames;

    public int getRestaurant_id() {
        return restaurant_id;
    }

    public void setRestaurant_id(int restaurant_id) {
        this.restaurant_id = restaurant_id;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }

    public String getSearch_item_title() {
        return search_item_title;
    }

    public void setSearch_item_title(String search_item_title) {
        this.search_item_title = search_item_title;
    }

    public String getSearch_item_desc() {
        return search_item_desc;
    }

    public void setSearch_item_desc(String search_item_desc) {
        this.search_item_desc = search_item_desc;
    }

    public String getMenuItemNames() {
        return menuItemNames;
    }

    public void setMenuItemNames(String menuItemNames) {
        this.menuItemNames = menuItemNames;
    }
}
