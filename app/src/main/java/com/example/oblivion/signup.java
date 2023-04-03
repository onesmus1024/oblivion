package com.example.oblivion;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class  signup<EmailPasswordActivity> extends AppCompatActivity {
    public static final String TAG = "TAG";
    TextInputEditText editTextEmail, editTextPassword,editTextName,editTextUsername;
    Button buttonButton;
    FirebaseAuth mAuth;
    FirebaseFirestore fStore;
    String userID;

    @Override
    public void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent=new Intent(getApplicationContext(), tutorial.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mAuth =FirebaseAuth.getInstance();
        editTextEmail=findViewById(R.id.email);
        editTextName=findViewById(R.id.name);
        editTextPassword=findViewById(R.id.password);
        editTextUsername=findViewById(R.id.username);
        buttonButton=findViewById(R.id.button);

        buttonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email, password, username,name;
                email = String.valueOf(editTextEmail.getText());
                password= String.valueOf(editTextPassword.getText());
                username=  String.valueOf(editTextUsername.getText());
                name=  String.valueOf(editTextName.getText());
                mAuth= FirebaseAuth.getInstance();
                fStore=FirebaseFirestore.getInstance();

                if(TextUtils.isEmpty(email)){
                    Toast.makeText(signup.this, "Enter Email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    Toast.makeText(signup.this, "Enter Password", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(username)){
                    Toast.makeText(signup.this, "Enter Username", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(name)){
                    Toast.makeText(signup.this, "Enter Name", Toast.LENGTH_SHORT).show();
                    return;
                }
                mAuth.createUserWithEmailAndPassword ( email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (task.isSuccessful()) {

                                    Toast.makeText(signup.this, "Account created.",
                                            Toast.LENGTH_SHORT).show();
                                    userID= mAuth.getCurrentUser().getUid();
                                    DocumentReference documentReference=fStore.collection("users").document(userID);
                                    Map<String,Object> user = new HashMap<>();
                                    user.put("name",name);
                                    user.put("email",email);
                                    user.put("username",username);
                                    documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Log.d(TAG, "onSuccess: user"+ userID);
                                        }
                                    });

                                    Intent intent=new Intent(getApplicationContext(), tutorial.class);
                                    startActivity(intent);
                                    finish();

                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(signup.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();

                            }
                        }

                        });
            }
        });
    }
}