package com.example.geoquiz.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.geoquiz.R;

public class GameOverActivity extends AppCompatActivity {
    public static final String EXTRA_CURRENT_INDEX = "currentIndex";
    private ImageButton mButtonReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        findView();
        setListener();
    }


    private void findView() {
        mButtonReset = findViewById(R.id.imgBtn_reset);

    }
    private void setListener() {
        mButtonReset.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent=new Intent();
            intent.putExtra(EXTRA_CURRENT_INDEX,0);
           finish();

        }
    });
    }



}