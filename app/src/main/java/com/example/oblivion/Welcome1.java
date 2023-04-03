package com.example.oblivion;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Timer;
import java.util.TimerTask;

public class Welcome1 extends AppCompatActivity {
    TextInputEditText editTextEmail, editTextPassword,editTextName,editTextUsername;

    FirebaseAuth mAuth;

    Timer timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome1);
        timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intent=new Intent(Welcome1.this, MainActivity5.class);
                startActivity(intent);
                finish();

            }
        }, 5000);
    }
}