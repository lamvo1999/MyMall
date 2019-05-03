package com.example.mymall;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mymall.Adapter.MyOrderAdapter;
import com.example.mymall.Model.MyOrderItemModel;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyOrdersFragment extends Fragment {


    public MyOrdersFragment() {
        // Required empty public constructor
    }

    private RecyclerView myOrdersRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_orders, container, false);

        myOrdersRecyclerView = view.findViewById(R.id.my_orders_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        myOrdersRecyclerView.setLayoutManager(layoutManager);

        List<MyOrderItemModel> myOrderItemModelList = new ArrayList<>();
        myOrderItemModelList.add(new MyOrderItemModel(R.drawable.product_image, 2,"Pixel 2XL (BLACK", "Delivered on Mon,15th JAN 2018"));
        myOrderItemModelList.add(new MyOrderItemModel(R.mipmap.image2, 1,"Pixel 2XL (BLACK", "Delivered on Mon,15th JAN 2018"));
        myOrderItemModelList.add(new MyOrderItemModel(R.drawable.product_image, 0,"Pixel 2XL (BLACK", "Cancelled"));
        myOrderItemModelList.add(new MyOrderItemModel(R.mipmap.image2, 4,"Pixel 2XL (BLACK", "Delivered on Mon,15th JAN 2018"));


        MyOrderAdapter myOrderAdapter = new MyOrderAdapter(myOrderItemModelList);
        myOrdersRecyclerView.setAdapter(myOrderAdapter);
        myOrderAdapter.notifyDataSetChanged();

        return view;
    }

}
