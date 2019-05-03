package com.example.mymall;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mymall.Adapter.MyRewardsAdapter;
import com.example.mymall.Model.RewardsModel;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyRewardsFragment extends Fragment {


    public MyRewardsFragment() {
        // Required empty public constructor
    }

    private RecyclerView rewardsRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_rewards, container, false);

        rewardsRecyclerView = view.findViewById(R.id.my_rewards_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rewardsRecyclerView.setLayoutManager(layoutManager);

        List<RewardsModel> rewardsModelList = new ArrayList<>();
        rewardsModelList.add(new RewardsModel("Cashback","till 2nd,June 2019", "GET 20% OFF on any product above Rs.500/- and below Rs.2500/-"));
        rewardsModelList.add(new RewardsModel("Cashback","till 2nd,June 2019", "GET 20% OFF on any product above Rs.500/- and below Rs.2500/-"));
        rewardsModelList.add(new RewardsModel("Buy 1 get 1","till 2nd,June 2019", "GET 20% OFF on any product above Rs.500/- and below Rs.2500/-"));
        rewardsModelList.add(new RewardsModel("Cashback","till 2nd,June 2019", "GET 20% OFF on any product above Rs.500/- and below Rs.2500/-"));
        rewardsModelList.add(new RewardsModel("Buy 1 get 1","till 2nd,June 2019", "GET 20% OFF on any product above Rs.500/- and below Rs.2500/-"));
        rewardsModelList.add(new RewardsModel("Cashback","till 2nd,June 2019", "GET 20% OFF on any product above Rs.500/- and below Rs.2500/-"));
        rewardsModelList.add(new RewardsModel("Cashback","till 2nd,June 2019", "GET 20% OFF on any product above Rs.500/- and below Rs.2500/-"));
        rewardsModelList.add(new RewardsModel("Cashback","till 2nd,June 2019", "GET 20% OFF on any product above Rs.500/- and below Rs.2500/-"));
        rewardsModelList.add(new RewardsModel("Cashback","till 2nd,June 2019", "GET 20% OFF on any product above Rs.500/- and below Rs.2500/-"));
        rewardsModelList.add(new RewardsModel("Cashback","till 2nd,June 2019", "GET 20% OFF on any product above Rs.500/- and below Rs.2500/-"));

        MyRewardsAdapter myRewardsAdapter = new MyRewardsAdapter(rewardsModelList, false);
        rewardsRecyclerView.setAdapter(myRewardsAdapter);
        myRewardsAdapter.notifyDataSetChanged();


        return view;
    }

}
