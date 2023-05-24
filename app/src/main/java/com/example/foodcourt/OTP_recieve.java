package com.example.foodcourt;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

public class OTP_recieve extends AppCompatActivity {

    Button otpVar;
    EditText ed1, ed2, ed3, ed4, ed5, ed6;
    TextView textView;
    String getotpbackend;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    setContentView(R.layout.otp_recieve);
   Intent num = getIntent();
   String number =  num.getStringExtra("mobile");
   textView.setText(String.format("+92-%s", number));
   getotpbackend = getIntent().getStringExtra("backendotp");


    otpVar = findViewById(R.id.button4);
        ed1 = findViewById(R.id.editTextTextPersonName12);
        ed2 = findViewById(R.id.editTextTextPersonName13);
        ed3 = findViewById(R.id.editTextTextPersonName14);
        ed4 = findViewById(R.id.editTextTextPersonName15);
        ed5 = findViewById(R.id.editTextTextPersonName16);
        ed6 = findViewById(R.id.editTextTextPersonName17);
      textView = findViewById(R.id.textView7);
        ProgressBar progressBar = findViewById(R.id.progressBar);


   otpVar.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View v) {
           if(!ed1.getText().toString().trim().isEmpty() &&
                   !ed2.getText().toString().trim().isEmpty() &&
                   !ed3.getText().toString().trim().isEmpty() &&
                   !ed4.getText().toString().trim().isEmpty() &&
                           !ed5.getText().toString().trim().isEmpty()
                           && !ed6.getText().toString().trim().isEmpty()){



               String enterotp = ed1.getText().toString() +
                       ed2.getText().toString() +
                       ed3.getText().toString() +
                       ed4.getText().toString() +
                       ed5.getText().toString() +
                       ed6.getText().toString();
   if(getotpbackend!=null){

              progressBar.setVisibility(View.VISIBLE);
              otpVar.setVisibility(View.INVISIBLE);
       PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(
               getotpbackend, enterotp

       );

       FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
           @Override
           public void onComplete(@NonNull Task<AuthResult> task) {
               progressBar.setVisibility(View.INVISIBLE);
               otpVar.setVisibility(View.VISIBLE);
               if(task.isSuccessful()){

                   Intent login = new Intent(OTP_recieve.this, Trial_login.class);
                   login.setFlags(login.FLAG_ACTIVITY_NEW_TASK|login.FLAG_ACTIVITY_CLEAR_TASK);
                   startActivity(login);
               }else {

                   Toast.makeText(getApplicationContext(), "Enter Correct OTP", Toast.LENGTH_LONG).show();
               }
           }
       });

               }else{

       Toast.makeText(getApplicationContext(), "please check you internet connection", Toast.LENGTH_LONG).show();
   }

               Toast.makeText(getApplicationContext(), "OTP Varify", Toast.LENGTH_LONG).show();

           }else {

               Toast.makeText(getApplicationContext(), "Please Enter All numbers", Toast.LENGTH_LONG).show();

           }


       }
   });

   numbertomove();
    }

    private void numbertomove() {

        ed1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            if(!s.toString().trim().isEmpty()){
                ed2.requestFocus();

            }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        ed2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(!s.toString().trim().isEmpty()){
                    ed3.requestFocus();

                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        ed3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(!s.toString().trim().isEmpty()){
                    ed4.requestFocus();

                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        ed4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(!s.toString().trim().isEmpty()){
                    ed4.requestFocus();

                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        ed5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(!s.toString().trim().isEmpty()){
                    ed6.requestFocus();

                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
