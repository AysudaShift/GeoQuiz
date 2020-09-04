package com.example.geoquiz.controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.geoquiz.R;

public class CheatActivity extends AppCompatActivity {

    public static final String EXTRA_IS_CHEAT = "com.example.geoquiz.ischeat";
    private static final String BUNDLE_IS_CHEAT="isCheat";
    private static final String BUNDLE_ANSWER_TEXT_VIEW="answer";
    private TextView mTextViewAnswer;
    private String mTextViewAnswerStr;
    private ImageButton mImageButtonShowAnswer;
    private boolean mIsAnswerTrue;
    private boolean mIsCheat;

    /************************** ON CREATE ******************/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {

            mIsCheat = savedInstanceState.getBoolean(BUNDLE_IS_CHEAT);
            setShownAnswerResult();
            mTextViewAnswerStr=savedInstanceState.getString(BUNDLE_ANSWER_TEXT_VIEW);
        }
        setContentView(R.layout.activity_cheat);
        mIsAnswerTrue =
                getIntent().getBooleanExtra(QuizActivity.EXTRA_QUESTION_ANSWER, false);
        findView();
        mTextViewAnswer.setText(mTextViewAnswerStr);
        setListener();


    }
    /**************** ON SAVE INSTANCE *********************/
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putBoolean(BUNDLE_IS_CHEAT, mIsCheat);
        outState.putString(BUNDLE_ANSWER_TEXT_VIEW,String.valueOf(mTextViewAnswer.getText()));

    }
    /***************************** FIND VIEW *****************/
    private void findView() {
        mTextViewAnswer = findViewById(R.id.txtview_answer);
        mImageButtonShowAnswer = findViewById(R.id.imgBtn_show_answer);
    }

    /************************** SET LISTENER ******************/

    private void setListener() {
        mImageButtonShowAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mIsAnswerTrue)
                    mTextViewAnswer.setText(R.string.answer_is_true);
                else
                    mTextViewAnswer.setText(R.string.answer_is_false);
                mIsCheat=true;
                setShownAnswerResult();

            }
        });
    }

    /********************** SET SHOWN ANSWER RESULT *************/

    private void setShownAnswerResult() {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_IS_CHEAT, mIsCheat);
        setResult(RESULT_OK, intent);
    }
}