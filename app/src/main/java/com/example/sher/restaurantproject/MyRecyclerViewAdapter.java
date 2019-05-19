package com.example.sher.restaurantproject;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import java.util.List;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder> {

    private List<String> restaurant_name;
    private List<String> location;
    private List<String> country_names;
    private List<String> ratingBar_number;
    private List<String> review_count;
    private List<String> image_names;
    private Context context;



    private LayoutInflater mInflater;
    private AdapterView.OnItemClickListener mClickListener;


    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView template_restaurantText;
        public TextView template_locationText;
        public TextView template_restaurant_country;
        public RatingBar template_ratingBar;
        public TextView template_reviewsCount;
        public ImageView templateImageView;





        public MyViewHolder(View view){
            super(view);

            template_restaurantText = (TextView) view.findViewById(R.id.template_restaurant_name);
            template_locationText = (TextView) view.findViewById(R.id.template_location_text);
            template_restaurant_country = (TextView) view.findViewById(R.id.template_restaurant_country);
            template_ratingBar = (RatingBar) view.findViewById(R.id.template_star_review);
            template_reviewsCount = (TextView) view.findViewById(R.id.template_review_count);
            templateImageView = (ImageView) view.findViewById(R.id.template_image);

        }

    }


    // data is passed into constructor
    public MyRecyclerViewAdapter(Context context,List<String> restaurant_name,List<String> location_name,List<String> restaurant_country,List<String> rating_count, List<String> reviews_count,List<String> image_name){
        this.mInflater = LayoutInflater.from(context);
        this.restaurant_name = restaurant_name;
        this.location = location_name;
        this.country_names = restaurant_country;
        this.ratingBar_number = rating_count;
        this.review_count = reviews_count;
        this.image_names = image_name;
        //this.mData = data;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view =  mInflater.inflate(R.layout.recyclerview_row,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position){



        float rating = Float.parseFloat(ratingBar_number.get(position));
        int ratingInt = (int) rating;
        String location_country[] = location.get(position).split("\\ ");
        Log.d("location",location_country[0]);
        Log.d("location", location_country[1]);

        holder.template_restaurantText.setText(restaurant_name.get(position));
        holder.template_locationText.setText(location_country[1]);
        holder.template_restaurant_country.setText(country_names.get(position));
        holder.template_ratingBar.setNumStars(ratingInt);
        holder.template_reviewsCount.setText(review_count.get(position)+" reviews");
       // holder.templateImageView.setImageResource(R.drawable.restaurant_pic);

        String imageUrl = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference="+image_names.get(position)+"&key=AIzaSyDEDjQ3XTOk1dJS5P6afTnr3BCPjB6acJs";
       // String imageUrl = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference=CnRtAAAATLZNl354RwP_9UKbQ_5Psy40texXePv4oAlgP4qNEkdIrkyse7rPXYGd9D_Uj1rVsQdWT4oRz4QrYAJNpFX7rzqqMlZw2h2E2y5IKMUZ7ouD_SlcHxYq1yL4KbKUv3qtWgTK0A6QbGh87GB3sscrHRIQiG2RrmU_jF4tENr9wGS_YxoUSSDrYjWmrNfeEHSGSc3FyhNLlBU&key=AIzaSyDnpKd40AWisgA2xDNtdN1-qzC-CszkYjk";
        Glide.with(context).load(imageUrl).apply(new RequestOptions().override(300, 175)).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                // if failed to load a pic automatically then load an image from drawable
                holder.templateImageView.setImageResource(R.drawable.restaurant_pic);
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
               // if loaded properly it comes here
                return false;
            }
        }).into(holder.templateImageView);

       // String textViewDetail =  mData.get(position);
        //holder.rowText.setText(textViewDetail);
    }

    @Override
    public int getItemCount(){

        return location.size();
    }

}
