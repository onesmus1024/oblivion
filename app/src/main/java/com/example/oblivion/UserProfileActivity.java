package com.example.oblivion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.DocumentSnapshot;

public class UserProfileActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseFirestore fStore;
    private Button button;

    private TextView tv_skin_concern;
    private TextView tv_allergy;
    private TextView tv_skin_sensitivity;
    private TextView tv_skin_tone;
    private TextView tv_skin_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        auth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        button = findViewById(R.id.button21);

        tv_skin_concern = findViewById(R.id.tv_skin_concern);
        tv_allergy = findViewById(R.id.tv_allergy);
        tv_skin_sensitivity = findViewById(R.id.tv_skin_sensitivity);
        tv_skin_tone = findViewById(R.id.tv_skin_tone);
        tv_skin_type = findViewById(R.id.tv_skin_type);

        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser != null) {
            String currentEmail = currentUser.getEmail();
            getUserProfile(currentEmail);
        }

        Button myButton = findViewById(R.id.button21);
        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), MainActivity6.class);
                startActivity(intent);
                finish();
            }
        });
    }



    void getUserProfile(String userEmail) {
        fStore.collection("forms")
                .whereEqualTo("email", userEmail)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && !task.getResult().isEmpty()) {
                        DocumentSnapshot document = task.getResult().getDocuments().get(0);
                        tv_skin_concern.setText("Skin Concern: " + document.getString("skin_concern"));
                        tv_allergy.setText("Allergy: " + document.getString("allergy"));
                        tv_skin_sensitivity.setText("Skin Sensitivity: " + document.getString("skin_sensitivity"));
                        tv_skin_tone.setText("Skin Tone: " + document.getString("skin_tones"));
                        tv_skin_type.setText("Skin Type: " + document.getString("skin_type"));
                    }
                });

        fStore.collection("users")
                .whereEqualTo("email", userEmail)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && !task.getResult().isEmpty()) {
                        DocumentSnapshot document = task.getResult().getDocuments().get(0);
                        // Add any additional user information you want to display here
                    }
                });
        ;



    }
}