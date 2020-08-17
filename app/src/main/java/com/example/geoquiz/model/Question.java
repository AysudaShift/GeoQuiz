package com.example.geoquiz.model;

public class Question {
    private int mQuestionTextResId;
    private boolean mIsAnswerTrue;

    /***************************** CONSTRUCTOR *********************/
    public Question(int questionTextResId, boolean isAnswerTrue) {
        mQuestionTextResId = questionTextResId;
        mIsAnswerTrue = isAnswerTrue;
    }



    /********************************* GETTER SETTER ***************/

    public int getQuestionTextResId() {

        return mQuestionTextResId;
    }

    public boolean isAnswerTrue() {

        return mIsAnswerTrue;
    }

    public void setAnswerTrue(boolean answerTrue) {

        mIsAnswerTrue = answerTrue;
    }

}
