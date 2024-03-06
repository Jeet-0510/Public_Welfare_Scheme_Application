package com.example.jamnagarwelfareschemes;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class HomeFragment extends Fragment {

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        CardView educational, childWelfare, SeniorCitizen, Poverty, Divyang, Health;

        educational = view.findViewById(R.id.cvEducational);
        childWelfare = view.findViewById(R.id.cvChildWelfare);
        SeniorCitizen = view.findViewById(R.id.cvSeniorCitizen);
        Poverty = view.findViewById(R.id.cvPoverty);
        Divyang = view.findViewById(R.id.cvDivyangKalyan);
        Health = view.findViewById(R.id.cvHealthHousing);

        educational.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),CatagorySchemes.class);
                intent.putExtra("catagory","Education Schemes");
                startActivity(intent);
            }
        });

        childWelfare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),CatagorySchemes.class);
                intent.putExtra("catagory","Child Welfare Schemes");
                startActivity(intent);
            }
        });

        SeniorCitizen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),CatagorySchemes.class);
                intent.putExtra("catagory","Senior Citizen Schemes");
                startActivity(intent);
            }
        });

        Poverty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),CatagorySchemes.class);
                intent.putExtra("catagory","Poverty Allevation Schemes");
                startActivity(intent);
            }
        });

        Divyang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),CatagorySchemes.class);
                intent.putExtra("catagory","Divyang Kalyan Schemes");
                startActivity(intent);
            }
        });

        Health.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),CatagorySchemes.class);
                intent.putExtra("catagory","Health & Housing Schemes");
                startActivity(intent);
            }
        });
        return view;
    }
}