package com.dileep.restaurants;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ProgressBar;
import android.widget.SearchView;

import com.dileep.restaurants.Adapter.SearchItemAdapter;
import com.dileep.restaurants.Data.AssetJsonData;
import com.dileep.restaurants.Model.Categories;
import com.dileep.restaurants.Model.Menu;
import com.dileep.restaurants.Model.MenuItem;
import com.dileep.restaurants.Model.Restaurant;
import com.dileep.restaurants.Model.SearchItem;
import com.dileep.restaurants.Utils.MenuUtil;
import com.dileep.restaurants.Utils.RestaurantUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private final String TAG= "MainActivity";

    private List<Restaurant> restaurantsList;

    private List<Menu> menuList;

    private Context mContext;

    private RecyclerView recyclerView;

    private List<SearchItem> searchItemList;

    private SearchView searchView;

    private AssetJsonData assetJsonData;

    private SearchItemAdapter searchItemAdapter;

    private Map<Restaurant,String> restaurantMenuMap;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        searchListener();
        BackGroundTask backGroundTask = new BackGroundTask();
        backGroundTask.execute();
    }



    private void searchListener() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                searchItems(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                searchItems(s);
                return false;
            }
        });
    }

    private void searchItems(String s) {
        if (TextUtils.isEmpty(s) || restaurantMenuMap == null) {
            return;
        }
        searchItemList.clear();

        for (Map.Entry<Restaurant, String> restaurantMenu : restaurantMenuMap.entrySet()) {
            String menuItemName = restaurantMenu.getValue();
            Restaurant restaurant = restaurantMenu.getKey();

            if (menuItemName.toLowerCase().contains(s.toLowerCase())) {
                SearchItem searchItem = new SearchItem();
                searchItem.setSearch_item_title(restaurant.getName());
                searchItem.setSearch_item_desc("Dish");
                searchItem.setRestaurant(restaurant);
                searchItem.setMenuItemNames(menuItemName);
                searchItemList.add(searchItem);
            }

            if (restaurant.getCuisine_type().toLowerCase().contains(s.toLowerCase())) {
                SearchItem searchItem = new SearchItem();
                searchItem.setSearch_item_title(restaurant.getName());
                searchItem.setSearch_item_desc("cuisine");
                searchItem.setImage_path(restaurant.getLogo_path());
                searchItem.setRestaurant(restaurant);
                searchItem.setMenuItemNames(menuItemName);
                searchItemList.add(searchItem);
            }

            if (restaurant.getName().toLowerCase().contains(s.toLowerCase())) {
                SearchItem searchItem = new SearchItem();
                searchItem.setSearch_item_title(restaurant.getName());
                searchItem.setSearch_item_desc("restaurant");
                searchItem.setImage_path(restaurant.getLogo_path());
                searchItem.setRestaurant(restaurant);
                searchItem.setMenuItemNames(menuItemName);
                searchItemList.add(searchItem);
            }
        }
        updateUI();
    }

    private void updateUI() {
        searchItemAdapter = new SearchItemAdapter(searchItemList, mContext);
        recyclerView.setAdapter(searchItemAdapter);
        searchItemAdapter.notifyDataSetChanged();
    }

    private List<SearchItem> loadData() {
        List<SearchItem> searchItemList = new ArrayList<>();
        String restaurantJsonData = assetJsonData.loadJsonDataFromAsset(RestaurantUtil.FILE_NAME);
        restaurantsList = assetJsonData.fetchRestaurantData(restaurantJsonData);
        String menuJsonData = assetJsonData.loadJsonDataFromAsset(MenuUtil.FILE_NAME);
        menuList = assetJsonData.fetchMenuData(menuJsonData);
        searchItemList = loadCache();
        return searchItemList;
    }

    private List<SearchItem> loadCache() {
        List<SearchItem> searchItemList = new ArrayList<>();
        restaurantMenuMap = new HashMap<>();
        for (Menu menu : menuList) {
            Restaurant menuRestaurant = new Restaurant();
            for (Restaurant restaurant : restaurantsList) {
                if (restaurant.getId() == menu.getRestaurantId()) {
                    menuRestaurant = restaurant;
                    break;
                }
            }
            List<Categories> categoriesList = menu.getCategoriesList();
            StringBuilder menuItemName = new StringBuilder();
            for(Categories categories : categoriesList) {
                List<MenuItem> menuItemList = categories.getMenuItemsList();
                for(MenuItem menuItem : menuItemList) {
                    menuItemName.append(menuItem.getName());
                    menuItemName.append(" , ");
                }
            }
            restaurantMenuMap.put(menuRestaurant, menuItemName.toString());
        }
        for (Restaurant restaurant : restaurantsList) {
            if(!restaurantMenuMap.containsKey(restaurant)) {
                restaurantMenuMap.put(restaurant,"");
            }
        }

        for (Map.Entry<Restaurant, String> restaurantMenu : restaurantMenuMap.entrySet()) {
            String menuItemName = restaurantMenu.getValue();
            Restaurant restaurant = restaurantMenu.getKey();
            SearchItem searchItem = new SearchItem();
            searchItem.setSearch_item_title(restaurant.getName());
            searchItem.setSearch_item_desc("restaurant");
            searchItem.setImage_path(restaurant.getLogo_path());
            searchItem.setRestaurant(restaurant);
            searchItem.setMenuItemNames(menuItemName);
            searchItemList.add(searchItem);
        }
        return searchItemList;
    }

    private void init() {
        mContext = MainActivity.this;
        restaurantsList = new ArrayList<>();
        menuList = new ArrayList<>();
        assetJsonData= new AssetJsonData(this);
        searchView = (SearchView) findViewById(R.id.searchview);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        searchItemList = new ArrayList<>();
    }

    private class BackGroundTask extends AsyncTask<String,String,List<SearchItem>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(mContext);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("fetching Restaurants...");
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(List<SearchItem> searchItems) {
            super.onPostExecute(searchItems);
            progressDialog.hide();
            searchItemList.clear();
            searchItemList = searchItems;
            updateUI();
        }

        @Override
        protected List<SearchItem> doInBackground(String... strings) {
            List<SearchItem> searchItemList = loadData();
            return searchItemList;
        }
    }
}