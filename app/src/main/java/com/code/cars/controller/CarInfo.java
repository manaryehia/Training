package com.code.cars.controller;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.transition.Explode;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.code.cars.R;

public class CarInfo extends AppCompatActivity {
    TextView TvPrice,TvLot,TvBid,TvYear;
    CollapsingToolbarLayout toolbar;

    RequestOptions option;
    ImageView imageView;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.info_car);

        Bundle bundle = getIntent().getExtras();
        String url = bundle.getString("url");
        String make = bundle.getString("make");
        String price = bundle.getString("price");
        String lot = bundle.getString("lot");
        String bid = bundle.getString("bid");
        String year = bundle.getString("year");



        toolbar=findViewById(R.id.collapsingToolBar);
        toolbar.setCollapsedTitleTextColor(getColor(R.color.colorAccent));
toolbar.setExpandedTitleColor(getColor(R.color.colorAccent));


       TvBid=findViewById(R.id.info_bid);
       TvLot=findViewById(R.id.info_lot);
       TvPrice=findViewById(R.id.info_price);
       TvYear=findViewById(R.id.info_year);
       imageView=findViewById(R.id.carImage);
        option = new RequestOptions().centerCrop();

        TvPrice.setText(price);
        TvYear.setText(year);
        TvLot.setText(lot);
        TvBid.setText(bid);
        toolbar.setTitle(make);
        Glide.with(CarInfo.this).load(url).apply(option).into(imageView);




    }
}
