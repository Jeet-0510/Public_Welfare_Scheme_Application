package com.example.jamnagarwelfareschemes;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class StatusFragment extends Fragment {

    public static final String SHARED_PREFS = "sharedPrefs";
    SearchView searchView;
    RecyclerView recyclerView;
    List<SchemesStatusData> schemesStatusData;
    String userName;
    public StatusFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_status, container, false);

        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        userName = sharedPreferences.getString("userName", "");

        searchView = view.findViewById(R.id.searchSchemesStatus);
        recyclerView = view.findViewById(R.id.recycleViewStatus);

        schemesStatusData = new ArrayList<>();

        setStatusRecycle();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                List<SchemesStatusData>  schemeData = new ArrayList<>();

                if (query.length()>0){

                    for(int i=0; i<schemesStatusData.size();i++){
                        if(schemesStatusData.get(i).getSchemeName().toUpperCase().contains(query.toUpperCase().trim())){
                            schemeData.add(schemesStatusData.get(i));
                        }
                    }

                    GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1);
                    recyclerView.setLayoutManager(gridLayoutManager);

                    SchemesStatusAdapter adapter = new SchemesStatusAdapter(getContext(),schemeData);
                    recyclerView.setAdapter(adapter);
                }else{
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1);
                    recyclerView.setLayoutManager(gridLayoutManager);

                    SchemesStatusAdapter adapter = new SchemesStatusAdapter(getContext(),schemesStatusData);
                    recyclerView.setAdapter(adapter);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                List<SchemesStatusData> schemeData = new ArrayList<>();

                if (newText.length()>0){

                    for(int i=0; i<schemesStatusData.size();i++){
                        if(schemesStatusData.get(i).getSchemeName().toUpperCase().contains(newText.toUpperCase().trim())){
                            schemeData.add(schemesStatusData.get(i));
                        }
                    }

                    GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1);
                    recyclerView.setLayoutManager(gridLayoutManager);

                    SchemesStatusAdapter adapter = new SchemesStatusAdapter(getContext(),schemeData);
                    recyclerView.setAdapter(adapter);
                }else{
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1);
                    recyclerView.setLayoutManager(gridLayoutManager);

                    SchemesStatusAdapter adapter = new SchemesStatusAdapter(getContext(),schemesStatusData);
                    recyclerView.setAdapter(adapter);
                }

                return false;
            }
        });

        return view;
    }

    private void setStatusRecycle() {

        FirebaseDatabase.getInstance().getReference("USERS").child(userName).child("Schemes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){

                    List<String> yearList = new ArrayList<>();
                    for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                        String year = dataSnapshot.getKey();
                        yearList.add(year);
                    }
                    for(String year: yearList){
                        FirebaseDatabase.getInstance().getReference("USERS").child(userName).child("Schemes").child(year).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if(snapshot.exists()) {
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
//
                                        SchemesStatusData data = new SchemesStatusData(applicationId,schemeName,schemeCatagory,status,query,transaction,date);
                                        statusData.add(data);
                                    }
                                    GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1);
                                    recyclerView.setLayoutManager(gridLayoutManager);

                                    SchemesStatusAdapter schemesStatusAdapter = new SchemesStatusAdapter(getContext(),statusData);
                                    recyclerView.setAdapter(schemesStatusAdapter);

                                    setSchemeData(statusData);
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
    void setSchemeData(List<SchemesStatusData> data){
        schemesStatusData = data;
    }
}