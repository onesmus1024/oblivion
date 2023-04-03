package com.example.oblivion;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.Objects;

public class Search extends AppCompatActivity {
    private Button emlaButton;
    private Button betterButton;
    private FirebaseFirestore db;
    private String currentUserEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = FirebaseFirestore.getInstance();

        emlaButton = findViewById(R.id.emla_button);
        betterButton = findViewById(R.id.better_button);

        // Get the current user's email from Firebase Authentication
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            currentUserEmail = user.getEmail();
        } else {
            // Handle errors
        }

        // Query the forms collection in Firestore for the user's email and skin tones
        db.collection("forms")
                .whereEqualTo("email", currentUserEmail)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                            String skinTones = document.getString("skin_tones");
                            String allergy = document.getString("allergy");

                            if ("fair".equals(skinTones) && "fragrance".equals(allergy)) {
                                // If skin tones are fair and allergy is fragrance, set Emla button
                                emlaButton.setText("Emla");
                                emlaButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent(Search.this, Notice.class);
                                        startActivity(intent);
                                    }
                                });
                            } else if ("fair".equals(skinTones) && "metals".equals(allergy)) {
                                // If skin tones are fair and allergy is metals, set Better button
                                betterButton.setText("Better");
                                betterButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent(Search.this, Notice.class);
                                        startActivity(intent);
                                    }
                                });
                            }
                        }
                    } else {
                        // Handle errors
                    }
                });
    }
}
