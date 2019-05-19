package com.example.sher.restaurantproject;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.sher.restaurantproject.Models.NearbyHotels;
import com.example.sher.restaurantproject.Updaters.FetchNearbyHotel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import java.util.ArrayList;
import java.util.List;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class MainActivity extends AppCompatActivity {

    // initializing the list to hold data to be populated in recycler view
    private List<String> RestaurantList = new ArrayList<>();
    private List<String> locationList = new ArrayList<>();
    private List<String> countryList = new ArrayList<>();
    private List<String> ratingList = new ArrayList<>();
    private List<String> reviewCountList = new ArrayList<>();
    private List<String> ImageList = new ArrayList<>();

    private RecyclerView recyclerView;
    private MyRecyclerViewAdapter mAdapter;
    private Context context;
    private FusedLocationProviderClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = MainActivity.this;

        // requesting permission
        requestPermission();

        client = LocationServices.getFusedLocationProviderClient(this);

        // asking for user location
        provideLocation();


        // show a progress dialog to show user to wait, location retrieval is bit slow to work around i put this in handler
        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Please waiting loading the location...");
        progressDialog.show();
        progressDialog.setCancelable(false);
        Handler locationHandler = new Handler();
        locationHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // fetching the data from server
                // Inititating the GET request
                FetchNearbyHotel.fetchNearbyData(MainActivity.this);
                progressDialog.dismiss();
            }
        },7000);





        // putting a handler to wait until data is fetched and loaded into lists
        // volley and retrofit handles this better but due to time shortage I put this one
        Handler  mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {

                // looping through list of model class and filling these details here
                for(int i=0;i<NearbyHotels.getAllNearbyHotels().size();i++){
                    RestaurantList.add(NearbyHotels.getAllNearbyHotels().get(i).getRestaurant_name());
                    locationList.add("\uD83D\uDCCC"+NearbyHotels.getAllNearbyHotels().get(i).getLocation());
                    countryList.add(NearbyHotels.getAllNearbyHotels().get(i).getFood_country());
                    ratingList.add(NearbyHotels.getAllNearbyHotels().get(i).getRating());
                    reviewCountList.add(NearbyHotels.getAllNearbyHotels().get(i).getReview_count());
                    ImageList.add(NearbyHotels.getAllNearbyHotels().get(i).getImage_reference());
                }


                // set up the recycler view and passing the data to adapter
                RecyclerView recyclerView = findViewById(R.id.main_recyclerview);
                DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),new LinearLayoutManager(MainActivity.this).getOrientation());
                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                mAdapter = new MyRecyclerViewAdapter(MainActivity.this,RestaurantList,locationList,countryList,ratingList,reviewCountList,ImageList);
                recyclerView.addItemDecoration(dividerItemDecoration);
                recyclerView.setAdapter(mAdapter);

            }
        },10000);


    }

    // get the location
    private void provideLocation(){
        // just to check whether we have location access or not and ask for permission at runtime
        if (ActivityCompat.checkSelfPermission(MainActivity.this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        // on success of getting location what will happen
        client.getLastLocation().addOnSuccessListener(MainActivity.this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {

                if(location!=null){

                    // getting the latitude and longitude
                    String latLong[] = location.toString().split("\\ ");
                    String locationWhole = latLong[1].toString();
                    String finalLocation[] = locationWhole.split("\\,");

                    Log.d("xxloc1",finalLocation[0]);
                    Log.d("xxloc2",finalLocation[1]);

                   // saving the latitude and longitude in a global static variable for further use by api
                    // this could be done properly but due to time issue this is fastest way came in my mind
                    Constants.latitude = finalLocation[0];
                    Constants.longitude = finalLocation[1];

                } else
                {
                    Toast.makeText(MainActivity.this,"Sorry Location can not be retrevied please re-run the app",Toast.LENGTH_LONG).show();
                }
            }
        });


    }
    // request permission
    private void requestPermission(){
        ActivityCompat.requestPermissions(this,new String[]{ACCESS_FINE_LOCATION},1);
    }


}
