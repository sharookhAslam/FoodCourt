package com.example.foodcourt;

import android.content.Context;
import android.content.SharedPreferences;

public class User
{
    Context context;
    SharedPreferences sharedPreferences;


    public String getOrder_id() {
        order_id = sharedPreferences.getString("order_id", "");
        return order_id;
    }

    public void setOrder_id(String order_id) {

        this.order_id = order_id;
        sharedPreferences.edit().putString("order_id", order_id).commit();
    }

    String order_id;
    public String getF_id() {
        f_id = sharedPreferences.getString("f_id", "");
        return f_id;
    }

    public void setF_id(String f_id) {
        this.f_id = f_id;
        sharedPreferences.edit().putString("f_id", f_id).commit();
    }

    String f_id;

    public String getMall_id() {
        mall_id = sharedPreferences.getString("mall_id", "");
        return mall_id;
    }

    public void setMall_id(String mall_id) {
        this.mall_id = mall_id;
        sharedPreferences.edit().putString("mall_id", mall_id).commit();
    }

    String mall_id;
    public String getUser_Price() {
        user_Price = sharedPreferences.getString("user_price", "");
        return user_Price;
    }

    public void setUser_Price(String user_Price) {
        this.user_Price = user_Price;
        sharedPreferences.edit().putString("user_price", user_Price).commit();
    }

    String user_Price;

    public String getUser_id() {
        user_id = sharedPreferences.getString("user_id", "");
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
        sharedPreferences.edit().putString("user_id", user_id).commit();
    }

    String user_id;

    public void removeUser(){

        sharedPreferences.edit().clear().commit();
    }

    public String getName() {

        name = sharedPreferences.getString("userdata", "");
        return name;
    }

    public void setName(String name) {
        this.name = name;
        sharedPreferences.edit().putString("userdata", name).commit();
    }

    private String name;

    public String getUserrole() {
        userrole = sharedPreferences.getString("userrole", "");
        return userrole;
    }


    public void setUserrole(String userrole) {
        this.userrole = userrole;
        sharedPreferences.edit().putString("userrole", userrole).commit();
    }

    private String userrole;

public User(Context context){
    this.context = context;
    sharedPreferences = context.getSharedPreferences("userinfo", Context.MODE_PRIVATE);


}

}
