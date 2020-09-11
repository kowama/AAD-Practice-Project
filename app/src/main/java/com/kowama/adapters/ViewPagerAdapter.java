package com.kowama.adapters;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.kowama.R;
import com.kowama.fragments.LearningLeaderFragment;
import com.kowama.fragments.SkillIqFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private Context context;
    public ViewPagerAdapter(Context context, FragmentManager fm, int behavior) {
        super(fm, behavior);
        this.context = context;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0 :
                return LearningLeaderFragment.newInstance();
            case 1:
                return SkillIqFragment.newInstance();
        }
        return null;
    }

    public CharSequence getPageTitle(int position) {
       String titles[]= context.getResources().getStringArray(R.array.tab_titles);
        return titles[position];
    }


    @Override
    public int getCount() {
        return 2;
    }
}
