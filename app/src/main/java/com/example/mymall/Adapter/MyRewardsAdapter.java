package com.example.mymall.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mymall.Model.RewardsModel;
import com.example.mymall.ProductDetailsActivity;
import com.example.mymall.R;

import java.util.List;

public class MyRewardsAdapter extends RecyclerView.Adapter<MyRewardsAdapter.ViewHolder> {


    private List<RewardsModel> rewardsModelList;
    private Boolean useMiniLayout  =false;

    public MyRewardsAdapter(List<RewardsModel> rewardsModelList, Boolean useMiniLayout) {
        this.rewardsModelList = rewardsModelList;
        this.useMiniLayout = useMiniLayout;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        if (useMiniLayout){
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.mini_reward_item_layout, viewGroup, false);
        }else {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rewards_item_layout, viewGroup, false);
        }
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        String title = rewardsModelList.get(position).getTitle();
        String date = rewardsModelList.get(position).getExpiryDate();
        String body = rewardsModelList.get(position).getCoupenBody();
        viewHolder.setData(title,date,body);
    }

    @Override
    public int getItemCount() {
        return rewardsModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView coupenTitle;
        private TextView coupenExpiryDate;
        private TextView coupenBody;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            coupenTitle = itemView.findViewById(R.id.coupen_title);
            coupenExpiryDate = itemView.findViewById(R.id.coupen_validity);
            coupenBody = itemView.findViewById(R.id.coupen_body);
        }
        private void setData(final String title, final String date, final String body){
            coupenTitle.setText(title);
            coupenExpiryDate.setText(date);
            coupenBody.setText(body);

            if (useMiniLayout){
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ProductDetailsActivity.coupenTitle.setText(title);
                        ProductDetailsActivity.coupenExpiryDate.setText(date);
                        ProductDetailsActivity.coupenBody.setText(body);
                        ProductDetailsActivity.showDialogRecyclerView();
                    }
                });
            }
        }
    }
}
