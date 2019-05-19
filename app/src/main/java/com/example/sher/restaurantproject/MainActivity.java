package com.example.sher.restaurantproject;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.sher.restaurantproject.Models.NearbyHotels;
import com.example.sher.restaurantproject.Updaters.FetchNearbyHotel;

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


        // fetching the data from server
        FetchNearbyHotel.fetchNearbyData(this);


        Handler  mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {

                for(int i=0;i<3;i++){
                    RestaurantList.add(NearbyHotels.getAllNearbyHotels().get(i).getRestaurant_name());
                    locationList.add("\uD83D\uDCCC"+NearbyHotels.getAllNearbyHotels().get(i).getLocation());
                    countryList.add(NearbyHotels.getAllNearbyHotels().get(i).getFood_country());
                    ratingList.add(NearbyHotels.getAllNearbyHotels().get(i).getRating());
                    reviewCountList.add(NearbyHotels.getAllNearbyHotels().get(i).getReview_count());
                    ImageList.add(NearbyHotels.getAllNearbyHotels().get(i).getImage_reference());
                }


                String rt_name = NearbyHotels.getAllNearbyHotels().get(0).getRestaurant_name();
                Log.d("mainActivity",rt_name);


                // set up the recycler view
                RecyclerView recyclerView = findViewById(R.id.main_recyclerview);
                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                mAdapter = new MyRecyclerViewAdapter(MainActivity.this,RestaurantList,locationList,countryList,ratingList,reviewCountList,ImageList);
                recyclerView.setAdapter(mAdapter);


            }
        },5000);




//        for(int i=0;i<NearbyHotels.getAllNearbyHotels().size();i++){
//            RestaurantList.add("Restaurant: "+String.valueOf(i));
//            locationList.add("\uD83D\uDCCC Location: "+String.valueOf(i));
//            countryList.add("Country: "+String.valueOf(i));
//            ratingList.add(String.valueOf(4));
//            reviewCountList.add(String.valueOf(i)+"Reviews");
//            ImageList.add("ImageURL"+String.valueOf(i));
//        }






    }

//    @Override
//    public void onItemClick(View view, int position){
//        Toast.makeText(this,"You clicked", Toast.LENGTH_LONG).show();
//    }
}
