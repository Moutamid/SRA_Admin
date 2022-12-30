package com.moutamid.sra_admin.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.moutamid.sra_admin.fragments.AllFragment;
import com.moutamid.sra_admin.fragments.DepositFragment;
import com.moutamid.sra_admin.fragments.WithdrawFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    public ViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (position == 0)
            fragment = new AllFragment();
        else if (position == 1)
            fragment = new WithdrawFragment();
        else if (position == 2)
            fragment = new DepositFragment();

        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        if (position == 0)
            title = "All Requests";
        else if (position == 1)
            title = "Withdraw Requests";
        else if (position == 2)
            title = "Deposit Requests";
        return title;
    }
}
