package com.example.foodcourt;


import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class Splash extends AppCompatActivity {
 ImageView iMg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);


        new Handler().postDelayed(new Runnable() {
            User user = new User(Splash.this);
            @Override
            public void run() {


                    if(user.getUserrole().equals("admin")) {
                              Intent i = new Intent(Splash.this, admin_panel.class);
                              i.putExtra("email", user.getName());
                              startActivity(i);
                              finish();
                          }
                    else if(user.getUserrole().equals("mall")) {
                        Intent i = new Intent(Splash.this, Sh_malls.class);
                        i.putExtra("email", user.getName());
                        startActivity(i);
                        finish();
                    }
                    else if(user.getUserrole().equals("customer")) {
                        Intent i = new Intent(Splash.this, User_Profile.class);
                        i.putExtra("email", user.getName());
                        startActivity(i);
                        finish();
                    }
                    else if(user.getUserrole().equals("food stall")) {
                        Intent i = new Intent(Splash.this, Fc_profile.class);
                        i.putExtra("email", user.getName());
                        startActivity(i);
                        finish();
                    }


                else {

                    // This method will be executed once the timer is over
                    Intent i = new Intent(Splash.this, Trial_login.class);
                    startActivity(i);
                    finish();

                }

                    /*
                    if(user.getUserrole() == "shopping mall"){

                        Intent i = new Intent(Splash.this, Sh_malls.class);
                        i.putExtra("userrole", user.getName());
                        startActivity(i);
                        finish();
                    }
    */

            }
        }, 5000);
    }
}
