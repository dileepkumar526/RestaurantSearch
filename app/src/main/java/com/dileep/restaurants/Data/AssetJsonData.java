package com.dileep.restaurants.Data;

import android.content.Context;
import android.util.Log;

import com.dileep.restaurants.Model.Categories;
import com.dileep.restaurants.Model.Menu;
import com.dileep.restaurants.Model.MenuItem;
import com.dileep.restaurants.Model.Restaurant;
import com.dileep.restaurants.Model.Review;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AssetJsonData {

    private Context mContext;

    private final String TAG = "AssetJsonData";

    private List<Restaurant> restaurantsList;

    private List<Menu> menuList;


    public AssetJsonData(Context context) {
        this.mContext = context;
    }

    public List<Restaurant> fetchRestaurantData(String jsonData) {
        List<Restaurant> restaurantsList = new ArrayList<>();
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(jsonData);
            if(jsonObject.has("restaurants")) {
                JSONArray restaurantsArray = jsonObject.getJSONArray("restaurants");
                for (int i = 0; i < restaurantsArray.length() ; i++) {
                    JSONObject restaurantObject = restaurantsArray.getJSONObject(i);
                    Restaurant restaurant = new Restaurant();
                    restaurant.setId(restaurantObject.getInt("id"));
                    restaurant.setName(restaurantObject.getString("name"));
                    restaurant.setNeighborhood(restaurantObject.getString("neighborhood"));
                    restaurant.setLogo_path(restaurantObject.getString("photograph"));
                    restaurant.setAddress(restaurantObject.getString("address"));
                    restaurant.setCuisine_type(restaurantObject.getString("cuisine_type"));
                    if (restaurantObject.has("latlng")) {
                        JSONObject latLangJsonObject = restaurantObject.getJSONObject("latlng");
                        Map<String,String> latLangMap = new HashMap<>();
                        latLangMap.put("lat",latLangJsonObject.getString("lat"));
                        latLangMap.put("lng",latLangJsonObject.getString("lng"));
                        restaurant.setLatlng(latLangMap);
                    }
                    if (restaurantObject.has("operating_hours")) {
                        JSONObject ohJsonObject = restaurantObject.getJSONObject("operating_hours");
                        Map<String,String> operatingHoursMap = new HashMap<>();
                        operatingHoursMap.put("Monday",ohJsonObject.getString("Monday"));
                        operatingHoursMap.put("Tuesday",ohJsonObject.getString("Tuesday"));
                        operatingHoursMap.put("Wednesday",ohJsonObject.getString("Wednesday"));
                        operatingHoursMap.put("Thursday",ohJsonObject.getString("Thursday"));
                        operatingHoursMap.put("Friday",ohJsonObject.getString("Friday"));
                        operatingHoursMap.put("Saturday",ohJsonObject.getString("Saturday"));
                        operatingHoursMap.put("Sunday",ohJsonObject.getString("Sunday"));
                        restaurant.setOperating_hours(operatingHoursMap);
                    }
                    if (restaurantObject.has("reviews")) {
                        List<Review> reviewList = new ArrayList<>();
                        JSONArray reviewsJsonArray = restaurantObject.getJSONArray("reviews");
                        for (int j=0 ; j < reviewsJsonArray.length(); j++) {
                            JSONObject reviewJsonObject = reviewsJsonArray.getJSONObject(j);
                            Review review = new Review();
                            review.setName(reviewJsonObject.getString("name"));
                            review.setDate(reviewJsonObject.getString("date"));
                            review.setRating(reviewJsonObject.getDouble("rating"));
                            review.setComments(reviewJsonObject.getString("comments"));
                            reviewList.add(review);
                        }
                        restaurant.setReviewList(reviewList);
                    }
                    restaurantsList.add(restaurant);
                }
            }
        } catch (JSONException e) {
            Log.i(TAG," fetchRestaurantData JSONException : "+ e);
        }
        setRestaurantsList(restaurantsList);
        return restaurantsList;
    }

    public List<Menu> fetchMenuData(String jsonData) {
        List<Menu> menuList = new ArrayList<>();
        JSONObject jsonObject = null;
        try{
            jsonObject = new JSONObject(jsonData);
            if (jsonObject.has("menus")) {
                JSONArray menuJsonArray = jsonObject.getJSONArray("menus");
                for (int i=0 ; i < menuJsonArray.length() ; i++) {
                    Menu menu = new Menu();
                    JSONObject menuJsonObject = menuJsonArray.getJSONObject(i);
                    menu.setRestaurantId(menuJsonObject.getInt("restaurantId"));
                    if (menuJsonObject.has("categories")) {
                        JSONArray categoriesJsonArray = menuJsonObject.getJSONArray("categories");
                        List<Categories> categoriesList = new ArrayList<>();
                        for (int j=0 ; j < categoriesJsonArray.length() ; j++) {
                            JSONObject categoriesJsonObject = categoriesJsonArray.getJSONObject(j);
                            Categories categories = new Categories();
                            categories.setId(categoriesJsonObject.getLong("id"));
                            categories.setName(categoriesJsonObject.getString("name"));
                            if (categoriesJsonObject.has("menu-items")) {
                                JSONArray menuItemJsonArray = categoriesJsonObject.getJSONArray("menu-items");
                                List<MenuItem> menuItemsList = new ArrayList<>();
                                for( int k=0 ; k < menuItemJsonArray.length() ; k++) {
                                    JSONObject menuItemJsonObject = menuItemJsonArray.getJSONObject(k);
                                    MenuItem menuItem = new MenuItem();
                                    menuItem.setId(menuItemJsonObject.getLong("id"));
                                    menuItem.setName(menuItemJsonObject.getString("name"));
                                    menuItem.setDescription(menuItemJsonObject.getString("description"));
                                    menuItem.setPrice(menuItemJsonObject.getDouble("price"));
                                    if(menuItemJsonObject.has("images")) {
                                        List<String> menuItemImageList = new ArrayList<>();
                                        JSONArray imageJsonArray = menuItemJsonObject.getJSONArray("images");
                                        for (int l=0 ; l < imageJsonArray.length() ; l++) {
                                            menuItemImageList.add(imageJsonArray.getJSONObject(l).getString("path"));
                                        }
                                        menuItem.setImageList(menuItemImageList);
                                    }
                                    menuItemsList.add(menuItem);
                                }
                                categories.setMenuItemsList(menuItemsList);
                            }
                            categoriesList.add(categories);
                        }
                        menu.setCategoriesList(categoriesList);
                    }
                    menuList.add(menu);
                }
            }
        } catch (JSONException e) {
            Log.i(TAG," fetchMenuData JSONException : "+ e);
        }
        setMenuList(menuList);
        return menuList;
    }

    public String loadJsonDataFromAsset(String path){
        String restaurantJson = null;
        try {
            InputStream inputStream = mContext.getAssets().open(path);
            int size = inputStream.available();
            byte[] bytes = new byte[size];
            inputStream.read(bytes);
            inputStream.close();
            restaurantJson = new String(bytes, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return  restaurantJson;
    }

    public List<Restaurant> getRestaurantsList() {
        return restaurantsList;
    }

    public void setRestaurantsList(List<Restaurant> restaurantsList) {
        this.restaurantsList = restaurantsList;
    }

    public List<Menu> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<Menu> menuList) {
        this.menuList = menuList;
    }
}
