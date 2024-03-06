package com.example.jamnagarwelfareschemes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class Home extends AppCompatActivity {
    public static final String SHARED_PREFS = "sharedPrefs";
    MeowBottomNavigation meowBottomNavigation;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;
    UserData dataclass;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Log.e("Home", "YES");

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String userName = sharedPreferences.getString("userName", "");

        //White Background
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
            int nightModeFlags = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
            if (nightModeFlags == Configuration.UI_MODE_NIGHT_YES) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                recreate();
            }
        }

        meowBottomNavigation = findViewById(R.id.bottomNavigation);
        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.navigationView);
        drawerLayout = findViewById(R.id.drawerLayout);

        if(getIntent().hasExtra("keySubmit")){
            replace(new StatusFragment(), false);
            meowBottomNavigation.show(2, true);
        } else if(getSupportFragmentManager().findFragmentById(R.id.framelayout)!=null) {
            replace(new HomeFragment(), false);
            meowBottomNavigation.show(1, true);
        }else {
            replace(new HomeFragment(), true);
            meowBottomNavigation.show(1, true);
        }

        meowBottomNavigation.add(new MeowBottomNavigation.Model(1, R.drawable.home1));
        meowBottomNavigation.add(new MeowBottomNavigation.Model(2, R.drawable.status1));
        meowBottomNavigation.add(new MeowBottomNavigation.Model(3, R.drawable.profile1));

        View headerView = navigationView.getHeaderView(0);
        TextView txtProfile = headerView.findViewById(R.id.txtProfileUser);
        txtProfile.setText(userName);
        ImageView imageView = headerView.findViewById(R.id.imgProfileUser);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("USERS").child(userName);
        ValueEventListener valueEventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot itemSnapshot : snapshot.getChildren()) {

                        if (itemSnapshot.getKey().equals("Profile")) {
                            dataclass = itemSnapshot.getValue(UserData.class);

                            if (!dataclass.getPhoto().equals("no image")) {
                                Glide.with(getApplicationContext())
                                        .load(dataclass.getPhoto())
                                        .into(imageView);
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        headerView.setOnClickListener(v -> {
            replace(new ProfileFragment(), false);
            meowBottomNavigation.show(3, true);
            drawerLayout.closeDrawer(GravityCompat.START);
        });

        meowBottomNavigation.setOnClickMenuListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {

                switch (model.getId()) {
                    case 1:
                        replace(new HomeFragment(), false);
                        break;

                    case 2:
                        replace(new StatusFragment(), false);
                        break;

                    case 3:
                        replace(new ProfileFragment(), false);
                        break;
                }
                return null;
            }
        });

        meowBottomNavigation.setOnShowListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {
                // YOUR CODES
                switch (model.getId()) {
                    case 1:
                        break;
                }
                return null;
            }
        });

        meowBottomNavigation.setOnShowListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {
                // YOUR CODES
                switch (model.getId()) {
                    case 2:
                        break;
                }
                return null;
            }
        });

        meowBottomNavigation.setOnShowListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {
                // YOUR CODES
                switch (model.getId()) {
                    case 3:
                        break;
                }
                return null;
            }
        });

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_open, R.string.navigation_close);
        actionBarDrawerToggle.getDrawerArrowDrawable().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        navigationView.setNavigationItemSelectedListener(item -> {

            if (item.getItemId() == R.id.itmHome) {
                meowBottomNavigation.show(1, true);
                replace(new HomeFragment(), false);
                drawerLayout.closeDrawer(GravityCompat.START);
            } else if (item.getItemId() == R.id.itmHistory) {
                Intent intent = new Intent(Home.this, History.class);
                startActivity(intent);
            } else if (item.getItemId() == R.id.itmCurrentStatus) {
                meowBottomNavigation.show(2, true);
                replace(new StatusFragment(), false);
                drawerLayout.closeDrawer(GravityCompat.START);
            } else if (item.getItemId() == R.id.itmHelp) {
                Intent intent = new Intent(Home.this, Help.class);
                startActivity(intent);
            } else if (item.getItemId() == R.id.itmLogout) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("check", "");
                editor.putString("mobileNumber", "");
                editor.apply();

                Intent intent = new Intent(Home.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
            return false;
        });
    }

    private void replace(Fragment fragment, Boolean flag) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (flag) {
            transaction.add(R.id.framelayout, fragment);
        } else {
            transaction.replace(R.id.framelayout, fragment);
        }
        transaction.commit();
    }
}