package com.example.geoquiz.controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.geoquiz.R;

public class SettingActivity extends AppCompatActivity {
    private Button mButtonSmall;
    private Button mButtonMedium;
    private Button mButtonLarge;
    private float mQuestionSize;
    public static final String EXTRA_QUESTION_TEXT_VIEW_SIZE="com.example.geoquiz.questionSize";
    private static final String BUNDLE_QUESTION_SIZE="questionSize";

/**************************** ON CREATE *********************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState!=null){
            mQuestionSize=savedInstanceState.getFloat(BUNDLE_QUESTION_SIZE);
            setShownAnswerResult();
        }
        setContentView(R.layout.activity_setting);
        findview();
        setListener();

    }
    /**************** ON SAVE INSTANCE *********************/
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putFloat(BUNDLE_QUESTION_SIZE, mQuestionSize);

    }
    /***************************** FIND VIEW **********************/
    private void findview() {
        mButtonSmall=findViewById(R.id.btn_small);
        mButtonMedium=findViewById(R.id.btn_medium);
        mButtonLarge=findViewById(R.id.btn_large);
    }
    /********************************** SET LISTENER *************/

    private void setListener() {
        mButtonSmall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mQuestionSize=14;
                setShownAnswerResult();
            }
        });
        mButtonMedium.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               mQuestionSize=18;
               setShownAnswerResult();
           }
       });
        mButtonLarge.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               mQuestionSize=26;
               setShownAnswerResult();
           }
       });
    }
    /********************* SET SHOWN ANSWER RESULT ******************/

    private void setShownAnswerResult() {
        Intent intent=new Intent();
        intent.putExtra(EXTRA_QUESTION_TEXT_VIEW_SIZE,mQuestionSize);
        setResult(RESULT_OK,intent);
    }

}