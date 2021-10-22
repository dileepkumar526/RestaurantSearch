package com.dileep.restaurants.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dileep.restaurants.Model.Menu;
import com.dileep.restaurants.Model.SearchItem;
import com.dileep.restaurants.R;
import com.dileep.restaurants.RestaurantDetails;
import com.dileep.restaurants.Utils.MenuUtil;
import com.dileep.restaurants.Utils.RestaurantUtil;

import java.util.List;

public class SearchItemAdapter extends RecyclerView.Adapter<SearchItemAdapter.ViewHolder> {

    private List<SearchItem> searchItemList;
    private Context mContext;

    public SearchItemAdapter(List<SearchItem> searchItemList, Context mContext) {
        this.searchItemList = searchItemList;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.search_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SearchItemAdapter.ViewHolder holder, int position) {
        SearchItem searchItem = searchItemList.get(position);
        holder.searchItemTitle.setText(searchItem.getSearch_item_title());
        holder.searchItemDesc.setText(searchItem.getSearch_item_desc());
        holder.searchItem = searchItem;
    }

    @Override
    public int getItemCount() {
        return searchItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView searchItemTitle;
        private TextView searchItemDesc;
        private ImageView imageView;
        private SearchItem searchItem;
        public ViewHolder(View itemView) {
            super(itemView);
            searchItemTitle = (TextView) itemView.findViewById(R.id.item_title_tv_id);
            searchItemDesc = (TextView) itemView.findViewById(R.id.item_desc_tv_id);
            imageView = (ImageView) itemView.findViewById(R.id.itemimageview);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, RestaurantDetails.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.putExtra(RestaurantUtil.KEY_RESTAURANT, searchItem.getRestaurant());
                    intent.putExtra(MenuUtil.KEY_MENU,searchItem.getMenuItemNames());
                    mContext.startActivity(intent);
                }
            });
        }
    }
}
