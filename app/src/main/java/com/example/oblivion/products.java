package com.example.oblivion;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.List;

public class products extends AppCompatActivity {
    private ListView listView;
    private ArrayAdapter adapter;
    private List<String> brands;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        brands = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, brands);
        listView = findViewById(R.id.list_view);
        listView.setAdapter(adapter);
        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        db.collection("forms")
                .whereEqualTo("email", firebaseAuth.getCurrentUser().getEmail())
                .whereEqualTo("skin_tones", "fair")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String allergy = document.getString("allergy");
                                if (allergy.equals("Metals")) {
                                    brands.add("Fenty");
                                    brands.add("Maybelline");
                                } else if (allergy.equals("dyes")) {
                                    brands.add("Huddah");
                                    brands.add("Maybelline");
                                } else if (allergy.equals("fragrance")) {
                                    brands.add("Fenty");
                                    brands.add("Elline");
                                } else if (allergy.equals("preservatives")) {
                                    brands.add("Fenty");
                                    brands.add("Arimis");
                                } else if (allergy.equals("none")) {
                                    brands.add("Fenty");
                                    brands.add("Nevia");
                                }
                            }
                            adapter.notifyDataSetChanged();
                        }
                    }
                });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Handle click event on each brand item in the list
                // and start a new activity based on the selected brand.
            }
        });
    }
}
