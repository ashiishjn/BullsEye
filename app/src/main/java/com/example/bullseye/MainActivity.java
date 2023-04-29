package com.example.bullseye;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    int random_number;
    public static MediaPlayer music;
    ImageView emoji;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        playMusic(R.raw.background_music);
        generateRandom();
    }

    public void playMusic(int id)
    {
        music = MediaPlayer.create(MainActivity.this, id);
        music.setLooping(true);
        music.start();
    }

    public void generateRandom()
    {
        LinearLayout ll = findViewById(R.id.page);
        ll.setBackgroundResource(R.drawable.s1);
        EditText ed = findViewById(R.id.number_input);
        Button bt = findViewById(R.id.button);
        random_number = (int)(Math.random() * 20) + 1;
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ll.setBackgroundResource(R.drawable.guessit);
                ed.setVisibility(View.VISIBLE);
                bt.setVisibility(View.VISIBLE);
            }
        }, 2000);
    }

    public void Tap(View v)
    {
        platTapSound();
        InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(),0);
        LinearLayout ll = findViewById(R.id.page);
        Button bt = findViewById(R.id.button);
        EditText input_number = findViewById(R.id.number_input);
        String number = input_number.getText().toString();
        input_number.setText("");
        if(number.length() == 0)
        {
            Toast.makeText(this, "Please enter a number", Toast.LENGTH_SHORT).show();
            return;
        }
        input_number.setVisibility(View.INVISIBLE);
        bt.setVisibility(View.INVISIBLE);
        int num = Integer.parseInt(number);
        if(num == random_number)
        {
            ll.setBackgroundResource(R.drawable.perfect);
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    generateRandom();
                }
            }, 2000);
        }
        else if(num>random_number)
        {
                ll.setBackgroundResource(R.drawable.godown);
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ll.setBackgroundResource(R.drawable.guessit);
                        input_number.setVisibility(View.VISIBLE);
                        bt.setVisibility(View.VISIBLE);
                    }
                }, 1500);
        }
        else
        {
            ll.setBackgroundResource(R.drawable.goup);
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    ll.setBackgroundResource(R.drawable.guessit);
                    input_number.setVisibility(View.VISIBLE);
                    bt.setVisibility(View.VISIBLE);
                }
            }, 1500);
        }
    }

    MediaPlayer media;
    public void platTapSound()
    {
        media= MediaPlayer.create(MainActivity.this, R.raw.tap);
        media.start();
        media.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                mp.reset();
                mp.release();
            }
        });
    }

    @Override
    protected void onPause(){
        super.onPause();
        music.pause();
    }

    @Override
    protected void onResume(){
        super.onResume();
        music.start();
    }
}