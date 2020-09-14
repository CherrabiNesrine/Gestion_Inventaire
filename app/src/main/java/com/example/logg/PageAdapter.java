package com.example.logg;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class PageAdapter extends FragmentPagerAdapter {
    int numberOfPage;
    public PageAdapter( FragmentManager fm, int numberOfPage) {
     super(fm);
     this.numberOfPage=numberOfPage;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0: Sales sales= new Sales(); return sales;
            case 1 : purchases pur = new purchases(); return pur;
            case 2 : Transfers transfers = new Transfers(); return transfers;
            default: return null;
        }

    }

    @Override
    public int getCount() {
        return numberOfPage;
    }
}
