package com.ptc.csale.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.ptc.csale.R;
import com.ptc.csale.adapter.AdAdapter;
import com.ptc.csale.adapter.ProductAdapter;
import com.ptc.csale.model.Ad;
import com.ptc.csale.model.Product;

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator;

/**
 * Created by phamthanhcong on 10/11/2017.
 */

public class HomeFragment extends Fragment {
    private View mRootView;
    private ViewPager viewPagerAd;
    private ArrayList<Ad> listAd;
    private AdAdapter adAdapter;
    private CircleIndicator indicatorAd;
    private Handler handler;
    private int delay = 5000;
    private int page = 0;
    private GridView gridViewProduct;
    private ProductAdapter productAdapter;
    private ArrayList<Product> productArrayList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_home, container, false);
        handler = new Handler();
        initData();
        initViews();
        return mRootView;
    }

    private void initData() {
        listAd = new ArrayList<>();
        listAd.add(new Ad("https://quangcao.thegioidohoa.vn/wp-content/uploads/2017/05/giao-duc-1-1.jpg"));
        listAd.add(new Ad("http://vietit.vn/media/upload/marketing_online/facebook_ads/quang-cao-facebook-ads.jpg"));
        listAd.add(new Ad("https://quangcaogiaredanang.jweb.vn/uploads/quangcaogiaredanang/images/2.jpg"));
        listAd.add(new Ad("https://newshop.vn/public/uploads/cat-images/ki-nang-doc-hieu-chuyen-sau-tieng-anh-luu-hoang-tri_L.jpg"));

        productArrayList = new ArrayList<>();
// title price time address thumbnail
        productArrayList.add(new Product("Gấu bông cực đẹp", "210.000đ", "Hà Nội", "http://hoiquanamthuc.com/wp-content/uploads/2016/11/gau-bong-dep-nhat-the-nhin-la-muon-mang-ve-nha-lien19.jpg", "10 phút trước"));
        productArrayList.add(new Product("Gấu bông cực đẹp", "210.000đ", "Hà Nội", "http://hoiquanamthuc.com/wp-content/uploads/2016/11/gau-bong-dep-nhat-the-nhin-la-muon-mang-ve-nha-lien19.jpg", "10 phút trước"));
        productArrayList.add(new Product("Gấu bông cực đẹp", "210.000đ", "Hà Nội", "http://hoiquanamthuc.com/wp-content/uploads/2016/11/gau-bong-dep-nhat-the-nhin-la-muon-mang-ve-nha-lien19.jpg", "10 phút trước"));
        productArrayList.add(new Product("Gấu bông cực đẹp", "210.000đ", "Hà Nội", "http://hoiquanamthuc.com/wp-content/uploads/2016/11/gau-bong-dep-nhat-the-nhin-la-muon-mang-ve-nha-lien19.jpg", "10 phút trước"));
        productArrayList.add(new Product("Gấu bông cực đẹp", "210.000đ", "Hà Nội", "http://hoiquanamthuc.com/wp-content/uploads/2016/11/gau-bong-dep-nhat-the-nhin-la-muon-mang-ve-nha-lien19.jpg", "10 phút trước"));
        productArrayList.add(new Product("Gấu bông cực đẹp", "210.000đ", "Hà Nội", "http://hoiquanamthuc.com/wp-content/uploads/2016/11/gau-bong-dep-nhat-the-nhin-la-muon-mang-ve-nha-lien19.jpg", "10 phút trước"));
        productArrayList.add(new Product("Gấu bông cực đẹp", "210.000đ", "Hà Nội", "http://hoiquanamthuc.com/wp-content/uploads/2016/11/gau-bong-dep-nhat-the-nhin-la-muon-mang-ve-nha-lien19.jpg", "10 phút trước"));
        productArrayList.add(new Product("Gấu bông cực đẹp", "210.000đ", "Hà Nội", "http://hoiquanamthuc.com/wp-content/uploads/2016/11/gau-bong-dep-nhat-the-nhin-la-muon-mang-ve-nha-lien19.jpg", "10 phút trước"));

    }

    private void initViews() {
        //    String title,price,time,place,thumnail;
        gridViewProduct = mRootView.findViewById(R.id.grid_view_product);
        productAdapter = new ProductAdapter(mRootView.getContext(), productArrayList);
        gridViewProduct.setAdapter(productAdapter);


        viewPagerAd = mRootView.findViewById(R.id.viewpager_ad);
        adAdapter = new AdAdapter(mRootView.getContext(), listAd);
        viewPagerAd.setAdapter(adAdapter);
        indicatorAd = mRootView.findViewById(R.id.indicator_ad);
        indicatorAd.setViewPager(viewPagerAd);
    }

    Runnable runnable = new Runnable() {
        public void run() {
            if (adAdapter.getCount() - 1 == page) {
                page = 0;
            } else {
                page++;
            }
            viewPagerAd.setCurrentItem(page, true);
            handler.postDelayed(this, delay);
        }
    };

    @Override
    public void onResume() {
        handler.postDelayed(runnable, delay);
        super.onResume();
    }
    @Override
    public void onPause() {
        handler.removeCallbacks(runnable);
        super.onPause();
    }
}



