package com.example.geoquiz.controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.geoquiz.R;
import com.example.geoquiz.model.Question;


public class QuizActivity extends AppCompatActivity {
    public static final String TAG = "Listener ";
    private static final String BUNDLE_KEY_CURRENT_INDEX ="currentIndex" ;
    private static final String BUNDLE_KEY_SCORE = "score";
    private static final String BUNDLE_KEY_GAME_STATE ="gameState" ;
    private static final String BUNDLE_KEY_IS_ANSWERED ="isAnswered" ;
    private ImageButton mButtonTrue;
    private ImageButton mButtonFalse;
    private ImageButton mButtonNext;
    private ImageButton mButtonPrevious;
    private ImageButton mButtonFirst;
    private ImageButton mButtonLast;
    private ImageButton mButtonReset;
    //LinearLayout mLinearLayoutMain;

    private TextView mQuestionTextView;
    private EditText mScoreEditText;

    private int mCurrentIndex = 0;
    private int mGameState = 0;
    private int mScore = 0;

    private boolean[] mIsAnswered = new boolean[6];
    private Question[] mQuestionBank = {
            new Question(R.string.question_australia, false),
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, true),
            new Question(R.string.question_americas, false),
            new Question(R.string.question_asia, false)
    };


    /**************************** ONCREATE *****************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            Log.d(TAG, "savedInstanceState: " + savedInstanceState);

            mCurrentIndex = savedInstanceState.getInt(BUNDLE_KEY_CURRENT_INDEX, 0);
            mGameState=savedInstanceState.getInt(BUNDLE_KEY_GAME_STATE,0);
            mScore =savedInstanceState.getInt(BUNDLE_KEY_SCORE,0);
            mIsAnswered= savedInstanceState.getBooleanArray(BUNDLE_KEY_IS_ANSWERED);

        }
        setContentView(R.layout.activity_quiz);
        findView();
        setListener();
        updateQuestion();

    }

    /******************************** ON SAVE INSTANCE STATE *******************/
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        //Log.d(TAG, "onSaveInstanceState: " + mCurrentIndex);

        outState.putInt(BUNDLE_KEY_CURRENT_INDEX, mCurrentIndex);
        outState.putInt(BUNDLE_KEY_SCORE, mScore);
        outState.putInt(BUNDLE_KEY_GAME_STATE, mGameState);
        outState.putBooleanArray(BUNDLE_KEY_IS_ANSWERED, mIsAnswered);
    }

    /*************************** FIND VIEW **********************************************/

    @SuppressLint("WrongViewCast")
    private void findView() {
        mButtonTrue = findViewById(R.id.imgBtn_true);
        mButtonFalse = findViewById(R.id.imgBtn_false);

        mButtonNext = findViewById(R.id.imgBtn_next);
        mButtonPrevious = findViewById(R.id.imgBtn_previous);

        mButtonFirst = findViewById(R.id.imgBtn_first);
        mButtonLast = findViewById(R.id.imgBtn_last);

        mQuestionTextView = findViewById(R.id.txtview_question_text);
        mScoreEditText = findViewById(R.id.editTextNumber_score);



       //mLinearLayoutMain = findViewById(R.id.linearLayout_main);




    }

    /*********************** SET LISTENER **********************************************/
    private void setListener() {

        /**************** TRUE ************/
        mButtonTrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mIsAnswered[mCurrentIndex] == false) {

                    checkAnswer(true);
                    mIsAnswered[mCurrentIndex] = true;
                    mGameState++;
                    myButtonsDisable();
               }


                /*Toast toast=new Toast(MainActivity.this);
                mToastTxtTrue.setText(R.string.toast_true);
                mToastTxtTrue.setTextColor(Color.GREEN);
                toast.setView(mToastTxtTrue);
                toast.setDuration(Toast.LENGTH_LONG);
                toast.show();
*/


            }
        });
        /**************** FALSE *************/

        mButtonFalse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if (mIsAnswered[mCurrentIndex] == false) {

                    checkAnswer(false);
                    mIsAnswered[mCurrentIndex] = true;
                    mGameState++;
                    myButtonsDisable();
                }


            }
        });
        /*************** NEXT *************/
        mButtonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                updateQuestion();
            }
        });
        /************* PREVIOUS **********/
        mButtonPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentIndex = (mCurrentIndex - 1 + mQuestionBank.length) % mQuestionBank.length;
                updateQuestion();
            }
        });
        /************** FIRST ************/
        mButtonFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentIndex = 0;
                updateQuestion();
            }
        });
        /************* LAST ***********/
        mButtonLast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentIndex = 5;
                updateQuestion();
            }
        });
    }

    /********************* BUTTON DISABLE ******************/

    private void myButtonsDisable() {
        mButtonTrue.setEnabled(false);
        mButtonFalse.setEnabled(false);
    }

    /********************* BUTTON ENABLE ******************/

    private void myButtonsEnable() {
        mButtonTrue.setEnabled(true);
        mButtonFalse.setEnabled(true);
    }


    /********************************** UPDATE QUESTION *****************/

    private void updateQuestion() {
        if (mGameState < 6) {

            int questionTextResId = mQuestionBank[mCurrentIndex].getQuestionTextResId();
            mQuestionTextView.setText(questionTextResId);

            if (mIsAnswered[mCurrentIndex] == false) {
                myButtonsEnable();
            }

        } else if (mGameState == 6) {
            gameOver();


        }
    }
/************************** GAME OVER ******************************/
    private void gameOver() {
        setContentView(R.layout.game_over);
        mButtonReset = findViewById(R.id.imgBtn_reset);
        changeUI();

        mButtonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentIndex = 0;
                setContentView(R.layout.activity_quiz);
                findView();
                setListener();
                updateQuestion();

            }
        });
    }

    /*************************** CHANGE UI ***********************/
    private void changeUI() {
        // mLinearLayoutMain.addView(mButtonReset);
          /* mButtonFalse.setVisibility(View.INVISIBLE);
            mButtonTrue.setVisibility(View.INVISIBLE);

            mButtonNext.setVisibility(View.INVISIBLE);
            mButtonPrevious.setVisibility(View.INVISIBLE);

            mButtonFirst.setVisibility(View.INVISIBLE);
            mButtonLast.setVisibility(View.INVISIBLE);*/

        /* mQuestionTextView.setVisibility(View.INVISIBLE);
*/         /*   //LinearLayout mLinearLayoutScore=findViewById(R.id.linrearLayout_score);
            final LinearLayout mGameOverLinearLayout=new LinearLayout(this);
            mGameOverLinearLayout.setOrientation(LinearLayout.VERTICAL);

            final Button mButtonReset = new Button(this);
            mButtonReset.setText(R.string.reset);

            final LinearLayout mScoreLinearLayout=new LinearLayout(this);
            mScoreLinearLayout.setOrientation(LinearLayout.HORIZONTAL);


            EditText mEditTextNumberScore=new EditText(this);
            TextView mTextViewScore=new TextView(this);
            mTextViewScore.setText(R.string.score_is);
            mScoreLinearLayout.addView(mEditTextNumberScore);
            mScoreLinearLayout.addView(mTextViewScore);

            mGameOverLinearLayout.addView(mButtonReset);
            mGameOverLinearLayout.addView(mScoreLinearLayout);*/
    }

    /*********************** CHECK ANSWER ************/

    private void checkAnswer(boolean userPressed) {

        if (mQuestionBank[mCurrentIndex].isAnswerTrue() == userPressed) {
            updateScore();
            Toast toast = Toast.makeText(QuizActivity.this,
                    R.string.toast_true, Toast.LENGTH_LONG);

            View toastView = toast.getView();

            TextView toastMessage = (TextView) toastView.findViewById(android.R.id.message);
            toastMessage.setTextSize(18);
            toastMessage.setTextColor(Color.GREEN);
            mQuestionTextView.setTextColor(Color.GREEN);
            toast.show();

        } else {

            Toast toast = Toast.makeText(QuizActivity.this,
                    R.string.toast_false, Toast.LENGTH_LONG);

            toast.setGravity(Gravity.TOP, 0, 60);
            View toastView = toast.getView();

            TextView toastMessage = (TextView) toastView.findViewById(android.R.id.message);
            toastMessage.setTextSize(18);
            toastMessage.setTextColor(Color.RED);
            mQuestionTextView.setTextColor(Color.RED);
            toast.show();

        }
    }

    /******************************* UPDATE SCORE *************************/

    private void updateScore() {
        mScore++;

        mScoreEditText.setText(String.valueOf(mScore));
    }
}