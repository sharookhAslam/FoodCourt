package com.example.foodcourt;

public class ALL_Malls {


    private String id;
    private String name;
    private String imageurl;


    public ALL_Malls() {
    }


    public ALL_Malls(String id, String imageurl, String name) {
        this.id = id;
        this.imageurl = imageurl;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.id = name;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }


}



