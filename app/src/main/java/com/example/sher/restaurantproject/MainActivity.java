package com.example.sher.restaurantproject;

import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity  {

    private List<String> RestaurantList = new ArrayList<>();
    private List<String> locationList = new ArrayList<>();
    private List<String> countryList = new ArrayList<>();
    private List<String> ratingList = new ArrayList<>();
    private List<String> reviewCountList = new ArrayList<>();

    private List<String> ImageList = new ArrayList<>();

    private RecyclerView recyclerView;
    private MyRecyclerViewAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        for(int i=0;i<20;i++){
            RestaurantList.add("Restaurant: "+String.valueOf(i));
            locationList.add("\uD83D\uDCCC Location: "+String.valueOf(i));
            countryList.add("Country: "+String.valueOf(i));
            ratingList.add(String.valueOf(4));
            reviewCountList.add(String.valueOf(i)+"Reviews");
            ImageList.add("ImageURL"+String.valueOf(i));


        }


        // set up the recycler view
        RecyclerView recyclerView = findViewById(R.id.main_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new MyRecyclerViewAdapter(this,RestaurantList,locationList,countryList,ratingList,reviewCountList,ImageList);
        recyclerView.setAdapter(mAdapter);

    }

//    @Override
//    public void onItemClick(View view, int position){
//        Toast.makeText(this,"You clicked", Toast.LENGTH_LONG).show();
//    }
}
