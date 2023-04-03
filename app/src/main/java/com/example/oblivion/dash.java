package com.example.oblivion;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Objects;

public class dash extends AppCompatActivity {

    private FirebaseFirestore fStore;
    private FirebaseUser currentUser;

    private String skinConcern;
    private String allergy;
    private String skinSensitivity;
    private String skinTone;
    private String skinType;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash);

        fStore = FirebaseFirestore.getInstance();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser != null) {
            String userEmail = currentUser.getEmail();

            // Fetch the skinConcern, allergy, skinSensitivity, skinTone, skinType fields from forms collection
            fStore.collection("forms")
                    .whereEqualTo("email", userEmail)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (DocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                                    skinConcern = document.getString("skinConcern");
                                    allergy = document.getString("allergy");
                                    skinSensitivity = document.getString("skinSensitivity");
                                    skinTone = document.getString("skinTone");
                                    skinType = document.getString("skinType");

                                    // Use the fetched values to create the user profile
                                    createUserProfile();
                                }
                            } else {
                                Log.d("UserProfileActivity", "Error getting documents: ", task.getException());
                            }
                        }
                    });
        }
    }

    private void createUserProfile() {
        // Create the user profile object with the fetched values
        UserProfile userProfile = new UserProfile(currentUser.getEmail(), skinConcern, allergy, skinSensitivity, skinTone, skinType);

        // Store the user profile object in the Firestore database
        fStore.collection("users")
                .document(currentUser.getUid())
                .set(userProfile)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d("dash", "User profile created successfully");
                        } else {
                            Log.d("dash", "Error creating user profile: ", task.getException());
                        }
                    }
                });
    }
}
