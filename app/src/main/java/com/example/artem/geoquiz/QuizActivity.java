package com.example.artem.geoquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {
    private Button mTrueButton;
    private Button mFalseButton;
    private Button mCheatButton;
    private ImageButton mNextButton;
    private ImageButton mPrevButton;
    private TextView mQuestionTextView;
    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index";
    private static final String CHEAT = "cheat";
    private static final int REQUEST_CODE_CHEAT = 0;
    private static int messageResId;
    private static boolean mIsCheater; //
    private static final String CURRENT_RESULT = "request";
    private int mCurrentIndex = 0;
    private int mCurrentResult = 0;

    private static Question[] mQuestionBank = new Question[]{
            new Question(R.string.question_australia, true),
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, false),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_asia, true)
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_CODE_CHEAT) {
            if (data == null) return;
//            mIsCheater = CheatActivity.wasAnswerShown(data);
            mQuestionBank[mCurrentIndex].setIsCheat(CheatActivity.wasAnswerShown(data));
            mIsCheater = mQuestionBank[mCurrentIndex].getIsCheat();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG, "onSaveInstance");
        outState.putInt(KEY_INDEX, mCurrentIndex);
        outState.putInt(CURRENT_RESULT, mCurrentResult);
        outState.putBoolean(CHEAT, mIsCheater);

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_quiz);

        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
            System.out.println("mCurrentIndex: " + mCurrentIndex);
            mCurrentResult = mQuestionBank[mCurrentIndex].getResult();
            System.out.println("mCurrentResult: " + mCurrentResult);
            mIsCheater = mQuestionBank[mCurrentIndex].getIsCheat(); //TODO реализовать сохранение
            System.out.println("mIsCheater " + mIsCheater);
        }

        mQuestionTextView = findViewById(R.id.question_text_view);
        mQuestionTextView.setOnClickListener(event -> {
            getCurrentIndex();
        });

        mTrueButton = findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(view -> {
            blockedButton();
            checkAnswer(true);
        });

        mFalseButton = findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(view -> {
            blockedButton();
            checkAnswer(false);
        });

        mNextButton = findViewById(R.id.next_button);
        mNextButton.setOnClickListener(view -> {
//            mIsCheater = false;
            getCurrentIndex();
        });

        mPrevButton = findViewById(R.id.prev_button);
        mPrevButton.setOnClickListener(event -> {
//            mIsCheater = false;
            if (0 < mCurrentIndex && mCurrentIndex < mQuestionBank.length) {
                System.out.println(mCurrentIndex);
                mCurrentIndex = (mCurrentIndex - 1) % mQuestionBank.length;
                updateQuestion();
            } else {
                Toast.makeText(this, "Это первый вопрос", Toast.LENGTH_SHORT).show();
            }
        });

        mCheatButton = findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(event -> {
            boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
            Intent intent = CheatActivity.newIntent(QuizActivity.this, answerIsTrue);
            startActivityForResult(intent, REQUEST_CODE_CHEAT);
        });

        updateQuestion();
    }

    private void updateQuestion() {
        Log.d(TAG, "Updating question text", new Exception());
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        if (mQuestionBank[mCurrentIndex].getResult() != 0) {
            blockedButton();
            mQuestionTextView.setText(question);
            Toast.makeText(QuizActivity.this, "На этот вопрос уже ответили", Toast.LENGTH_SHORT).show();
        } else {
            mTrueButton.setEnabled(true);
            mFalseButton.setEnabled(true);
            mQuestionTextView.setText(question);
        }
    }

    private void blockedButton() {
        mTrueButton.setEnabled(false);
        mFalseButton.setEnabled(false);
    }

    private void checkAnswer(boolean userPressedTrue) {
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
        messageResId = 0;

        if (mIsCheater) {
            messageResId = R.string.judgment_toast;
            mQuestionBank[mCurrentIndex].setResult(2);
            mQuestionBank[mCurrentIndex].setIsCheat(mIsCheater);
            System.out.println("result: " + mQuestionBank[mCurrentIndex].getResult() + "\nis cheat: " + mQuestionBank[mCurrentIndex].getIsCheat());
        } else {
            if (userPressedTrue == answerIsTrue) {
                messageResId = R.string.correct_toast;
                mQuestionBank[mCurrentIndex].setResult(1);
            } else {
                messageResId = R.string.incorrect_toast;
                mQuestionBank[mCurrentIndex].setResult(2);
            }
        }

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
    }

    private void getCurrentIndex() {
        int countTrue = 0;
        int countFalse = 0;
        mIsCheater = false;

        if (0 <= mCurrentIndex && mCurrentIndex < mQuestionBank.length - 1) {
            mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
            updateQuestion();
        } else {
            for (int i = 0; i < mQuestionBank.length; i++) {
                if (mQuestionBank[i].getResult() == 1) {
                    countTrue++;
                } else if (mQuestionBank[i].getResult() == 2) {
                    countFalse++;
                }
            }
            Toast.makeText(QuizActivity.this, "Правильных ответов: " + countTrue +
                            "\nНеправильных ответов: " + countFalse + "\nНе получили ответа " +
                            (mQuestionBank.length - (countFalse + countTrue)) + " вопросов.",
                    Toast.LENGTH_SHORT).show();
        }
    }
}

