package xyz.open032.timernext;

import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView mTextViewCountDown;
    private Button mButtonStartPause;

    private CountDownTimer mCountDownTimer;

    private boolean mTimerRunning;

    private long mTime = 5000;

    private SoundPool soundPool;
    private int sound1, sound2, sound3, sound4, sound5, sound6, sound7, sound8;

    private int treti = 0;
    private int mNumMethod = 0;
    private int mTimeOne = 5000;
    private int mTimeTwo = 10000;
    private EditText mOneET;
    private EditText mTwoET;
    private PowerManager.WakeLock wl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            AudioAttributes audioAttributes = new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION).build();

            soundPool = new SoundPool.Builder().setMaxStreams(6).setAudioAttributes(audioAttributes).build();
        } else {
            soundPool = new SoundPool(6, AudioManager.STREAM_MUSIC, 0);
        }
        sound1 = soundPool.load(this, R.raw.one, 1);
        sound2 = soundPool.load(this, R.raw.two, 1);
        sound3 = soundPool.load(this, R.raw.three, 1);
        sound4 = soundPool.load(this, R.raw.podnimay, 1);
        sound5 = soundPool.load(this, R.raw.fixid, 1);
        sound6 = soundPool.load(this, R.raw.zakanchvay, 1);
        sound7 = soundPool.load(this, R.raw.treti, 1);
        sound8 = soundPool.load(this, R.raw.dvetreti, 1);

        mTextViewCountDown = findViewById(R.id.text_view_countdown);

        mOneET = findViewById(R.id.editText5);
        mTwoET = findViewById(R.id.editText4);

        mButtonStartPause = findViewById(R.id.button_start_pause);

        mButtonStartPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTimerRunning) {                                               
                    pauseTimer();
                } else {
                    startTimer();
                }
            }
        });
        updateCountDownText();
    }
    public void onClickFiveTen(View view) {
        mTimeOne = 5000;
        mTimeTwo = 10000;
        mOneET.setText(null);
        mTwoET.setText(null);
        mOneET.setCursorVisible(false);
        mTwoET.setCursorVisible(false);
    }
    public void onClickTenFifteen(View view) {
        mTimeOne = 10000;
        mTimeTwo = 15000;
        mOneET.setText(null);
        mTwoET.setText(null);
        mOneET.setCursorVisible(false);
        mTwoET.setCursorVisible(false);
    }
    private void startTimer() {

        treti++;
        if (treti == 15){
            soundPool.play(sound7, 1, 1, 0, 0, 1);
        }
        if (treti == 30){
            soundPool.play(sound8, 1, 1, 0, 0, 1);
        }
        if (mOneET.length() != 0 ){
            mTimeOne = Integer.parseInt(mOneET.getText().toString()) * 1000;
        }
        if (mTwoET.length() != 0 ){
            mTimeTwo = Integer.parseInt(mTwoET.getText().toString()) * 1000;
        }
        if (mNumMethod >= 15){
            mNumMethod = 0;
        }
        if (mNumMethod == 0 || mNumMethod == 3 || mNumMethod == 6 || mNumMethod == 9 || mNumMethod == 12){
            mTime = mTimeOne;
            soundPool.play(sound4, 1, 1, 0, 0, 1);
        }
        if (mNumMethod == 1 || mNumMethod == 4 || mNumMethod == 7 || mNumMethod == 10 || mNumMethod == 13){
            mTime = mTimeTwo;
            soundPool.play(sound5, 1, 1, 0, 0, 1);
        }
        if (mNumMethod == 2 || mNumMethod == 5 || mNumMethod == 8 || mNumMethod == 11 || mNumMethod == 14){
            mTime = mTimeOne;
            soundPool.play(sound6, 1, 1, 0, 0, 1);
        }

        mNumMethod++;
        mCountDownTimer = new CountDownTimer(mTime, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTime = millisUntilFinished;
                updateCountDownText();
                if (millisUntilFinished <= 4000 && millisUntilFinished >= 3000){
                    soundPool.play(sound3, 1, 1, 0, 0, 1);
                }
                if (millisUntilFinished <= 3000 && millisUntilFinished >= 2000){
                    soundPool.play(sound2, 1, 1, 0, 0, 1);
                }
                if (millisUntilFinished <= 2000 && millisUntilFinished >= 1000){
                    soundPool.play(sound1, 1, 1, 0, 0, 1);
                }
            }

            @Override
            public void onFinish() {
                mButtonStartPause.setText("Start");
                startTimer();

            }
        }.start();

        mTimerRunning = true;
        mButtonStartPause.setText("pause");
    }

    private void pauseTimer() {
        mCountDownTimer.cancel();
        mTimerRunning = false;
        mButtonStartPause.setText("Start");
    }


    private void updateCountDownText() {
        int minutes = (int) (mTime / 1000) / 60;
        int seconds = (int) (mTime / 1000) % 60;

        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

        mTextViewCountDown.setText(timeLeftFormatted);
    }
}
