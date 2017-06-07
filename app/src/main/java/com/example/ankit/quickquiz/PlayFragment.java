package com.example.ankit.quickquiz;


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
public class PlayFragment extends Fragment {
    Button playButton;

    public PlayFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_play, container, false);
        playButton = (Button) view.findViewById(R.id.bt_play_game);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), DualBattleQuestionsActivity.class));
                //startActivity(new Intent(getActivity(), LoadingQuestionsActivity.class));
            }
        });
    }
}
