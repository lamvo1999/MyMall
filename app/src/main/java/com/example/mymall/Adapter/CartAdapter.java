package com.example.mymall.Adapter;

import android.app.Dialog;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mymall.DBsetdata.DBqueries;
import com.example.mymall.Model.CartItemModel;
import com.example.mymall.ProductDetailsActivity;
import com.example.mymall.R;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter {

    private List<CartItemModel> cartItemModelList;
    private int lastPosition = -1;
    private TextView cartTotalAmount ;
    private boolean showDeleteBtn ;

    public CartAdapter(List<CartItemModel> cartItemModelList , TextView cartTotalAmount,boolean showDeleteBtn) {
        this.cartItemModelList = cartItemModelList;
        this.cartTotalAmount = cartTotalAmount ;
        this.showDeleteBtn = showDeleteBtn;
    }



    @Override
    public int getItemViewType(int position) {
        switch (cartItemModelList.get(position).getType()){
            case 0:
                return CartItemModel.CART_ITEM;
            case 1:
                return CartItemModel.TOTAL_AMOUNT;
            default:
                return -1;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        switch (viewType){
            case CartItemModel.CART_ITEM:
                  View cartItemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cart_item_layout, viewGroup, false);
                  return  new CartItemViewHolder(cartItemView);
            case CartItemModel.TOTAL_AMOUNT:
                View cartTotalView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cart_total_amount_layout, viewGroup, false);
                return  new CartTotalAmountViewHolder(cartTotalView);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        switch (cartItemModelList.get(position).getType()){
            case CartItemModel.CART_ITEM:
                String productID = cartItemModelList.get(position).getProductID();
                 String resource = cartItemModelList.get(position).getProductImage();
                 String title = cartItemModelList.get(position).getProductTitle();
                 long freeCoupens = cartItemModelList.get(position).getFreeCoupens();
                 String productPrice = cartItemModelList.get(position).getProductPrice();
                 String cuttedPrice = cartItemModelList.get(position).getCuttedPrice();
                 long offersApplied = cartItemModelList.get(position).getOffersApplied();
                 boolean inStock =cartItemModelList.get(position).isInStock() ;

                ((CartItemViewHolder)viewHolder).setItemDetails(productID,resource,
                        title,freeCoupens,
                        productPrice,cuttedPrice,
                        offersApplied, position,
                        inStock);
                break;
            case CartItemModel.TOTAL_AMOUNT:
                int totalItems = 0;
                int totalItemPrice = 0;
                String deliveryPrice;
                int totalAmout;
                int savedAmount = 0;

                for (int x = 0; x < cartItemModelList.size();x++){

                    if (cartItemModelList.get(x).getType() == CartItemModel.CART_ITEM && cartItemModelList.get(x).isInStock()){
                        totalItems++;
                        totalItemPrice = totalItemPrice + Integer.parseInt(cartItemModelList.get(x).getProductPrice());
                    }

                }
                if (totalItemPrice > 500){
                    deliveryPrice = "FREE";
                    totalAmout = totalItemPrice;
                }else {
                    deliveryPrice = "60";
                    totalAmout = totalItemPrice + 60;
                }

                 ((CartTotalAmountViewHolder)viewHolder).setTotalAmount(totalItems,totalItemPrice,deliveryPrice,totalAmout,savedAmount);
                 break;
            default:
                return;
        }

        if (lastPosition < position) {
            Animation animation = AnimationUtils.loadAnimation(viewHolder.itemView.getContext(), R.anim.fade_in);
            viewHolder.itemView.setAnimation(animation);
            lastPosition = position;
        }
    }

    @Override
    public int getItemCount() {
        return cartItemModelList.size();
    }

    class CartItemViewHolder extends RecyclerView.ViewHolder{

        private ImageView productImage;
        private ImageView freeCoupenIcon;
        private TextView productTitle;
        private TextView freeCoupens;
        private TextView productPrice;
        private TextView cuttedPrice;
        private TextView offersApplied;
        private TextView coupensApplied;
        private TextView productQuantity;
        private LinearLayout coupenRedemptionLayout ;

        private LinearLayout deleteBtn;


        public CartItemViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.product_image);
            productTitle = itemView.findViewById(R.id.product_title);
            freeCoupenIcon = itemView.findViewById(R.id.free_coupen_icon);
            freeCoupens = itemView.findViewById(R.id.tv_free_coupen);
            productPrice = itemView.findViewById(R.id.product_price);
            cuttedPrice = itemView.findViewById(R.id.cutted_price);
            offersApplied = itemView.findViewById(R.id.offers_applied);
            coupensApplied = itemView.findViewById(R.id.coupens_applied);
            productQuantity = itemView.findViewById(R.id.product_quantity);
            coupenRedemptionLayout = itemView.findViewById(R.id.coupen_redemption_layout);

            deleteBtn = itemView.findViewById(R.id.remove_item_btn);
        }

        private void setItemDetails(String productId, String resource,
                                    String title, Long freeCoupensNo,
                                    String productPriceText, String cuttedPriceText,
                                    Long offersAppliedNo, final int position,
                                    boolean inStock){

            Glide.with(itemView.getContext()).load(resource).into(productImage);
            productTitle.setText(title);

            if (inStock) {
                if (freeCoupensNo > 0){
                    freeCoupenIcon.setVisibility(View.VISIBLE);
                    freeCoupens.setVisibility(View.VISIBLE);
                    if (freeCoupensNo == 1){
                        freeCoupens.setText("free" + freeCoupensNo + "Coupen");
                    }else {
                        freeCoupens.setText("free" + freeCoupensNo + "Coupens");
                    }
                }else {
                    freeCoupenIcon.setVisibility(View.INVISIBLE);
                    freeCoupens.setVisibility(View.INVISIBLE);
                }


                productPrice.setText("Rs."+productPriceText+"/-");
                productPrice.setTextColor(Color.parseColor("#000000"));
                cuttedPrice.setText("Rs."+cuttedPriceText+"/-");
                coupenRedemptionLayout.setVisibility(View.VISIBLE);

                productQuantity.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Dialog quantytiDialog = new Dialog(itemView.getContext());
                        quantytiDialog.setContentView(R.layout.quantity_dialog);
                        quantytiDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                        quantytiDialog.setCancelable(false);
                        final EditText quantityNo = quantytiDialog.findViewById(R.id.quantity_no);
                        Button   cancelBtn = quantytiDialog.findViewById(R.id.cancel_btn);
                        Button okBtn = quantytiDialog.findViewById(R.id.ok_btn);

                        cancelBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                quantytiDialog.dismiss();
                            }
                        });

                        okBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                productQuantity.setText("Qty: "+ quantityNo.getText());
                                quantytiDialog.dismiss();
                            }
                        });
                        quantytiDialog.show();
                    }
                });
                if (offersAppliedNo > 0){
                    offersApplied.setVisibility(View.VISIBLE);
                    offersApplied.setText(offersAppliedNo + " Offers applied");
                }else {
                    offersApplied.setVisibility(View.INVISIBLE);
                }

            }else {
                productPrice.setText("out of stock");
                productPrice.setTextColor(itemView.getContext().getResources().getColor(R.color.colorPrimary));
                cuttedPrice.setText(" ");
                coupenRedemptionLayout.setVisibility(View.GONE);
                freeCoupens.setVisibility(View.GONE);//phan duoi
                productQuantity.setVisibility(View.INVISIBLE);
                coupensApplied.setVisibility(View.GONE);
                offersApplied.setVisibility(View.GONE);
                freeCoupenIcon.setVisibility(View.INVISIBLE);
            }

            if (showDeleteBtn) {
                deleteBtn.setVisibility(View.VISIBLE);

            }else {
                deleteBtn.setVisibility(View.GONE);
            }
            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!ProductDetailsActivity.running_cart_query){
                        ProductDetailsActivity.running_cart_query = true;
                        DBqueries.removeFromCart(position, itemView.getContext(),cartTotalAmount);
                    }
                }
            });
        }
    }

    class CartTotalAmountViewHolder extends RecyclerView.ViewHolder{

        private TextView totalItems;
        private TextView totalItemPrice;
        private TextView deliveryPrice;
        private TextView totalAmount;
        private TextView savedAmount;

        public CartTotalAmountViewHolder(@NonNull View itemView) {
            super(itemView);

            totalItems = itemView.findViewById(R.id.total_items);
            totalItemPrice = itemView.findViewById(R.id.total_item_price);
            deliveryPrice = itemView.findViewById(R.id.delivery_price);
            totalAmount = itemView.findViewById(R.id.total_price);
            savedAmount = itemView.findViewById(R.id.saved_amount);
        }
        private void setTotalAmount(int totalItemText, int totalItemPriceText, String deliveryPriceText, int totalAmountText, int savedAmountText){
              totalItems.setText("Price ("+totalItemText+" )");
              totalItemPrice.setText("US$."+totalItemPriceText);
              if (deliveryPriceText.equals("FREE")) {
                  deliveryPrice.setText(deliveryPriceText);
              }else {
                  deliveryPrice.setText("US$."+deliveryPriceText);
              }
              totalAmount.setText("US$."+totalAmountText);
              cartTotalAmount.setText("US$."+totalAmountText);
              savedAmount.setText("You saver US$."+savedAmountText+" on this order.");

              LinearLayout parent = (LinearLayout) cartTotalAmount.getParent().getParent();
              if (totalItemPriceText == 0){
                  DBqueries.cartItemModelList.remove(DBqueries.cartItemModelList.size()-1);
                  parent.setVisibility(View.GONE);
              }else{
                  parent.setVisibility(View.VISIBLE);
              }
        }
    }
}
