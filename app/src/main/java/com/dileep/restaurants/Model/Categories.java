package com.dileep.restaurants.Model;

import java.io.Serializable;
import java.util.List;

public class Categories implements Serializable {
    private long id;
    private String name;
    private List<MenuItem> menuItemsList;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<MenuItem> getMenuItemsList() {
        return menuItemsList;
    }

    public void setMenuItemsList(List<MenuItem> menuItemsList) {
        this.menuItemsList = menuItemsList;
    }
}
