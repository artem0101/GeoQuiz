package com.example.artem.geoquiz;

public class Question {
    private int mTextResId;
    private boolean mAnswerTrue;
    private int result;
    private boolean mIsCheat;

    public Question(int TextResId, boolean AnswerTrue) {
        this.mTextResId = TextResId;
        this.mAnswerTrue = AnswerTrue;
        this.result = 0;
        this.mIsCheat = false; //TODO доработать сохранеие cheat

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

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public boolean getIsCheat() {
        return mIsCheat;
    }

    public void setIsCheat(boolean mIsCheat) {
        this.mIsCheat = mIsCheat;
    }
}
