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
                Fragment_Friends fragmentOne = new Fragment_Friends();
                return fragmentOne;
            case 1:
                Fragment_Requests fragmentTwo = new Fragment_Requests();
                return fragmentTwo;
            case 2:
                Fragment_Members fragmentThree = new Fragment_Members();
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
