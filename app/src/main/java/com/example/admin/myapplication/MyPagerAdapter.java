package com.example.admin.myapplication;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by admin on 3/30/2017.
 */

public class MyPagerAdapter extends FragmentStatePagerAdapter {

    private int tabCount;

    public MyPagerAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                FragmentOne fragmentOne = new FragmentOne();
                return fragmentOne;
            case 1:
                FragmentTwo fragmentTwo = new FragmentTwo();
                return fragmentTwo;
            case 2:
                FragmentThree fragmentThree = new FragmentThree();
                return fragmentThree;
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
