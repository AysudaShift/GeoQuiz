package com.example.geoquiz.controller;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
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
    public static final int REQUEST_CODE_CHEAT = 0;
    public static final int REQUEST_CODE_SETTING = 1;
    public static final String EXTRA_QUESTION_ANSWER = "com.example.geoquiz.questionAnswer";

    private static final String BUNDLE_KEY_CURRENT_INDEX = "currentIndex";
    private static final String BUNDLE_KEY_SCORE = "score";
    private static final String BUNDLE_KEY_GAME_STATE = "gameState";
    private static final String BUNDLE_KEY_IS_ANSWERED = "isAnswered";
    private static final String BUNDLE_KEY_IS_CHEATER = "isCheater";
    private static final String BUNDLE_USER_PRESSED = "userPressed";
    private static final String BUNDLE_QUESTION_TEXT_VIEW_SIZE = "questionTextViewSize";
    private static final String BUNDLE_QUESTION_TEXT_VIEW_COLOR = "questionTextViewColor";
    private ImageButton mButtonTrue;
    private ImageButton mButtonFalse;
    private ImageButton mButtonNext;
    private ImageButton mButtonPrevious;
    private ImageButton mButtonFirst;
    private ImageButton mButtonLast;
    private ImageButton mButtonCheat;
    private ImageButton mButtonSetting;

    //LinearLayout mLinearLayoutMain;

    private TextView mQuestionTextView;
    private EditText mScoreEditText;

    private int mCurrentIndex = 0;
    private int mGameState = 0;
    private int mScore = 0;
    private int mQuestionTxtViewColor = Color.BLACK;
    private float mQuestionTxtViewSize = 24;
    private boolean mUserPressed;

    private boolean[] mIsAnswered = new boolean[6];
    private boolean[] mIsCheater = new boolean[6];

    private Question[] mQuestionBank = {
            new Question(R.string.question_australia, false),
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, true),
            new Question(R.string.question_americas, false),
            new Question(R.string.question_asia, false)
    };


    /**************************** ON CREATE *****************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {

            mCurrentIndex = savedInstanceState.getInt(BUNDLE_KEY_CURRENT_INDEX, 0);
            mGameState = savedInstanceState.getInt(BUNDLE_KEY_GAME_STATE, 0);

            mScore = savedInstanceState.getInt(BUNDLE_KEY_SCORE, 0);
            mIsAnswered = savedInstanceState.getBooleanArray(BUNDLE_KEY_IS_ANSWERED);
            mIsCheater = savedInstanceState.getBooleanArray(BUNDLE_KEY_IS_CHEATER);
            mUserPressed = savedInstanceState.getBoolean(BUNDLE_USER_PRESSED);
            mQuestionTxtViewSize = savedInstanceState.getFloat(BUNDLE_QUESTION_TEXT_VIEW_SIZE);
            mQuestionTxtViewColor = savedInstanceState.getInt(BUNDLE_QUESTION_TEXT_VIEW_COLOR);
        }
        setContentView(R.layout.activity_quiz);
        findView();


        setListener();

        currentQuestion();

    }

    /******************************** ON SAVE INSTANCE STATE *******************/
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(BUNDLE_KEY_CURRENT_INDEX, mCurrentIndex);
        outState.putInt(BUNDLE_KEY_SCORE, mScore);
        outState.putInt(BUNDLE_KEY_GAME_STATE, mGameState);
        outState.putBooleanArray(BUNDLE_KEY_IS_ANSWERED, mIsAnswered);
        outState.putBooleanArray(BUNDLE_KEY_IS_CHEATER, mIsCheater);
        outState.putBoolean(BUNDLE_USER_PRESSED, mUserPressed);

        outState.putFloat(BUNDLE_QUESTION_TEXT_VIEW_SIZE, mQuestionTxtViewSize);

        outState.putInt(BUNDLE_QUESTION_TEXT_VIEW_COLOR, mQuestionTxtViewColor);
    }

    /*************************** ON ACTIVITY RESULT **********************/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null || resultCode != RESULT_OK)
            return;
        if (requestCode == REQUEST_CODE_CHEAT) {

            mIsCheater[mCurrentIndex] =
                    data.getBooleanExtra(CheatActivity.EXTRA_IS_CHEAT, false);
        }
        if (requestCode == REQUEST_CODE_SETTING) {
            mQuestionTxtViewSize =
                    data.getFloatExtra(SettingActivity.EXTRA_QUESTION_TEXT_VIEW_SIZE, 0);
            mQuestionTextView.setTextSize(mQuestionTxtViewSize);
        }
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
        // mQuestionTextView.setTextColor(mQuestionTxtViewColor);
        mScoreEditText = findViewById(R.id.editTextNumber_score);

        mButtonCheat = findViewById(R.id.imgBtn_cheat);
        mButtonSetting = findViewById(R.id.imgBtn_setting);
    }

    /*********************** SET LISTENER **********************************************/
    private void setListener() {


        /**************** TRUE ************/
        mButtonTrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mIsAnswered[mCurrentIndex] == false) {
                    mUserPressed = true;

                    checkAnswer();
                    mIsAnswered[mCurrentIndex] = true;
                    mGameState++;
                    myButtonsDisable();
                }

            }
        });
        /**************** FALSE *************/

        mButtonFalse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mIsAnswered[mCurrentIndex] == false) {
                    mUserPressed = false;

                    checkAnswer();
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
        /************ CHEAT *********/
        mButtonCheat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QuizActivity.this, CheatActivity.class);
                intent.putExtra(EXTRA_QUESTION_ANSWER, mQuestionBank[mCurrentIndex].isAnswerTrue());

                startActivityForResult(intent, REQUEST_CODE_CHEAT);
            }
        });
        /************* SETTING *************/

        mButtonSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QuizActivity.this, SettingActivity.class);
                startActivityForResult(intent, REQUEST_CODE_SETTING);
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


    /******************** UPDATE QUESTION *****************/

    private void updateQuestion() {
        if (mGameState < 6) {

            int questionTextResId = mQuestionBank[mCurrentIndex].getQuestionTextResId();
            mQuestionTextView.setText(questionTextResId);
            mQuestionTextView.setTextSize(mQuestionTxtViewSize);

            mQuestionTextView.setTextColor(Color.BLACK);
            mQuestionTxtViewColor=Color.BLACK;

            if (mIsAnswered[mCurrentIndex] == false) {
                myButtonsEnable();
            }

        } else if (mGameState == 6) {
            // gameOver();


        }
    }
 /******************** CURRENT QUESTION *****************/

    private void currentQuestion() {
        if (mGameState < 6) {

            int questionTextResId = mQuestionBank[mCurrentIndex].getQuestionTextResId();
            mQuestionTextView.setText(questionTextResId);
            mQuestionTextView.setTextSize(mQuestionTxtViewSize);

            mQuestionTextView.setTextColor(mQuestionTxtViewColor);

            if (mIsAnswered[mCurrentIndex] == false) {
                myButtonsEnable();
            }

        } else if (mGameState == 6) {
            // gameOver();


        }
    }

    /************************** GAME OVER ******************************/
    private void gameOver() {
        Intent intent = new Intent(QuizActivity.this, GameOverActivity.class);
        startActivity(intent);


    }

    /*********************** CHECK ANSWER ************/

    private void checkAnswer() {
        if (mIsCheater[mCurrentIndex]) {
            Toast.makeText(QuizActivity.this,
                    R.string.toast_cheat, Toast.LENGTH_LONG).show();

        } else {
            if (mQuestionBank[mCurrentIndex].isAnswerTrue() == mUserPressed) {
                updateScore();
                Toast toast = Toast.makeText(QuizActivity.this,
                        R.string.toast_true, Toast.LENGTH_LONG);

                View toastView = toast.getView();

                TextView toastMessage = (TextView) toastView.findViewById(android.R.id.message);
                toastMessage.setTextSize(18);
                toastMessage.setTextColor(Color.GREEN);
                mQuestionTextView.setTextColor(Color.GREEN);
                mQuestionTxtViewColor = Color.GREEN;
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
                mQuestionTxtViewColor = Color.RED;
                toast.show();

            }

        }


    }

    /******************************* UPDATE SCORE *************************/

    private void updateScore() {
        mScore++;

        mScoreEditText.setText(String.valueOf(mScore));
    }


}