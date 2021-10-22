package com.dileep.restaurants.Model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class Restaurant implements Serializable {
    private int id;
    private String name;
    private String neighborhood;
    private String logo_path;
    private String address;
    private Map<String,String> latlng;
    private String cuisine_type;
    private Map<String,String> operating_hours;
    private List<Review> reviewList;



    public Map<String, String> getLatlng() {
        return latlng;
    }

    public void setLatlng(Map<String, String> latlng) {
        this.latlng = latlng;
    }

    public void setOperating_hours(Map<String, String> operating_hours) {
        this.operating_hours = operating_hours;
    }

    public Map<String, String> getOperating_hours() {
        return operating_hours;
    }

    public List<Review> getReviewList() {
        return reviewList;
    }

    public void setReviewList(List<Review> reviewList) {
        this.reviewList = reviewList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getLogo_path() {
        return logo_path;
    }

    public void setLogo_path(String logo_path) {
        this.logo_path = logo_path;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public String getCuisine_type() {
        return cuisine_type;
    }

    public void setCuisine_type(String cuisine_type) {
        this.cuisine_type = cuisine_type;
    }

}
