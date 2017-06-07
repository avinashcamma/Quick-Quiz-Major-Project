package com.example.ankit.quickquiz;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final String ADD_QUESTION_SINGLE_TIME_PREFRENCE = "addQuestionIdsPrefrence";


    private boolean editQuestionIdsSingleTime = false;

    ViewPager mViewPager;
    TabLayout mTabLayout;

    SharedPreferences mPreferences;
    SharedPreferences.Editor mEditor;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Binding Via ButterKnife
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mTabLayout = (TabLayout) findViewById(R.id.main_tab_layout);

        mViewPager.setAdapter(new GamePagerAdapter(getSupportFragmentManager(), this));
        mTabLayout.setupWithViewPager(mViewPager);


        //Set icons for the tabs
        mTabLayout.getTabAt(0).setIcon(R.drawable.ic_vector_home);
        mTabLayout.getTabAt(1).setIcon(R.drawable.ic_vector_play);
        mTabLayout.getTabAt(2).setIcon(R.drawable.iv_vector_account_profile);

        //Select 2nd item by default
        mTabLayout.getTabAt(1).select();
        mPreferences = getSharedPreferences(ADD_QUESTION_SINGLE_TIME_PREFRENCE, Context.MODE_PRIVATE);
        mEditor = mPreferences.edit();


    }


    @Override
    protected void onStart() {
        super.onStart();


    }
}














































