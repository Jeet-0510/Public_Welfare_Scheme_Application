package com.example.jamnagarwelfareschemes;

import static android.content.Context.MODE_PRIVATE;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;


public class ApprovedFragment extends Fragment {

    public static final String SHARED_PREFS = "sharedPrefs";
    RecyclerView recyclerView;
    FloatingActionButton filter;
    List<SchemesStatusData> schemesStatusData ;
    String userName;
    HistoryAdapter historyAdapter;

    public ApprovedFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_approved, container, false);

        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        userName = sharedPreferences.getString("userName", "");

        recyclerView = view.findViewById(R.id.recycleViewApproved);
        filter = view.findViewById(R.id.filterApproved);

        filter.setColorFilter(getResources().getColor(android.R.color.white));

        schemesStatusData = new ArrayList<>();

        setHistory();

        setFilter();

        return view;
    }

    private void setHistory() {
        FirebaseDatabase.getInstance().getReference("USERS").child(userName).child("Schemes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    List<String> yearList = new ArrayList<>();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        String year = dataSnapshot.getKey();
                        yearList.add(year);
                    }
                    for (String year : yearList) {
                        FirebaseDatabase.getInstance().getReference("USERS").child(userName).child("Schemes").child(year).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists()) {

                                    List<SchemesStatusData> statusData = new ArrayList<>();
                                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

//                                        SchemesStatusData data = dataSnapshot.child("SchemeStatusData").getValue(SchemesStatusData.class);
                                        String applicationId = dataSnapshot.child("SchemeStatusData").child("applicationId").getValue(String.class);
                                        String schemeCatagory = dataSnapshot.child("SchemeStatusData").child("schemeCatagory").getValue(String.class);
                                        String schemeName = dataSnapshot.child("SchemeStatusData").child("schemeName").getValue(String.class);
                                        String query = dataSnapshot.child("SchemeStatusData").child("query").getValue(String.class);
                                        String status = dataSnapshot.child("SchemeStatusData").child("status").getValue(String.class);
                                        String transaction = dataSnapshot.child("SchemeStatusData").child("transaction").getValue(String.class);
                                        String date = dataSnapshot.child("SchemeStatusData").child("dateOfApplication").getValue(String.class);

                                        if (status != null && status.equals("Approved")) {
                                            SchemesStatusData data = new SchemesStatusData(applicationId, schemeName, schemeCatagory, status, query, transaction, date);
                                            statusData.add(data);
                                        }
                                    }
                                    if (statusData.size() == 0) {
                                        if (getContext() != null) {
                                            Toast.makeText(getContext(), "No Approved application Found!!!", Toast.LENGTH_SHORT).show();
                                        }
                                        Log.e("Initial " , "No Data");
                                    }else{
                                        setSchemeData(statusData);
                                        Log.e("Initial",statusData.get(0).getApplicationId());
                                    }

                                    GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1);
                                    recyclerView.setLayoutManager(gridLayoutManager);

                                    historyAdapter = new HistoryAdapter(getContext(), statusData);
                                    recyclerView.setAdapter(historyAdapter);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setFilter() {
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Create an AlertDialog builder
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Select Year");

                // Create a layout inflater
                LayoutInflater inflater = getLayoutInflater();

                // Inflate the layout for the dialog
                View dialogView = inflater.inflate(R.layout.filter_year, null);
                builder.setView(dialogView);

                // Get the Spinner from the layout
                Spinner yearSpinner = dialogView.findViewById(R.id.yearSpinner);

                List<String> yearList = new ArrayList<>();

                FirebaseDatabase.getInstance().getReference("USERS").child(userName).child("Schemes").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            yearList.add("ALL");
                            yearList.add("2022");
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                String year = dataSnapshot.getKey();
                                yearList.add(year);
                            }
                            ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, yearList);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            yearSpinner.setAdapter(adapter);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                // Set positive button click listener
                builder.setPositiveButton("Search", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Handle the selected year
                        String selectedYear = (String) yearSpinner.getSelectedItem();
                        if (Objects.equals(selectedYear, "ALL")) {
                            FirebaseDatabase.getInstance().getReference("USERS").child(userName).child("Schemes").addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if (snapshot.exists()) {
                                        List<SchemesStatusData> statusData = new ArrayList<>();
                                        List<String> yearList = new ArrayList<>();
                                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                            String year = dataSnapshot.getKey();
                                            yearList.add(year);
                                        }
                                        for (String year : yearList) {
                                            FirebaseDatabase.getInstance().getReference("USERS").child(userName).child("Schemes").child(year).addValueEventListener(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                    if (snapshot.exists()) {
                                                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                                                            String applicationId = dataSnapshot.child("SchemeStatusData").child("applicationId").getValue(String.class);
                                                            String schemeCatagory = dataSnapshot.child("SchemeStatusData").child("schemeCatagory").getValue(String.class);
                                                            String schemeName = dataSnapshot.child("SchemeStatusData").child("schemeName").getValue(String.class);
                                                            String query = dataSnapshot.child("SchemeStatusData").child("query").getValue(String.class);
                                                            String status = dataSnapshot.child("SchemeStatusData").child("status").getValue(String.class);
                                                            String transaction = dataSnapshot.child("SchemeStatusData").child("transaction").getValue(String.class);
                                                            String date = dataSnapshot.child("SchemeStatusData").child("dateOfApplication").getValue(String.class);

                                                            if (status != null && status.equals("Approved")) {
                                                                SchemesStatusData data = new SchemesStatusData(applicationId, schemeName, schemeCatagory, status, query, transaction, date);
                                                                statusData.add(data);
                                                            }
                                                        }
                                                        if (statusData.size() == 0) {
                                                            if (getContext() != null) {
                                                                Toast.makeText(getContext(), "No Approved application Found!!!", Toast.LENGTH_SHORT).show();
                                                            }
                                                            Log.e("All "+ selectedYear , "No Data");
                                                        }else {
                                                            setSchemeData(statusData);
                                                            Log.e("All",statusData.get(0).getApplicationId());
//                                                            historyAdapter.setData(schemesStatusData);
//                                                            historyAdapter.notifyDataSetChanged();
                                                        }
//                                                        historyAdapter.notifyDataSetChanged();

                                                        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1);
                                                        recyclerView.setLayoutManager(gridLayoutManager);

                                                        historyAdapter = new HistoryAdapter(getContext(), statusData);
                                                        recyclerView.setAdapter(historyAdapter);
                                                    }
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError error) {

                                                }
                                            });
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                        } else {
                            FirebaseDatabase.getInstance().getReference("USERS").child(userName).child("Schemes").child(selectedYear).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if (snapshot.exists()) {
//                                        if(historyAdapter1!=null) {
//                                            historyAdapter1.clearData();
//                                            historyAdapter1.notifyDataSetChanged();
//                                        }
//                                        if(historyAdapter2!=null) {
//                                            historyAdapter2.clearData();
//                                            historyAdapter2.notifyDataSetChanged();
//                                        }
                                        List<SchemesStatusData> statusData = new ArrayList<>();

                                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

//                                        SchemesStatusData data = dataSnapshot.child("SchemeStatusData").getValue(SchemesStatusData.class);
                                            String applicationId = dataSnapshot.child("SchemeStatusData").child("applicationId").getValue(String.class);
                                            String schemeCatagory = dataSnapshot.child("SchemeStatusData").child("schemeCatagory").getValue(String.class);
                                            String schemeName = dataSnapshot.child("SchemeStatusData").child("schemeName").getValue(String.class);
                                            String query = dataSnapshot.child("SchemeStatusData").child("query").getValue(String.class);
                                            String status = dataSnapshot.child("SchemeStatusData").child("status").getValue(String.class);
                                            String transaction = dataSnapshot.child("SchemeStatusData").child("transaction").getValue(String.class);
                                            String date = dataSnapshot.child("SchemeStatusData").child("dateOfApplication").getValue(String.class);

                                            if (status != null && status.equals("Approved")) {
                                                SchemesStatusData data = new SchemesStatusData(applicationId, schemeName, schemeCatagory, status, query, transaction, date);
                                                statusData.add(data);
                                            }
                                        }
                                        if (statusData.size() == 0) {
                                            if (getContext() != null) {
                                                Toast.makeText(getContext(), "No Approved application Found in Year " + selectedYear + " !!!", Toast.LENGTH_SHORT).show();
                                            }
                                            Log.e("Year " + selectedYear , "No Data");
                                        }else {
                                            setSchemeData(statusData);
                                            Log.e("Year " + selectedYear,statusData.get(0).getApplicationId());
                                        }
//                                        historyAdapter.setData(schemesStatusData);
//                                        historyAdapter.notifyDataSetChanged();
//                                        historyAdapter.notifyDataSetChanged();

                                        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1);
                                        recyclerView.setLayoutManager(gridLayoutManager);

                                        historyAdapter = new HistoryAdapter(getContext(), statusData);
                                        recyclerView.setAdapter(historyAdapter);
                                    } else {
                                        Log.e("no snapshot " , "No Data");
                                        Toast.makeText(getContext(), "No Approved application Found in Year " + selectedYear + " !!!", Toast.LENGTH_SHORT).show();

                                        historyAdapter.clearData();
                                        historyAdapter.notifyDataSetChanged();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                    }
                });

                // Set negative button click listener
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Handle cancel action
                        dialog.dismiss();
                    }
                });

                // Show the dialog
                builder.create().show();
            }
        });

    }

    void setSchemeData(List<SchemesStatusData> data){
        schemesStatusData = data;
    }

}