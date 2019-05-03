package com.example.mymall.Model;

public class SliderModel {

    private String banner;
    private String backgroungColor;

    public SliderModel(String banner, String backgroungColor) {
        this.banner = banner;
        this.backgroungColor = backgroungColor;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public String getBackgroungColor() {
        return backgroungColor;
    }

    public void setBackgroungColor(String backgroungColor) {
        this.backgroungColor = backgroungColor;
    }
}
