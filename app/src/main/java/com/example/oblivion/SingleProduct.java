package com.example.oblivion;

public class SingleProduct {

    private String img_url;
    private String id;

    public SingleProduct(String img_url,String id) {
        this.img_url = img_url;
        this.id = id;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }
}
