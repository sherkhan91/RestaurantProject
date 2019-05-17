package com.example.sher.restaurantproject;

import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        // getting the action bar and putton an icon on actionbar
//        ActionBar actionBar =  getSupportActionBar();
//        actionBar.setDisplayShowHomeEnabled(true);
//        actionBar.setIcon(R.mipmap.ic_launcher);
//
//
//        TextView actionbarTitle = new TextView(getApplicationContext());
//        ViewGroup.LayoutParams lp = new RelativeLayout.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.WRAP_CONTENT
//        );
//
//        actionbarTitle.setLayoutParams(lp);
//        actionbarTitle.setText(actionBar.getTitle());
//        actionbarTitle.setTextColor(Color.BLACK);
//        actionbarTitle.setGravity(Gravity.CENTER);
//        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
//
//        actionBar.setCustomView(actionbarTitle);

    }
}
