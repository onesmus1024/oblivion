package com.example.oblivion;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

// Java class to access the database
public class DatabaseAccess {
    private FirebaseFirestore fStore;

    public DatabaseAccess() {
        fStore = FirebaseFirestore.getInstance();
    }

    public void writeUserData(String userId, String skin_tone, String allergy) {
        Map<String, Object> userData = new HashMap<>();
        userData.put("skin_tone", skin_tone);
        userData.put("allergy", allergy);
        fStore.collection("users").document(userId).set(userData);
    }

    public void readUserData(String userId, final Callback callback) {
        fStore.collection("users").document(userId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    User user = document.toObject(User.class);
                    callback.onCallback(user);
                }
            }
        });
    }
}

// User class to store the user information
class User {
    public String skin_tone;
    public String allergy;

    public User() {
        // Default constructor required for calls to DocumentSnapshot.toObject(User.class)
    }

    public User(String skin_tone, String allergy) {
        this.skin_tone = skin_tone;
        this.allergy = allergy;
    }
}

// Callback interface to retrieve the user information
interface Callback {
    void onCallback(User user);
}
;
