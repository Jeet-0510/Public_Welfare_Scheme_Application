package com.example.jamnagarwelfareschemes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class History extends AppCompatActivity {
    Toolbar toolbar;
    TextView title;
    LinearLayout Approved, Rejected;
    FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        toolbar = findViewById(R.id.toolbar);
        title = findViewById(R.id.txtToolbarText);
        Approved = findViewById(R.id.LLApproved);
        Rejected = findViewById(R.id.LLRejected);
        frameLayout = findViewById(R.id.framelayoutHistory);

        title.setText("History");

        if(getSupportFragmentManager().findFragmentById(R.id.framelayoutHistory)!=null) {
            replace(new ApprovedFragment(), false);
            Approved.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.historyGreen));
            Rejected.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.historyGrey));

        }else {
            replace(new ApprovedFragment(), true);
            Approved.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.historyGreen));
            Rejected.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.historyGrey));

        }

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_white_back_arrow); // Set the custom icon here
        }

        Approved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replace(new ApprovedFragment(), false);
                Approved.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.historyGreen));
                Rejected.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.historyGrey));
            }
        });

        Rejected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replace(new RejectedFragment(), false);
                Rejected.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.historyRed));
                Approved.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.historyGrey));

            }
        });
    }

    private void replace(Fragment fragment, Boolean flag) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (flag) {
            transaction.add(R.id.framelayoutHistory, fragment);
        } else {
            transaction.replace(R.id.framelayoutHistory, fragment);
        }
        transaction.commit();
    }
}