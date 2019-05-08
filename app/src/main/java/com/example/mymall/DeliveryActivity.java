package com.example.mymall;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.mymall.Adapter.CartAdapter;
import com.example.mymall.DBsetdata.DBqueries;
import com.example.mymall.Model.CartItemModel;

import java.util.ArrayList;
import java.util.List;

public class DeliveryActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView deliveryRecyclerview;
    private Button changeOrAddNewAddressBtn;
    public static final int SELECT_ADDRESS = 0;
    private TextView totalAmount ;
    private TextView fullname;
    private TextView fullAddress;
    private TextView pincode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Delivery");

        deliveryRecyclerview = findViewById(R.id.delivery_ryceclerview);
        changeOrAddNewAddressBtn = findViewById(R.id.change_or_address_btn);
        totalAmount = findViewById(R.id.total_cart_amount);

        fullname =(TextView) findViewById(R.id.fullname);
        fullAddress =(TextView) findViewById(R.id.address);
        pincode =(TextView) findViewById(R.id.pincode);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        deliveryRecyclerview.setLayoutManager(layoutManager);

        CartAdapter cartAdapter = new CartAdapter(DBqueries.cartItemModelList,totalAmount,false);
        deliveryRecyclerview.setAdapter(cartAdapter);
        cartAdapter.notifyDataSetChanged();

        changeOrAddNewAddressBtn.setVisibility(View.VISIBLE);
        changeOrAddNewAddressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myAddressesIntent = new Intent(DeliveryActivity.this, MyAddressesActivity.class);
                myAddressesIntent.putExtra("MODE", SELECT_ADDRESS);
                startActivity(myAddressesIntent);
            }
        });



    }

    @Override
    protected void onStart() {
        super.onStart();
        fullname.setText(DBqueries.addressesModelList.get(DBqueries.selectedAddress).getFullnamr());
        fullAddress.setText(DBqueries.addressesModelList.get(DBqueries.selectedAddress).getAddress());
        pincode.setText(DBqueries.addressesModelList.get(DBqueries.selectedAddress).getPincode());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
