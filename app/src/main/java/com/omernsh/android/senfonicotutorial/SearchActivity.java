package com.omernsh.android.senfonicotutorial;

import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.omernsh.android.senfonicotutorial.R;


public class SearchActivity extends Noyau {


    private EditText editText;
    private Button button;
    private AnimationDrawable anim;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_search);
        activateToolbarWithHomeEnabled();
        editText = (EditText) findViewById(R.id.editText);
        button = (Button) findViewById(R.id.btnSearch);
        RelativeLayout container = (RelativeLayout) findViewById(R.id.container);

        // initialize the animation
        anim = (AnimationDrawable) container.getBackground();
        anim.setEnterFadeDuration(1000);
        anim.setExitFadeDuration(1000);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.getText().toString() != null) {
                    SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    sharedPref.edit().putString(FLICKR_QUERY, editText.getText().toString()).commit();
                    finish();

                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        // start the animation
        if (anim != null && !anim.isRunning())
            anim.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // stop the animation
        if (anim != null && anim.isRunning())
            anim.stop();
    }

}
