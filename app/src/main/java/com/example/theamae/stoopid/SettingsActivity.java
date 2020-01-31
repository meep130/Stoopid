
package com.example.theamae.stoopid;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {

    private ImageButton back;
    private Switch music;
    private BackgroundSoundService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        back = (ImageButton) findViewById(R.id.back_btn);
        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                setResult(Activity.RESULT_CANCELED, new Intent(new Intent(SettingsActivity.this, MainMenuActivity.class)));
                finish();
            }
        });

        music = (Switch) findViewById(R.id.music_switch);
        music.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                } else {
                    // The toggle is disabled
                }
            }
        });
    }

    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        Toast.makeText(this, "The Switch is " + (isChecked ? "on" : "off"),
                Toast.LENGTH_SHORT).show();
        if(isChecked) {
            //do stuff when Switch is ON
            Toast.makeText(this, "The Switch is " + (isChecked ? "on" : "off"),
                    Toast.LENGTH_SHORT).show();
        } else {
            //do stuff when Switch if OFF
        }
    }
}
