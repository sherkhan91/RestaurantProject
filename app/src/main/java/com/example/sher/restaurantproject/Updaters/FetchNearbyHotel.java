package com.example.sher.restaurantproject.Updaters;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.sher.restaurantproject.Constants;
import com.example.sher.restaurantproject.Models.NearbyHotels;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;


public class FetchNearbyHotel {
    private static String baseurl = "https://maps.googleapis.com/maps/api/place/textsearch/json?query=5+star+hotels&location=";
    private static String url;
    private static  ProgressDialog progressDialog;


    public static boolean fetchNearbyData(Context context) {

        // if there is no location retrieved Default co-ordinates for Dubai has been put
        if(Constants.latitude==null)
            Constants.latitude = "25.2048";
        if(Constants.longitude==null)
            Constants.longitude = "55.2708";
        url = baseurl+Constants.latitude+","+Constants.longitude+"&radius=1000&key=AIzaSyDnpKd40AWisgA2xDNtdN1-qzC-CszkYjk";
        progressDialog = new ProgressDialog(context);
        new FetchNearbyHotel.RollingNumberRequester().execute();
        return false;
    }

    private static class RollingNumberRequester extends AsyncTask<Void,  Void, Void>
    {

        protected void onPreExecute() {
            super.onPreExecute();
            Log.d("response","In preExecutre");
            progressDialog.setMessage("Please wait while we fetch nearby places..");
            progressDialog.show();
            progressDialog.setCancelable(false);

        }

        protected Void doInBackground(Void... params) {
            Log.d("response","background work started to fetch data");
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(url).build();
            Log.d("url",url);
            Response response = null;
            try {
                Log.d("response","first stage");
                response = client.newCall(request).execute();
                JSONObject reader = new JSONObject(response.body().string());
                JSONArray resultArray= reader.getJSONArray("results");


                StringBuilder formattedJSON = new StringBuilder();
                formattedJSON.append("[");


                // manually crafting a json file to fit the model class
                for(int i=0;i<resultArray.length();i++){
                    if(i==5)
                        break;
                    JSONObject resultObject = resultArray.getJSONObject(i);

                    String name = resultObject.getString("name");
                    String Address = resultObject.getJSONObject("plus_code").getString("compound_code");
                    String rating = resultObject.getString("rating");
                    String reviews = resultObject.getString("user_ratings_total");
                    String photoReference;
                    try{
                         photoReference = resultObject.getJSONArray("photos").getJSONObject(0).getString("photo_reference");

                    } catch (JSONException e){
                        photoReference = "CmRaAAAA8hFLpaXRtiy-2IcgpdcxaNtueuJfX3uyHxKNctuCCjIqJvZWlPHetnZSzQ4oFxJ79mGEHD4Bdf-lD0e8ebPhbFETONre3oP5WuAT8jWZCTHR6JUHuAGdSV2giQf3HwR3EhBmxHPeL3_ZIvaqb_0EtMMRGhQ1rq8rxvFGvO1UEjDOv9BfazTMDA";

                    }

                    String country = "food type";

                    formattedJSON.append("{");
                    formattedJSON.append("\"restaurant_name\":"+"\""+name+"\",");
                    formattedJSON.append("\"location\":"+"\""+Address+"\",");
                    formattedJSON.append("\"food_country\":"+"\""+country+"\",");
                    formattedJSON.append("\"rating\":"+"\""+rating+"\",");
                    formattedJSON.append("\"review_count\":"+"\""+reviews+"\",");
                    formattedJSON.append("\"image_reference\":"+"\""+photoReference+"\"");
                    formattedJSON.append("}");
                    if(i!=4)
                        formattedJSON.append(",");

                    Log.d("response name:",name);
                    Log.d("response address:",Address);
                    // restaurant type fill something dummy
                    Log.d("response rating:",rating);
                    Log.d("response reviews:",reviews);
                    Log.d("response photo:",photoReference);
                }


                formattedJSON.append("]");
                Log.d("response formatted:", formattedJSON.toString());
                NearbyHotels.getNearbyHotelsFromJson(formattedJSON.toString());





            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            progressDialog.dismiss();

        }
    }
}

