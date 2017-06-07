package com.example.ankit.quickquiz;

/*
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.FloatRange;
import android.view.View;

import agency.tango.materialintroscreen.MaterialIntroActivity;
import agency.tango.materialintroscreen.MessageButtonBehaviour;
import agency.tango.materialintroscreen.SlideFragmentBuilder;
import agency.tango.materialintroscreen.animations.IViewTranslation;

public class IntroActivity extends MaterialIntroActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_intro);


        enableLastSlideAlphaExitTransition(false);

        getBackButtonTranslationWrapper()
                .setEnterTranslation(new IViewTranslation() {
                    @Override
                    public void translate(View view, @FloatRange(from = 0, to = 1.0) float percentage) {
                        view.setAlpha(percentage);
                    }
                });

        //FIRST SLIDE
        addSlide(new SlideFragmentBuilder()
                        .backgroundColor(R.color.colorPrimary)
                        .buttonsColor(R.color.colorAccent)
                        .image(R.drawable.large)
                        .title(getString(R.string.app_name))
                        .description(getString(R.string.app_name_desc))
                        .build(),
                new MessageButtonBehaviour(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showMessage("We provide solutions to make you love your work");
                    }
                }, "Work with love"));

        //SECOND SLIDE
        addSlide(new SlideFragmentBuilder()
                .backgroundColor(R.color.second_slide_background)
                .buttonsColor(R.color.colorAccent)
                .image(R.drawable.cute_girls)
                .title("Second Screen \nWant more?")
                .description("Go on")
                .build());

        //THIRD SLIDE
        addSlide(new SlideFragmentBuilder()
                .backgroundColor(R.color.fourth_slide_background)
                .buttonsColor(R.color.colorAccent)
                .image(R.drawable.face)
                .title("3rd Screen \nThat's it")
                .description("Would you join us?")
                .build());
    }

    @Override
    public void onFinish() {
        super.onFinish();
        startActivity(new Intent(this, MainActivity.class));
    }
}
*/
