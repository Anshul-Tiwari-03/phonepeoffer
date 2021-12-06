package com.example.phonepeoffers.offer_detail;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.phonepeoffers.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class imageslideradapter extends PagerAdapter{


    String[] list1;
    Context context;
    String url="https://anshul.ohari5336.in/your_code/coupons/";

    imageslideradapter(String[] imaglist, Context c) {
        this.list1 = imaglist;
        context = c;
    }

    @Override
    public int getCount() {
        return list1.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {



        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.image_layout,container,false);
        ImageView image = view.findViewById(R.id.imageview);


        Glide.with(context).load(url+list1[position]).into(image);

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);

    }
}




