package com.ptc.csale.adapter;

import android.content.Context;
import android.media.FaceDetector;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ptc.csale.R;
import com.ptc.csale.model.Ad;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by phamthanhcong on 11/11/2017.
 */

public class AdAdapter extends PagerAdapter{
   private ArrayList<Ad> listAd;
private LayoutInflater inflater;

    public AdAdapter(Context context, ArrayList<Ad> listAd) {
        this.listAd = listAd;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return listAd.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {

        return view.equals(object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = inflater.inflate(R.layout.item_ad,container,false);
        ImageView imageAd = view.findViewById(R.id.image_item_ad);
        Ad ad = listAd.get(position);
        Picasso.with(container.getContext()).load(ad.getUrlImageAd()).into(imageAd);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
