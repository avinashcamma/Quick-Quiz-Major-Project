package com.example.ankit.quickquiz;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class PracticeFragment extends Fragment {
    Button mEnglishButton, mGKButton, mQuantativeButton;

    Context mContext;

    public PracticeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mContext = getContext();
        View view = inflater.inflate(R.layout.fragment_practice, container, false);
        mEnglishButton = (Button) view.findViewById(R.id.bt_english);
        mGKButton = (Button) view.findViewById(R.id.bt_gk);
        mQuantativeButton = (Button) view.findViewById(R.id.bt_quantative);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mEnglishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext, EnglishPracticeActivity.class));
            }
        });
        mQuantativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext, ReasoningPracticeActivity.class));

            }
        });
        mGKButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext, GKPracticeActivity.class));

            }
        });
    }
}
