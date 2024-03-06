package com.example.jamnagarwelfareschemes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CatagorySchemes extends AppCompatActivity {

    Toolbar toolbar;
    TextView title;
    SearchView search;
    RecyclerView recyclerView;
    ValueEventListener valueEventListener;
    String catagory;
    List<SchemeData> schemeDataList;

    //    CatagorySchemesAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catagory_schemes);

        toolbar = findViewById(R.id.toolbar);
        title = findViewById(R.id.txtToolbarText);
        search = findViewById(R.id.searchSchemes);
        recyclerView = findViewById(R.id.recycleView);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_white_back_arrow); // Set the custom icon here
        }

        schemeDataList = new ArrayList<>();

        catagory = getIntent().getStringExtra("catagory");
        title.setText(catagory);
        title.setTextSize(16);

        EditText searchEditText = search.findViewById(androidx.appcompat.R.id.search_src_text);
//        int color = getResources().getColor(R.color.textHint);
//        searchEditText.setHintTextColor(color);
        int textColor = getResources().getColor(R.color.black);
        searchEditText.setTextColor(textColor);

        setSchemeRecycle();

//        FirebaseDatabase.getInstance().getReference("Schemes").removeEventListener(valueEventListener);

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
//                List<String> schemeName = new ArrayList<>();
                List<SchemeData> schemeData = new ArrayList<>();

                if (query.length() > 0) {

                    for (int i = 0; i < schemeDataList.size(); i++) {
                        if (schemeDataList.get(i).getName().toUpperCase().contains(query.toUpperCase().trim())) {
                            schemeData.add(schemeDataList.get(i));
                        }
                    }

                    GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
                    recyclerView.setLayoutManager(gridLayoutManager);

                    CatagorySchemesAdapter adapter = new CatagorySchemesAdapter(getApplicationContext(), schemeData);
                    recyclerView.setAdapter(adapter);
                } else {
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
                    recyclerView.setLayoutManager(gridLayoutManager);

                    CatagorySchemesAdapter adapter = new CatagorySchemesAdapter(getApplicationContext(), schemeDataList);
                    recyclerView.setAdapter(adapter);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                List<SchemeData> schemeData = new ArrayList<>();

                if (newText.length() > 0) {

                    for (int i = 0; i < schemeDataList.size(); i++) {
                        if (schemeDataList.get(i).getName().toUpperCase().contains(newText.toUpperCase().trim())) {
                            schemeData.add(schemeDataList.get(i));
                        }
                    }

                    GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
                    recyclerView.setLayoutManager(gridLayoutManager);

                    CatagorySchemesAdapter adapter = new CatagorySchemesAdapter(getApplicationContext(), schemeData);
                    recyclerView.setAdapter(adapter);
                } else {
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
                    recyclerView.setLayoutManager(gridLayoutManager);

                    CatagorySchemesAdapter adapter = new CatagorySchemesAdapter(getApplicationContext(), schemeDataList);
                    recyclerView.setAdapter(adapter);
                }
                return false;
            }
        });

    }

    private void setSchemeRecycle() {
        if (isNetworkAvailable()) {
            valueEventListener = FirebaseDatabase.getInstance().getReference("Schemes").child(catagory).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {

                        List<SchemeData> schemeData = new ArrayList<>();
                        for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                            List<String> document = new ArrayList<>();
                            if (snapshot1.child("document").exists()) {
                                for (DataSnapshot docSnapshot : snapshot1.child("document").getChildren()) {
                                    document.add(docSnapshot.getValue(String.class));
                                }
                            }
                            String name = snapshot1.child("name").getValue(String.class);
                            String detail = snapshot1.child("detail").getValue(String.class);
                            String category = snapshot1.child("catagories").getValue(String.class);
                            String eligibility = snapshot1.child("eligibility").getValue(String.class);
                            String deadline = snapshot1.child("deadline").getValue(String.class);

                            SchemeData data = new SchemeData(document, name, detail, category, eligibility, deadline);
                            schemeData.add(data);
                        }
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
                        recyclerView.setLayoutManager(gridLayoutManager);

                        CatagorySchemesAdapter adapter = new CatagorySchemesAdapter(getApplicationContext(), schemeData);
                        recyclerView.setAdapter(adapter);

                        setSchemeData(schemeData);
                    } else {
                        Toast.makeText(CatagorySchemes.this, "No Schemes Available!!!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        } else {
            Toast.makeText(getApplicationContext(), "Please Check your Internet Connection!!!", Toast.LENGTH_SHORT).show();
        }
    }

    void setSchemeData(List<SchemeData> data) {
        schemeDataList = data;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}