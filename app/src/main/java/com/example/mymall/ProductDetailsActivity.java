package com.example.mymall;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mymall.Adapter.MyRewardsAdapter;
import com.example.mymall.Adapter.ProductDetailsAdapter;
import com.example.mymall.Adapter.ProductImagesAdapter;
import com.example.mymall.DBsetdata.DBqueries;
import com.example.mymall.Model.ProductSpecificationModel;
import com.example.mymall.Model.RewardsModel;
import com.example.mymall.Model.WhishlistModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.mymall.MainActivity.showCart;
import static com.example.mymall.RegisterActivity.setSignUpFragment;

public class ProductDetailsActivity extends AppCompatActivity {

    public static boolean running_wishlist_query = false;

   private ViewPager productImagesViewPaper;
   private TextView productTitle;
   private TextView averageRatingMiniView;
   private TextView totalRatingMiniView;
   private TextView productPrice;
   private TextView cuttedPrice;
   private ImageView codIndicator;
   private TextView tvCodIndicator;
   protected TabLayout viewpaperIndicator;

   private LinearLayout coupenRedmeptionLayout;
   private Button coupenRedeemBtn;

   private TextView rewardTitle;
   private TextView rewardBody;

   ////////product description
   private ConstraintLayout productDetailsOnlyContainer;
   private ConstraintLayout productDetailsTabsContainer;
   private TabLayout productDetailsTabLayout;
   private ViewPager productDetailsViewPager;
   private TextView productOnlyDescriptionBody;

   private List<ProductSpecificationModel> productSpecificationModelList = new ArrayList<>();
   private String productDescription;
   private String productOtherDetails;
    ////////product description

   public static boolean ALREADY_ADDED_TO_WISHLIST = false;
   public static FloatingActionButton addToWhishListBtn;

    ///////// coupen Dialog
    public static TextView coupenTitle;
    public static TextView coupenExpiryDate;
    public static TextView coupenBody;
    private static RecyclerView coupensRecyclerview;
    private static LinearLayout selectedCoupen;
    ///////// coupen Dialog

    private Dialog signInDiaLog;
    public static String productID;
    private Dialog loadingDialog;
    private DocumentSnapshot documentSnapshot;

   private FirebaseFirestore firebaseFirestore;
   private FirebaseUser currentUser;

   /////////// rating layout
    public static LinearLayout rateNowContainer;
    private TextView totalRatings;
    private LinearLayout ratingsNoContainer;
    private TextView totalRatingsFigure;
    private LinearLayout ratingProgressbarContainer;
    private TextView averageRating;
   /////////// rating layout

    private Button buyNowBtn;
    private LinearLayout addToCartBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        productImagesViewPaper = findViewById(R.id.product_images_viewpaper);
        viewpaperIndicator = findViewById(R.id.viewpaper_indicator);
        addToWhishListBtn = findViewById(R.id.add_to_whishlist_btn);
        productDetailsViewPager =  findViewById(R.id.product_details_viewpager);
        productDetailsTabLayout = findViewById(R.id.product_details_tab_layout);
        buyNowBtn = findViewById(R.id.buy_now_btn);
        coupenRedeemBtn =findViewById(R.id.coupen_redemption_btn);
        productTitle = findViewById(R.id.product_title);
        averageRatingMiniView = findViewById(R.id.tv_product_rating_miniview);
        totalRatingMiniView = findViewById(R.id.total_rating_miniview);
        productPrice = findViewById(R.id.product_price);
        cuttedPrice = findViewById(R.id.cuttes_price);
        tvCodIndicator = findViewById(R.id.tv_cod_indicator);
        codIndicator = findViewById(R.id.cod_indicator_imageview);
        rewardTitle = findViewById(R.id.reward_title);
        rewardBody = findViewById(R.id.reward_body);
        productDetailsTabsContainer = findViewById(R.id.product_details_tab_container);
        productDetailsOnlyContainer = findViewById(R.id.product_details_container);
        productOnlyDescriptionBody = findViewById(R.id.product_detail_body);
        totalRatings = findViewById(R.id.total_ratings);
        ratingsNoContainer = findViewById(R.id.ratings_number_container);
        totalRatingsFigure = findViewById(R.id.total_ratings_figure);
        ratingProgressbarContainer = findViewById(R.id.ratings_progressbar_container);
        averageRating =findViewById(R.id.average_rating);
        addToCartBtn = findViewById(R.id.add_to_cart_btn);
        coupenRedmeptionLayout = findViewById(R.id.coupen_redemption_layout);

        ///////// loading dialog

        loadingDialog = new Dialog(ProductDetailsActivity.this);
        loadingDialog.setContentView(R.layout.loading_progress_dialog);
        loadingDialog.setCancelable(true);
        loadingDialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.slider_background));
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        loadingDialog.show();
        //////////loading dialog


        firebaseFirestore = FirebaseFirestore.getInstance();

        final List<String> productImages = new ArrayList<>();
        productID = getIntent().getStringExtra("PRODUCT_ID");
         firebaseFirestore.collection("PRODUCTS").document(productID)
                 .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
             @Override
             public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                  if (task.isSuccessful()){

                      DocumentSnapshot documentSnapshot = task.getResult();

                      for (long x =1; x < (long)documentSnapshot.get("no_of_product_images") +1; x++){
                          productImages.add(documentSnapshot.get("product_image_"+x).toString());
                      }
                      ProductImagesAdapter productImagesAdapter = new ProductImagesAdapter(productImages);
                      productImagesViewPaper.setAdapter(productImagesAdapter);

                      productTitle.setText(documentSnapshot.get("product_title").toString());
                      averageRatingMiniView.setText(documentSnapshot.get("average_rating").toString());
                      totalRatingMiniView.setText("("+(long)documentSnapshot.get("total_ratings")+")ratings");
                      productPrice.setText("US$"+documentSnapshot.get("product_price").toString());
                      cuttedPrice.setText("US$"+documentSnapshot.get("cutted_price").toString());
                      if ((boolean)documentSnapshot.get("COD")){
                          codIndicator.setVisibility(View.VISIBLE);
                          tvCodIndicator.setVisibility(View.VISIBLE);
                      }else {
                          codIndicator.setVisibility(View.INVISIBLE);
                          tvCodIndicator.setVisibility(View.INVISIBLE);
                      }
                      rewardTitle.setText((long)documentSnapshot.get("free_coupens") + documentSnapshot.get("free_coupen_title").toString());
                      rewardBody.setText(documentSnapshot.get("free_coupen_body").toString());

                      if ((boolean)documentSnapshot.get("use_tab_layout")){
                          productDetailsTabsContainer.setVisibility(View.VISIBLE);
                          productDetailsOnlyContainer.setVisibility(View.GONE);
                          productDescription = documentSnapshot.get("product_description").toString();

                          productOtherDetails = documentSnapshot.get("product_other_details").toString();

                          for (long x = 1; x < (long)documentSnapshot.get("total_spec_titles") +1; x++){
                              productSpecificationModelList.add(new ProductSpecificationModel(0,documentSnapshot.get("spec_title_"+x).toString()));
                              for (long y = 1;y < (long)documentSnapshot.get("spec_title_"+x+"_total_fileds")+ 1; y++){
                                  productSpecificationModelList.add(new ProductSpecificationModel(1,documentSnapshot.get("spec_title_"+x+"_filed_"+y+"_name").toString()
                                          ,documentSnapshot.get("spec_title_"+x+"_filed_"+y+"_value").toString()));
                              }
                          }

                      }else {
                          productDetailsTabsContainer.setVisibility(View.GONE);
                          productDetailsOnlyContainer.setVisibility(View.VISIBLE);
                          productOnlyDescriptionBody.setText(documentSnapshot.get("product_description").toString());
                      }

                          totalRatings.setText((long)documentSnapshot.get("total_ratings")+"ratings");

                           for (int x =0; x<5 ;x++){
                               TextView rating = (TextView) ratingsNoContainer.getChildAt(x);
                               rating.setText(String.valueOf((long)documentSnapshot.get((5-x)+"_star")));

                               ProgressBar progressBar = (ProgressBar) ratingProgressbarContainer.getChildAt(x);
                               int maxProgress = Integer.parseInt(String.valueOf((long)documentSnapshot.get("total_ratings")));
                               progressBar.setMax(maxProgress);
                               progressBar.setProgress(Integer.parseInt(String.valueOf((long)documentSnapshot.get((5-x)+"_star"))));
                           }
                           totalRatingsFigure.setText(String.valueOf((long)documentSnapshot.get("total_ratings")));
                           averageRating.setText(documentSnapshot.get("average_rating").toString());
                           productDetailsViewPager.setAdapter(new ProductDetailsAdapter(getSupportFragmentManager(),productDetailsTabLayout.getTabCount(),productDescription, productOtherDetails, productSpecificationModelList));

                           if (currentUser != null) {
                               if (DBqueries.myRating.size() == 0){
                                   DBqueries.loadRaingList(ProductDetailsActivity.this);
                               }
                               if (DBqueries.wishList.size() == 0) {
                                   DBqueries.loadWishList(ProductDetailsActivity.this, loadingDialog, false);
                                   DBqueries.loadRaingList(ProductDetailsActivity.this);
                               } else {
                                   loadingDialog.dismiss();
                               }
                           }else {
                               loadingDialog.dismiss();
                           }
                           if (DBqueries.wishList.contains(productID)){
                               ALREADY_ADDED_TO_WISHLIST = true;
                               addToWhishListBtn.setSupportImageTintList(getResources().getColorStateList(R.color.colorPrimary));
                           }else {
                               addToWhishListBtn.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#9e9e9e")));
                               ALREADY_ADDED_TO_WISHLIST = false;
                           }

                  }else {
                      loadingDialog.dismiss();
                      String error = task.getException().toString();
                      Toast.makeText(ProductDetailsActivity.this, "ERROR :"+error, Toast.LENGTH_SHORT).show();
                  }
             }
         });


        viewpaperIndicator.setupWithViewPager(productImagesViewPaper, true);

        addToWhishListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentUser == null){
                    signInDiaLog.show();
                }else {
                    if (!running_wishlist_query) {
                        running_wishlist_query = true;
                        if (ALREADY_ADDED_TO_WISHLIST) {
                            int index = DBqueries.wishList.indexOf(productID);
                            DBqueries.removeFromWishlist(index, ProductDetailsActivity.this);
                            addToWhishListBtn.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#9e9e9e")));
                        } else {
                            addToWhishListBtn.setSupportImageTintList(getResources().getColorStateList(R.color.colorPrimary));
                            Map<String, Object> addProduct = new HashMap<>();
                            addProduct.put("product_ID_" + String.valueOf(DBqueries.wishList.size()), productID);

                            firebaseFirestore.collection("USERS").document(currentUser.getUid()).collection("USER_DATA").document("MY_WISHLIST")
                                    .update(addProduct).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {

                                        Map<String, Object> updateListSize = new HashMap<>();
                                        updateListSize.put("list_size", (long) (DBqueries.wishList.size() + 1));

                                        firebaseFirestore.collection("USERS").document(currentUser.getUid()).collection("USER_DATA").document("MY_WISHLIST")
                                                .update(updateListSize).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {

                                                    if (DBqueries.wishlistModeList.size() != 0) {
                                                        DBqueries.wishlistModeList.add(new WhishlistModel(productID, documentSnapshot.get("product_image_1").toString()
                                                                , documentSnapshot.get("product_title_").toString()
                                                                , (long) documentSnapshot.get("free_coupens")
                                                                , documentSnapshot.get("average_rating_").toString()
                                                                , (long) documentSnapshot.get("total_ratings")
                                                                , documentSnapshot.get("product_price").toString()
                                                                , documentSnapshot.get("cutted_price").toString()
                                                                , (boolean) documentSnapshot.get("COD")));
                                                    }

                                                    ALREADY_ADDED_TO_WISHLIST = true;
                                                    addToWhishListBtn.setSupportImageTintList(getResources().getColorStateList(R.color.colorPrimary));
                                                    DBqueries.wishList.add(productID);
                                                    Toast.makeText(ProductDetailsActivity.this, "Added to wishlist successfully", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    addToWhishListBtn.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("9e9e9e")));
                                                    String error = task.getException().toString();
                                                    Toast.makeText(ProductDetailsActivity.this, "ERROR :" + error, Toast.LENGTH_SHORT).show();
                                                }
                                                running_wishlist_query = false;
                                            }
                                        });

                                    } else {
                                        running_wishlist_query = false;
                                        String error = task.getException().toString();
                                        Toast.makeText(ProductDetailsActivity.this, "ERROR :" + error, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                        }
                    }
                }
            }
        });



        productDetailsViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(productDetailsTabLayout));
        productDetailsTabLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                productDetailsViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


      /////////rating Layout
        rateNowContainer = findViewById(R.id.rate_now_container);
        for (int x =0; x< rateNowContainer.getChildCount();x++){
            final int startPosition = x;
            rateNowContainer.getChildAt(x).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (currentUser == null) {
                        signInDiaLog.show();
                    }else {
                        setRating(startPosition);
                    }
                }
            });
        }
     /////////rating Layout

        buyNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentUser == null) {
                    signInDiaLog.show();
                }else {
                    Intent deliveryIntent = new Intent(ProductDetailsActivity.this, DeliveryActivity.class);
                    startActivity(deliveryIntent);
                }
            }
        });

        addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentUser == null) {
                    signInDiaLog.show();
                }else {
                   ////////todo: add to cart
                }
            }
        });

    /////////// coupen dialog
        final Dialog checkCoupenPriceDialo = new Dialog(ProductDetailsActivity.this);
        checkCoupenPriceDialo.setContentView(R.layout.coupen_redeem_dialog);
        checkCoupenPriceDialo.setCancelable(true);
        checkCoupenPriceDialo.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        ImageView toggleRecyclerView = checkCoupenPriceDialo.findViewById(R.id.toggle_recyclerview);
        coupensRecyclerview = checkCoupenPriceDialo.findViewById(R.id.coupens_recyclerview);
        selectedCoupen = checkCoupenPriceDialo.findViewById(R.id.selected_coupen);

        coupenTitle = checkCoupenPriceDialo.findViewById(R.id.coupen_title);
        coupenExpiryDate = checkCoupenPriceDialo.findViewById(R.id.coupen_validity);
        coupenBody = checkCoupenPriceDialo.findViewById(R.id.coupen_body);

        TextView originalPrice = checkCoupenPriceDialo.findViewById(R.id.original_price);
        TextView discountedPrice = checkCoupenPriceDialo.findViewById(R.id.discounted_price);

        LinearLayoutManager layoutManager = new LinearLayoutManager(ProductDetailsActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        coupensRecyclerview.setLayoutManager(layoutManager);

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


        MyRewardsAdapter myRewardsAdapter = new MyRewardsAdapter(rewardsModelList, true);
        coupensRecyclerview.setAdapter(myRewardsAdapter);
        myRewardsAdapter.notifyDataSetChanged();


        toggleRecyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogRecyclerView();
            }
        });
        ////////// coupen dialog

        coupenRedeemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkCoupenPriceDialo.show();

            }
        });

        ///////// signIn dialog

        signInDiaLog = new Dialog(ProductDetailsActivity.this);
        signInDiaLog.setContentView(R.layout.sign_in_dialog);
        signInDiaLog.setCancelable(true);
        signInDiaLog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);

        Button dialogSignInBtn = signInDiaLog.findViewById(R.id.sign_in_btn);
        Button dialogSingUpBtn = signInDiaLog.findViewById(R.id.sign_up_btn);
        final Intent registerIntent = new Intent(ProductDetailsActivity.this, RegisterActivity.class);

        dialogSignInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignInFragment.disableCloseBtn = true;
                SignUpFragment.disableCloseBtn = true;
                signInDiaLog.dismiss();
                setSignUpFragment = false;
                startActivity(registerIntent);
            }
        });

        dialogSingUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignInFragment.disableCloseBtn = true;
                SignUpFragment.disableCloseBtn = true;
                signInDiaLog.dismiss();
                setSignUpFragment = true;
                startActivity(registerIntent);
            }
        });

        //////// signIn dialog


    }

    @Override
    protected void onStart() {
        super.onStart();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser  == null){
            coupenRedmeptionLayout.setVisibility(View.GONE);
        }else {
            coupenRedmeptionLayout.setVisibility(View.VISIBLE);
        }
        if (currentUser != null) {
            if (DBqueries.myRating.size() == 0){
                DBqueries.loadRaingList(ProductDetailsActivity.this);
            }
            if (DBqueries.wishList.size() == 0) {
                DBqueries.loadWishList(ProductDetailsActivity.this, loadingDialog, false);
                DBqueries.loadRaingList(ProductDetailsActivity.this);
            } else {
                loadingDialog.dismiss();
            }
        }else {
            loadingDialog.dismiss();
        }
        if (DBqueries.wishList.contains(productID)){
            ALREADY_ADDED_TO_WISHLIST = true;
            addToWhishListBtn.setSupportImageTintList(getResources().getColorStateList(R.color.colorPrimary));
        }else {
            ALREADY_ADDED_TO_WISHLIST = false;
        }

    }

    public static void  showDialogRecyclerView(){
        if (coupensRecyclerview.getVisibility() == View.GONE){
            coupensRecyclerview.setVisibility(View.VISIBLE);
            selectedCoupen.setVisibility(View.GONE);
        }else {
            coupensRecyclerview.setVisibility(View.GONE);
            selectedCoupen.setVisibility(View.VISIBLE);
        }
    }

    public static void setRating(int startPosition) {
        for (int x =0; x < rateNowContainer.getChildCount(); x++){
            ImageView startBtn = (ImageView)rateNowContainer.getChildAt(x);
            startBtn.setImageTintList(ColorStateList.valueOf(Color.parseColor("#bebebe")));
            if (x <= startPosition){
                startBtn.setImageTintList(ColorStateList.valueOf(Color.parseColor("#ffbb00")));
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_and_cart_icon, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            finish();
            return true;
        }else if (id == R.id.main_search_icon){
            return true;
        }else if (id == R.id.main_cart_icon) {
            if (currentUser == null) {
                signInDiaLog.show();
            } else {
                Intent cartIntent = new Intent(ProductDetailsActivity.this, MainActivity.class);
                showCart = true;
                startActivity(cartIntent);
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }

}
