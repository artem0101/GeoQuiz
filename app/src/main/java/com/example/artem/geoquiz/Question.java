package com.example.artem.geoquiz;

public class Question {
    private int mTextResId;
    private boolean mAnswerTrue;
    private int result;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public Question(int mTextResId, boolean mAnswerTrue, int result) {

        this.mTextResId = mTextResId;
        this.mAnswerTrue = mAnswerTrue;
        this.result = result;
    }

    public Question(int TextResId, boolean AnswerTrue) {
        this.mTextResId = TextResId;
        this.mAnswerTrue = AnswerTrue;
        this.result = 0;

    }

    public int getTextResId() {
        return mTextResId;
    }

    public void setTextResId(int textResId) {
        this.mTextResId = textResId;
    }

    public boolean isAnswerTrue() {
        return mAnswerTrue;
    }

    public void setAnswerTrue(boolean answerTrue) {
        this.mAnswerTrue = answerTrue;
    }
}
