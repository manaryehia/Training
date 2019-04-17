package com.code.cars.controller;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.code.cars.R;
import com.code.cars.model.Car;
import com.code.cars.controller.MainActivity;

import java.util.List;

import static com.code.cars.R.*;

public class CarsAdapter extends RecyclerView.Adapter<CarsAdapter.CarsViewHolder> {
    private Context context;

    private List<Car> dataList;
    RequestOptions option;
    private boolean isGrid;
    public static int i;

    //private ArrayList<AuctionInfo>dataList2;

    public CarsAdapter(Context con , List<Car> carList , boolean isGrid) {

        this.dataList = carList;
        option = new RequestOptions().centerCrop();
        this.isGrid = isGrid;
        context = con;
    }


    @NonNull
    @Override
    public CarsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup , int i) {
        this.i=i;
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view;
        if (isGrid)
            view = layoutInflater.inflate(R.layout.grid_card , viewGroup , false);
        else
            view = layoutInflater.inflate(layout.row_card , viewGroup , false);
        return new CarsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CarsViewHolder holder , int i) {
        holder.TvMake.setText(dataList.get(i).getMakeEn() + " " + dataList.get(i).getModelEn() + " " + dataList.get(i).getYear());
        holder.TvPrice.setText(dataList.get(i).auctionInfo.getCurrentPrice() + " " + dataList.get(i).auctionInfo.getCurrencyEn());
        holder.TvLot.setText(String.valueOf(dataList.get(i).auctionInfo.getLot()));
        holder.TvBid.setText(String.valueOf(dataList.get(i).auctionInfo.getBids()));
        String imageUrl = dataList.get(i).getImage();
        String imageUrl2 = "";
        imageUrl2 = imageUrl.replace("[w]" , "200").replace("[h]" , "100");
        //final String title= (dataList.get(i).getMakeEn() + " " + dataList.get(i).getModelEn() + " " + dataList.get(i).getYear());
       final String price=(dataList.get(i).auctionInfo.getCurrentPrice() + " " + dataList.get(i).auctionInfo.getCurrencyEn());
       final String lot=(String.valueOf(dataList.get(i).auctionInfo.getLot()));
       final String bid=(String.valueOf(dataList.get(i).auctionInfo.getBids()));
       final String make=(dataList.get(i).getMakeEn()+" "+dataList.get(i).getModelEn());
       final String year=(String.valueOf(dataList.get(i).getYear()));






        Glide.with(context).load(imageUrl2).apply(option).into(holder.imageView);
//   holder.TvTime.setText(dataList.get(i).auctionInfo.getEndDate());
        final String finalImageUrl = imageUrl2;




        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(v.getContext(),CarInfo.class);

                intent.putExtra("url", finalImageUrl);
                intent.putExtra("make",make);
                intent.putExtra("price",price);
                intent.putExtra("lot",lot);
                intent.putExtra("bid",bid);
                intent.putExtra("year",year);
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) v.getContext() ,holder.imageView, ViewCompat.getTransitionName(holder.imageView));
              context.startActivity(intent,options.toBundle());
            }

        });


    }


    @Override
    public int getItemCount() {

        return dataList.size();
    }

    class CarsViewHolder extends RecyclerView.ViewHolder {
        private TextView TvMake, TvPrice, TvLot, TvBid, TvTime;
        private ImageView imageView;


        private CarsViewHolder(@NonNull View itemView) {
            super(itemView);

            TvMake = itemView.findViewById(id.TV_MakeModel);
            TvPrice = itemView.findViewById(id.TV_Price);
            TvLot = itemView.findViewById(id.TV_lot);
            TvBid = itemView.findViewById(id.TV_bid);
            TvTime = itemView.findViewById(id.TV_timeLeft);
            imageView = itemView.findViewById(id.IV_CarImage);


        }
    }
}