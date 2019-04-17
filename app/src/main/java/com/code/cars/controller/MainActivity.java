package com.code.cars.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.code.cars.R;
import com.code.cars.model.Car;
import com.code.cars.model.CarsOnline;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    ProgressBar progress;
    public SwipeRefreshLayout swipeRefreshLayout;
    private CarsAdapter adapter;
    public boolean isGrid = false;
    public LinearLayoutManager linearLayoutManager;
    public GridLayoutManager gridLayoutManager;
    MenuItem item;
    Menu menu;



    List<Car> carList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        initViews();

        //swipeRefreshLayout.setEnabled(false);

//        final TextView textView;
        // textView = findViewById(R.id.tv_test);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.emiratesauction.com/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CarsApi carsApi = retrofit.create(CarsApi.class);
        Call<CarsOnline> call = carsApi.getCarsInfo();
        call.enqueue(new Callback<CarsOnline>() {
            @Override
            public void onResponse(Call<CarsOnline> call , Response<CarsOnline> response) {

                CarsOnline carsOnlines = response.body();
                carList = carsOnlines.getCars();

//                for (Car cars : carsOnlines.getCars()) {
////                    String content;
////                    content = String.valueOf(carsOnline.getCars());
//                    textView.append(cars.getMakeEn() + "\n");
//                }
//                textView.append(carsOnlines.toString());
                progress.setVisibility(View.GONE);
                generateCarList();
            }

            @Override
            public void onFailure(Call<CarsOnline> call , Throwable t) {
                Toast.makeText(MainActivity.this , "Something went wrong...Please try later!" , Toast.LENGTH_SHORT).show();

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        getMenuInflater().inflate(R.menu.main_activity_menu , menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        this.item = item;
        int id = item.getItemId();
        if (id == (R.id.btn_grid)) {
            if (isGrid) {
                gridLayoutManager = new GridLayoutManager(MainActivity.this , 2);
                recyclerView.setLayoutManager(gridLayoutManager);
                adapter = new CarsAdapter(MainActivity.this , carList , true);
                recyclerView.setAdapter(adapter);
                isGrid = false;
                item.setIcon(R.drawable.list);
            } else {

                linearLayoutManager = new LinearLayoutManager(MainActivity.this);
                recyclerView.setLayoutManager(linearLayoutManager);
                adapter = new CarsAdapter(MainActivity.this , carList , false);
                recyclerView.setAdapter(adapter);
                isGrid = true;

                item.setIcon(R.drawable.grid2);
            }
        }
        if (item.isChecked()) {
            item.setChecked(false);
        } else {
            item.setChecked(true);

        }
        return super.onOptionsItemSelected(item);
    }


    private void initViews() {
        recyclerView = findViewById(R.id.Rv_RecyclerView);
        progress = findViewById(R.id.progress);
    }


    private void generateCarList() {

        linearLayoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new CarsAdapter(MainActivity.this , carList , false);
        recyclerView.setAdapter(adapter);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                swipeRefreshLayout.setRefreshing(false);
                initViews();
                //if(!isGrid)
                //item.setIcon(R.drawable.grid2);
                // else
                generateCarList();
                if (!isGrid) {
                    gridLayoutManager = new GridLayoutManager(MainActivity.this , 2);
                    recyclerView.setLayoutManager(gridLayoutManager);
                    adapter = new CarsAdapter(MainActivity.this , carList , true);
                    recyclerView.setAdapter(adapter);
                    isGrid = false;
                    item.setIcon(R.drawable.list);
                } else {
                    linearLayoutManager = new LinearLayoutManager(MainActivity.this);
                    recyclerView.setLayoutManager(linearLayoutManager);
                    adapter = new CarsAdapter(MainActivity.this , carList , false);
                    recyclerView.setAdapter(adapter);
                    isGrid = true;

                    item.setIcon(R.drawable.grid2);
                }
//swipeRefreshLayout.setEnabled(true);
            }
        });


    }


}



