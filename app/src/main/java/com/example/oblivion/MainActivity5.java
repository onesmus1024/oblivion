package com.example.oblivion;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.oblivion.Profile;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;

public class MainActivity5 extends AppCompatActivity {

    private EditText emailEditText;
    private Spinner skinTonesSpinner, allergySpinner, skinTypeSpinner, skinSensitivitySpinner, skinConcernSpinner;
    private Button submitButton;

    private FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);

        emailEditText = findViewById(R.id.emailEditText);
        skinTonesSpinner = findViewById(R.id.skinTonesSpinner);
        allergySpinner = findViewById(R.id.allergySpinner);
        skinTypeSpinner = findViewById(R.id.skinTypeSpinner);
        skinSensitivitySpinner = findViewById(R.id.skinSensitivitySpinner);
        skinConcernSpinner = findViewById(R.id.skinConcernSpinner);
        submitButton = findViewById(R.id.submitButton);

       fStore = FirebaseFirestore.getInstance();

        ArrayAdapter<CharSequence> skinTonesAdapter = ArrayAdapter.createFromResource(this, R.array.skin_tones_values, android.R.layout.simple_spinner_item);
        skinTonesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        skinTonesSpinner.setAdapter(skinTonesAdapter);

        ArrayAdapter<CharSequence> allergyAdapter = ArrayAdapter.createFromResource(this, R.array.allergy_values, android.R.layout.simple_spinner_item);
        allergyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        allergySpinner.setAdapter(allergyAdapter);

        ArrayAdapter<CharSequence> skinTypeAdapter = ArrayAdapter.createFromResource(this, R.array.skin_type_values, android.R.layout.simple_spinner_item);
        skinTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        skinTypeSpinner.setAdapter(skinTypeAdapter);

        ArrayAdapter<CharSequence> skinSensitivityAdapter = ArrayAdapter.createFromResource(this, R.array.skin_sensitivity_values, android.R.layout.simple_spinner_item);
        skinSensitivityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        skinSensitivitySpinner.setAdapter(skinSensitivityAdapter);

        ArrayAdapter<CharSequence> skinConcernAdapter = ArrayAdapter.createFromResource(this, R.array.skin_concern_values, android.R.layout.simple_spinner_item);
        skinConcernAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        skinConcernSpinner.setAdapter(skinConcernAdapter);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString().trim();
                String skinTones = skinTonesSpinner.getSelectedItem().toString();
                String allergy = allergySpinner.getSelectedItem().toString();
                String skinType = skinTypeSpinner.getSelectedItem().toString();
                String skinSensitivity = skinSensitivitySpinner.getSelectedItem().toString();
                String skinConcern = skinConcernSpinner.getSelectedItem().toString();

                if (email.isEmpty()) {
                    emailEditText.setError("Email is required!");
                    emailEditText.requestFocus();
                    return;
                }

                Map<String, Object> formData = new HashMap<>();
                formData.put("email", email);
                formData.put("skin_tones", skinTones);
                formData.put("allergy", allergy);
                formData.put("skin_type", skinType);
                formData.put("skin_sensitivity", skinSensitivity);
                formData.put("skin_concern", skinConcern);

               fStore.collection("forms").add(formData)
                        .addOnSuccessListener(documentReference -> {
                            Toast.makeText(MainActivity5.this, "Form submitted successfully!", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(MainActivity5.this,UserProfileActivity.class);
                                    // Open another activity after submitting the form
                                    startActivity(new Intent(MainActivity5.this, UserProfileActivity.class));
                        })
                        .addOnFailureListener(e -> Toast.makeText(MainActivity5.this, "Error submitting form: " + e.getMessage(), Toast.LENGTH_LONG).show());
            }
        });
    }
}
