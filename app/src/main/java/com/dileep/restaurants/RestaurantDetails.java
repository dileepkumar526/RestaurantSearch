package com.dileep.restaurants;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import com.dileep.restaurants.Model.Restaurant;
import com.dileep.restaurants.Utils.MenuUtil;
import com.dileep.restaurants.Utils.RestaurantUtil;


public class RestaurantDetails extends AppCompatActivity {

    private final String TAG = "RestaurantDetails";

    private Context mContext;

    private TextView restaurant_name_tv;

    private TextView cuisine_type_tv;

    private TextView restaurant_address_tv;

    private TextView menu_names_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_restaurant_details);
        initData();
        loadRestaurantDetails();
    }

    private void initData() {
        restaurant_name_tv = (TextView) findViewById(R.id.restaurant_name_tv_id);
        cuisine_type_tv = (TextView) findViewById(R.id.cuisine_tv_id);
        restaurant_address_tv = (TextView) findViewById(R.id.address_tv_id);
        menu_names_tv = (TextView) findViewById(R.id.menu_name_tv_id);
    }

    private void loadRestaurantDetails() {
        Restaurant restaurant = (Restaurant) getIntent().getSerializableExtra(RestaurantUtil.KEY_RESTAURANT);
        String menuNames = getIntent().getStringExtra(MenuUtil.KEY_MENU);
        restaurant_name_tv.setText(restaurant.getName());
        restaurant_address_tv.setText(restaurant.getAddress());
        cuisine_type_tv.setText(restaurant.getCuisine_type());
        if (TextUtils.isEmpty(menuNames)) {
            menu_names_tv.setText(getResources().getString(R.string.menu_not_found));
        } else {
            menu_names_tv.setText(menuNames);
        }

    }
}