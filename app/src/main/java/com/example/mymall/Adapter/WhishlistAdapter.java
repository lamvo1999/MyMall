package com.example.mymall.Adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.mymall.DBsetdata.DBqueries;
import com.example.mymall.Model.WhishlistModel;
import com.example.mymall.ProductDetailsActivity;
import com.example.mymall.R;

import java.util.List;

public class WhishlistAdapter extends RecyclerView.Adapter<WhishlistAdapter.ViewHolder> {


    private List<WhishlistModel> whishlistModelList;
    private Boolean wishlist;
    private int lastPosition = -1;

    public WhishlistAdapter(List<WhishlistModel> whishlistModelList, Boolean wishlist) {
        this.whishlistModelList = whishlistModelList;
        this.wishlist = wishlist;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.wishlist_item_layout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
       String productId = whishlistModelList.get(position).getProductId();
        String resource = whishlistModelList.get(position).getProductImage();
        String title = whishlistModelList.get(position).getProductTitle();
        long freeCoupens = whishlistModelList.get(position).getFreeCoupens();
        String rating = whishlistModelList.get(position).getRating();
        long totalRatings = whishlistModelList.get(position).getTotalRatings();
        String productPrice = whishlistModelList.get(position).getProductPrice();
        String cuttedPrice = whishlistModelList.get(position).getCuttedPrice();
        boolean COD = whishlistModelList.get(position).isCOD();
        viewHolder.setData(productId,resource,title,freeCoupens,rating,totalRatings,productPrice,cuttedPrice,COD, position);

        if (lastPosition < position) {
            Animation animation = AnimationUtils.loadAnimation(viewHolder.itemView.getContext(), R.anim.fade_in);
            viewHolder.itemView.setAnimation(animation);
            lastPosition = position;
        }

    }

    @Override
    public int getItemCount() {
        return whishlistModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{


        private ImageView productImage;
        private TextView productTitle;
        private TextView freeCoupens;
        private ImageView coupenIcon;
        private TextView rating;
        private TextView totalRatings;
        private View priceCut;
        private TextView productPrice;
        private TextView cuttedPrice;
        private TextView paymentMethod;
        private ImageView deleteBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.product_image);
            productTitle = itemView.findViewById(R.id.product_title);
            freeCoupens = itemView.findViewById(R.id.free_coupen);
            coupenIcon = itemView.findViewById(R.id.coupen_icon);
            rating = itemView.findViewById(R.id.tv_product_rating_miniview);
            totalRatings = itemView.findViewById(R.id.total_ratings);
            priceCut = itemView.findViewById(R.id.price_cut);
            productPrice = itemView.findViewById(R.id.product_price);
            cuttedPrice = itemView.findViewById(R.id.cutted_price);
            paymentMethod = itemView.findViewById(R.id.payment_mrethod);
            deleteBtn = itemView.findViewById(R.id.delete_btn);

        }
        private void setData(final String productId, String resource, String title, long freeCoupensNo, String averageRate, long totalRatingsNo, String price, String cuttedPriceValue, boolean COD, final int index){
            Glide.with(itemView.getContext()).load(resource).into(productImage);
            productTitle.setText(title);
            if (freeCoupensNo != 0){
                coupenIcon.setVisibility(View.VISIBLE);
                if (freeCoupensNo == 1) {
                    freeCoupens.setText("free " + freeCoupensNo + " coupen");
                }else {
                    freeCoupens.setText("free " + freeCoupensNo + " coupens");
                }
            }else {
                coupenIcon.setVisibility(View.INVISIBLE);
                freeCoupens.setVisibility(View.INVISIBLE);
            }
            rating.setText(averageRate);
            totalRatings.setText("("+totalRatingsNo+")" +"ratings");
            productPrice.setText("US$"+price);
            cuttedPrice.setText("US$"+cuttedPriceValue);
            if (COD){
                paymentMethod.setVisibility(View.VISIBLE);
            }else {
               paymentMethod.setVisibility(View.INVISIBLE);
            }

            if (wishlist){
                deleteBtn.setVisibility(View.VISIBLE);
            }else {
                deleteBtn.setVisibility(View.GONE);
            }

            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!ProductDetailsActivity.running_wishlist_query) {
                        ProductDetailsActivity.running_wishlist_query = true;
                        DBqueries.removeFromWishlist(index, itemView.getContext());
                    }
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent productDetailsIntent = new Intent(itemView.getContext(), ProductDetailsActivity.class);
                    productDetailsIntent.putExtra("PRODUCT_ID",productId);
                    itemView.getContext().startActivity(productDetailsIntent);
                }
            });
        }
    }
}
