package com.example.sher.restaurantproject.Models;
import com.example.sher.restaurantproject.Models.NearbyHotelModel;
import com.google.gson.Gson;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class NearbyHotels {

    private static List<NearbyHotelModel> nearbyHotels = new LinkedList<>();

    public static void getNearbyHotelsFromJson(String nearbyHotelsJSON){
        Gson gson = new Gson();
        nearbyHotels = Arrays.asList(gson.fromJson(nearbyHotelsJSON, NearbyHotelModel[].class));
    }

    public static List<NearbyHotelModel> getAllNearbyHotels(){
        return nearbyHotels;
    }
}
