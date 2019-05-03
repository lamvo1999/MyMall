package com.example.mymall.Model;

import java.util.List;

public class HomePageModel {

    public static final int BANNER_SLIDER = 0;
    public static final int STRIP_AD_BANNER = 1;
    public  static final int HORIZONTAL_PRODUCT_VIEW = 2;
    public  static final int GRID_PRODUCT_VIEW = 3;

    private int type;
    private String backgroundColor;


    ///////// Banner Slider
    private List<SliderModel> sliderModelList;
    public HomePageModel(int type, List<SliderModel> sliderModelList) {
        this.type = type;
        this.sliderModelList = sliderModelList;
    }
    public int getType() {
        return type;
    }
    public void setType(int type) {
        this.type = type;
    }
    public List<SliderModel> getSliderModelList() {
        return sliderModelList;
    }
    public void setSliderModelList(List<SliderModel> sliderModelList) {
        this.sliderModelList = sliderModelList;
    }
    ///////// Banner Slider

    /////// Strip Ad
    private String resource;


    public HomePageModel(int type, String resource, String backgroundColor) {
        this.type = type;
        this.resource = resource;
        this.backgroundColor = backgroundColor;
    }
    public String getResource() {
        return resource;
    }
    public void setResource(String resource) {
        this.resource = resource;
    }
    public String getBackgroundColor() {
        return backgroundColor;
    }
    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    /////// Strip Ad

    private String title;
    private List<HorizontalProductScrollModel> horizontalProductScrollModels;

    ////////Horizontal Product Layout

    private List<WhishlistModel> viewAllProductList;

    public HomePageModel(int type, String title,String backgroundColor, List<HorizontalProductScrollModel> horizontalProductScrollModels, List<WhishlistModel> viewAllProductList) {
        this.type = type;
        this.title = title;
        this.backgroundColor = backgroundColor;
        this.horizontalProductScrollModels = horizontalProductScrollModels;
        this.viewAllProductList = viewAllProductList;
    }

    public List<WhishlistModel> getViewAllProductList() {
        return viewAllProductList;
    }

    public void setViewAllProductList(List<WhishlistModel> viewAllProductList) {
        this.viewAllProductList = viewAllProductList;
    }

    ////////Horizontal Product Layout

    ////////Grid Product Layout

    public HomePageModel(int type, String title, String backgroundColor, List<HorizontalProductScrollModel> horizontalProductScrollModels) {
        this.type = type;
        this.title = title;
        this.backgroundColor = backgroundColor;
        this.horizontalProductScrollModels = horizontalProductScrollModels;
    }

    ////////Grid Product Layout
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public List<HorizontalProductScrollModel> getHorizontalProductScrollModels() {
        return horizontalProductScrollModels;
    }
    public void setHorizontalProductScrollModels(List<HorizontalProductScrollModel> horizontalProductScrollModels) {
        this.horizontalProductScrollModels = horizontalProductScrollModels;
    }



}
