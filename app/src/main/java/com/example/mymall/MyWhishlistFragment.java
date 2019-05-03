package com.example.mymall;


import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.mymall.Adapter.WhishlistAdapter;
import com.example.mymall.DBsetdata.DBqueries;
import com.example.mymall.Model.WhishlistModel;
import com.example.mymall.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyWhishlistFragment extends Fragment {


    public MyWhishlistFragment() {
        // Required empty public constructor
    }

    private RecyclerView wishlishRecyclerView;
    private Dialog loadingDialog;
    public static WhishlistAdapter whishlistAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_my_whishlist, container, false);

        ///////// loading dialog

        loadingDialog = new Dialog(getContext());
        loadingDialog.setContentView(R.layout.loading_progress_dialog);
        loadingDialog.setCancelable(true);
        loadingDialog.getWindow().setBackgroundDrawable(getContext().getDrawable(R.drawable.slider_background));
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        loadingDialog.show();
        //////////loading dialog


        wishlishRecyclerView = view.findViewById(R.id.my_whishlist_recyclerview);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        wishlishRecyclerView.setLayoutManager(linearLayoutManager);

        if (DBqueries.wishlistModeList.size() == 0){
            DBqueries.wishList.clear();
            DBqueries.loadWishList(getContext(), loadingDialog, true);
        }else {
            loadingDialog.dismiss();
        }


         whishlistAdapter = new WhishlistAdapter(DBqueries.wishlistModeList, true);
         wishlishRecyclerView.setAdapter(whishlistAdapter);
         whishlistAdapter.notifyDataSetChanged();


        return view;
    }

}
