package com.ptc.csale.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.ptc.csale.R;
import com.ptc.csale.adapter.ViewPagerHome;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {
    DrawerLayout drawerLayout;
    Button btnMenu, btnHome, btnProduct, btnCart, btnHot, btnPost,btnNotification;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private ViewPagerHome viewPagerHome;
    private LinearLayout leftMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initData();
        initViews();
        initPager();
    }

    private void initData() {

    }

    private void initViews() {
        drawerLayout = findViewById(R.id.drawerLayout);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        btnMenu = findViewById(R.id.btnMenu);
        btnHome = findViewById(R.id.btn_home);
        btnProduct = findViewById(R.id.btn_product);
        btnCart = findViewById(R.id.btn_cart);
        btnHot = findViewById(R.id.btn_hot_product);
        btnPost = findViewById(R.id.btn_post);
        btnNotification = findViewById(R.id.btn_notification);
        btnMenu.setOnClickListener(this);
        btnHome.setOnClickListener(this);
        btnProduct.setOnClickListener(this);
        btnCart.setOnClickListener(this);
        btnHot.setOnClickListener(this);
        btnPost.setOnClickListener(this);
        btnNotification.setOnClickListener(this);
    }

    private void initPager() {
        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);
        FragmentManager manager = getSupportFragmentManager();
        viewPagerHome = new ViewPagerHome(manager);
        viewPager.setAdapter(viewPagerHome);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setTabsFromPagerAdapter(viewPagerHome);
        tabLayout.getTabAt(0).setIcon(R.drawable.icon_home);
        tabLayout.getTabAt(1).setIcon(R.drawable.icon_product);
        tabLayout.getTabAt(2).setIcon(R.drawable.icon_cart);
        tabLayout.getTabAt(3).setIcon(R.drawable.icon_post);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnMenu:
                drawerLayout.openDrawer(Gravity.LEFT);
                break;
            case R.id.btn_home:
                viewPager.setCurrentItem(0);
                drawerLayout.closeDrawers();
                break;
            case R.id.btn_product:
                viewPager.setCurrentItem(1);
                drawerLayout.closeDrawers();
                break;
            case R.id.btn_cart:
                viewPager.setCurrentItem(2);
                drawerLayout.closeDrawers();
                break;
            case R.id.btn_post:
                viewPager.setCurrentItem(3);
                drawerLayout.closeDrawers();
                break;
        }
    }
}
