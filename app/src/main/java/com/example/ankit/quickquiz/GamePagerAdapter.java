package com.example.ankit.quickquiz;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Ankit on 2/23/2017.
 */
public class GamePagerAdapter extends FragmentStatePagerAdapter {
    Context mContext;
    public static final String LOG_TAG = GamePagerAdapter.class.getName();
    public static final int COUNT = 3;
    public static final int PRACTICE_SCREEN = 0;
    public static final int PLAY_SCREEN = 1;
    public static final int STATISTICS_SCREEN = 2;


    public GamePagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case PRACTICE_SCREEN:

                return new PracticeFragment();
            case PLAY_SCREEN:

                return new PlayFragment();
            case STATISTICS_SCREEN:
                return new ProfileFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Home";
            case 1:
                return "Play";
            case 2:
                return "Profile";
        }
        return null;
    }
}
