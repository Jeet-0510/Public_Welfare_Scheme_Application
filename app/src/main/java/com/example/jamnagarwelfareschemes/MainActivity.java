package com.example.jamnagarwelfareschemes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static final String SHARED_PREFS = "sharedPrefs";
    ViewPager sliderViewPager;
    Button btnSkip;
    LinearLayout dotsIndicatorLayout;
    TextView dots[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!check()) {
            sliderViewPager = findViewById(R.id.viewPager);
            btnSkip = findViewById(R.id.btnSkip);
            dotsIndicatorLayout = findViewById(R.id.dotsIndicatorLayout);

            btnSkip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), TermsAndConditions.class);
                    startActivity(intent);
                    finish();
                }
            });

            ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this);
            sliderViewPager.setAdapter(viewPagerAdapter);

            setUpIndicator(0);

            sliderViewPager.addOnPageChangeListener(viewListner);

        }
    }

    public void setUpIndicator(int position) {

        dots = new TextView[3];
        dotsIndicatorLayout.removeAllViews();

        for (int i = 0; i < dots.length; i++) {

            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226"));
            dots[i].setTextSize(60);
            dots[i].setTextColor(getResources().getColor(R.color.inactive, getApplicationContext().getTheme()));
            dotsIndicatorLayout.addView(dots[i]);

        }

        dots[position].setTextColor(getResources().getColor(R.color.active, getApplicationContext().getTheme()));
    }

    ViewPager.OnPageChangeListener viewListner = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            setUpIndicator(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private boolean check() {

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String check = sharedPreferences.getString("check", "");
        if (check.equals("true")) {
            Intent intent = new Intent(MainActivity.this, Home.class);
            startActivity(intent);
            finish();
            return true;
        }
        return false;
    }
}