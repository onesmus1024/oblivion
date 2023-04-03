package com.example.oblivion;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity6 extends AppCompatActivity {

    private static final String TAG = "test";
    private static final int MY_SOCKET_TIMEOUT_MS = 9000;
    private FirebaseAuth auth;
    private FirebaseFirestore fStore;

    List<SingleProduct> products = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecommendedProductsRecyclerViewAdapter recommendedProductsRecyclerViewAdapter;
    private RecyclerView.LayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        auth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);
        recyclerView = findViewById(R.id.product_rv);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser != null) {
            String currentEmail = currentUser.getEmail();
            getFormData(currentEmail);

        }

        getFormData("ten@gmail.com");



    }

    String skinConcern;
    String allergy;
    String skinSensitivity;
    String skinTones;
    String skinType;

    void getFormData(String userEmail) {
        Toast.makeText(this, userEmail, Toast.LENGTH_SHORT).show();
        fStore.collection("forms")
                .whereEqualTo("email", userEmail)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && !task.getResult().isEmpty()) {
                        DocumentSnapshot document = task.getResult().getDocuments().get(0);
                        skinConcern = document.getString("skinConcern");
                        allergy = document.getString("allergy");
                        skinSensitivity = document.getString("skinSensitivity");
                        skinTones = document.getString("skinTones");
                        skinType = document.getString("skinType");
                        // Call getProducts() after retrieving form data
                        getProducts();
                    }else {
                        Log.d(TAG, "No matching documents. Using dummy data.");
                        // Use dummy data
                        skinConcern = "Dryness";
                        allergy = "None";
                        skinSensitivity = "Sensitive";
                        skinTones = "Medium";
                        skinType = "Dry";
                        // Call getProducts() with dummy data
                        getProducts();

                    }
                });
    }

    void getProducts() {
        String url = "https://92be-41-81-191-165.in.ngrok.io/predict";
        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(this);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("allergy", allergy);
            jsonObject.put("skin_concern", skinConcern);
            jsonObject.put("skin_sensitivity", skinSensitivity);
            jsonObject.put("skin_tone", skinTones);
            jsonObject.put("skin_type", skinType);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Request a JSON response from the provided URL.
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Handle response from server
                        String prediction = response.optString("prediction");
                        Toast.makeText(MainActivity6.this, prediction, Toast.LENGTH_SHORT).show();
                        fStore.collection("images")
                                .whereEqualTo("type", "deep")
                                .get()
                                .addOnCompleteListener(task -> {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            String imgUrl = document.getString("img_url");
                                            // Do something with imgUrl
                                            products.add(new SingleProduct(imgUrl,"2"));
                                        }

                                        recommendedProductsRecyclerViewAdapter.notifyDataSetChanged();
                                    } else {
                                        Log.d(TAG, "Error getting documents: ", task.getException());
                                        products.add(new SingleProduct("https://firebasestorage.googleapis.com/v0/b/oblivion-fd8ec.appspot.com/o/Fenty%2Ffoundnation%2Fdeep%2F1smear.png?alt=media&token=1aa936e3-7d3b-4060-99c0-a39707dc082b","2"));

                                        recommendedProductsRecyclerViewAdapter.notifyDataSetChanged();


                                    }
                                });


                        recommendedProductsRecyclerViewAdapter = new RecommendedProductsRecyclerViewAdapter(products, MainActivity6.this);
                        recyclerView.setAdapter(recommendedProductsRecyclerViewAdapter);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Handle error
                Toast.makeText(MainActivity6.this, error.toString(), Toast.LENGTH_SHORT).show();
            }


        });


        requestQueue.add(jsonObjectRequest);




    }

}
