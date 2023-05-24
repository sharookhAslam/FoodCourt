package com.example.foodcourt;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import java.util.concurrent.TimeUnit;

public class Otp_send extends AppCompatActivity {
    Button btnsendotp;
    EditText phoneNum;
    ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otp_varification);

        btnsendotp = findViewById(R.id.button6);
        phoneNum = findViewById(R.id.editTextPhone2);
          progressBar = findViewById(R.id.progressBar2);


        btnsendotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if(!phoneNum.getText().toString().trim().isEmpty()) {
                    if((phoneNum.getText().toString().trim()).length() == 10){

                        progressBar.setVisibility(View.VISIBLE);
                        btnsendotp.setVisibility(View.INVISIBLE);

                        PhoneAuthProvider.verifyPhoneNumber(
                                PhoneAuthOptions
                                        .newBuilder(FirebaseAuth.getInstance())
                                        .setActivity(Otp_send.this)
                                        .setPhoneNumber("+92"+phoneNum.getText().toString())
                                        .setTimeout(60L, TimeUnit.SECONDS)
                                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                            @Override
                                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                                progressBar.setVisibility(View.INVISIBLE);
                                                btnsendotp.setVisibility(View.VISIBLE);
                                            }

                                            @Override
                                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                                progressBar.setVisibility(View.INVISIBLE);
                                                btnsendotp.setVisibility(View.VISIBLE);
                                                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                                            }

                                            @Override
                                            public void onCodeSent(@NonNull String backendotp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                                progressBar.setVisibility(View.INVISIBLE);
                                                btnsendotp.setVisibility(View.VISIBLE);

                                                Intent i = new Intent(Otp_send.this, OTP_recieve.class);
                                                i.putExtra(phoneNum.getText().toString(), "mobile");
                                                i.putExtra("backendotp", backendotp);
                                                startActivity(i);
                                                          }
                                                      }

                                        )
                                        .build());
                }else {
                        Toast.makeText(getApplicationContext(), "Please Enter a valid Phone number", Toast.LENGTH_LONG).show();

                    }
                    }

                    else {

                    Toast.makeText(getApplicationContext(), "Please Enter a Phone number", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}