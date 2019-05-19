package com.example.sher.restaurantproject.Updaters;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.sher.restaurantproject.Models.NearbyHotels;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;


public class FetchNearbyHotel {
    private static String baseurl = "https://maps.googleapis.com/maps/api/place/textsearch/json?query=5+star+restaurants&location=41.3851,2.1734&radius=1000&key=AIzaSyDnpKd40AWisgA2xDNtdN1-qzC-CszkYjk";
    private static String url;
    private static  ProgressDialog progressDialog;


    public static boolean fetchNearbyData(Context context) {
        url = baseurl;
        progressDialog = new ProgressDialog(context);
        new FetchNearbyHotel.RollingNumberRequester().execute();
        return false;
    }

    private static class RollingNumberRequester extends AsyncTask<Void,  Void, Void>
    {

        protected void onPreExecute() {
            super.onPreExecute();
            Log.d("response","first1");
            //progressDialog.setMessage("Please wait while we fetch nearby places..");
           // progressDialog.show();

        }

        protected Void doInBackground(Void... params) {
            Log.d("response","background work started to fetch data");
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(url).build();
            Response response = null;
            try {
                Log.d("response","first stage");
                response = client.newCall(request).execute();
                JSONObject reader = new JSONObject(response.body().string());
                JSONArray resultArray= reader.getJSONArray("results");


                StringBuilder formattedJSON = new StringBuilder();
                formattedJSON.append("[");

                for(int i=0;i<resultArray.length();i++){
                    if(i==5)
                        break;
                    JSONObject resultObject = resultArray.getJSONObject(i);

                    String name = resultObject.getString("name");
                    String Address = resultObject.getJSONObject("plus_code").getString("compound_code");
                    String rating = resultObject.getString("rating");
                    String reviews = resultObject.getString("user_ratings_total");
                    String photoReference = resultObject.getJSONArray("photos").getJSONObject(0).getString("photo_reference");
                    String country = "country";

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
          //  progressDialog.dismiss();

        }
    }
}

