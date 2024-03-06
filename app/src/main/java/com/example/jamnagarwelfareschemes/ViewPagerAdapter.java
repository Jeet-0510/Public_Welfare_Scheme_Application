package com.example.jamnagarwelfareschemes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class ViewPagerAdapter extends PagerAdapter {

    Context context;

    int image1= R.drawable.slider_view_1, image2= R.drawable.slider_view_2;

    int description1 = R.string.slider_view1, description2= R.string.slider_view2;

    public ViewPagerAdapter(Context context){
        this.context = context;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (LinearLayout)object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view=null;

        if(position == 0) {
            view = layoutInflater.inflate(R.layout.slider_layout,container,false);

            ImageView imageView = (ImageView) view.findViewById(R.id.imgSlider);
            TextView textView = (TextView) view.findViewById(R.id.txtSlider);

            imageView.setImageResource(image1);
            textView.setText(description1);
        }else if (position == 1){
            view = layoutInflater.inflate(R.layout.slider_layout2,container,false);
        }else {
            view = layoutInflater.inflate(R.layout.slider_layout,container,false);

            ImageView imageView = (ImageView) view.findViewById(R.id.imgSlider);
            TextView textView = (TextView) view.findViewById(R.id.txtSlider);

            imageView.setImageResource(image2);
            textView.setText(description2);
        }

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        container.removeView((LinearLayout)object);
    }
}
