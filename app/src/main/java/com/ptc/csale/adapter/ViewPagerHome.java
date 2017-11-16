package com.ptc.csale.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.ptc.csale.Fragment.CartFragment;
import com.ptc.csale.Fragment.HomeFragment;
import com.ptc.csale.Fragment.PostFragment;
import com.ptc.csale.Fragment.ProductFragment;

/**
 * Created by phamthanhcong on 10/11/2017.
 */

public class ViewPagerHome extends FragmentPagerAdapter {
    private HomeFragment mHomeFragment;
    private ProductFragment mProductFragment;
    private CartFragment mCartFragment;
    private PostFragment mPostFragment;

    public ViewPagerHome(FragmentManager fm) {
        super(fm);
        mHomeFragment = new HomeFragment();
        mProductFragment = new ProductFragment();
        mCartFragment = new CartFragment();
        mPostFragment=new PostFragment();
    }

    @Override
    public Fragment getItem(int position) {
        if (position ==0){
            return mHomeFragment;
        }
        else if(position ==1){
            return mProductFragment;
        }
        else if(position==2){
            return mCartFragment;
        }
        else if(position==3){
            return mPostFragment;
        }
        else{
            // Todo thing
        }
        return null;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position){
            case 0:
                title = "Home";
                break;
            case 1:
                title = "Product";
                break;
            case 2:
                title = "Cart";
                break;
            case  3:
                title="Post";
                        break;
        }
        return title;
    }
}
