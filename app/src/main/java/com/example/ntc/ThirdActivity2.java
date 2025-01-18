package com.example.ntc;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class ThirdActivity2 extends AppCompatActivity {

    String buttonvalue;
    Button start;
    private CountDownTimer countDownTimer;
    TextView mtext;
    private boolean MTimeRunning;
    private long MTimeLeft;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_third);

        Intent intent = getIntent();
        buttonvalue = intent.getStringExtra("value");
        int intvalue = Integer.parseInt(buttonvalue);
        switch (intvalue) {
            case 1:
                setContentView(R.layout.activity_bow2);
                break;
            case 2:
                setContentView(R.layout.activity_bridge2);
                break;
            case 3:
                setContentView(R.layout.activity_chair2);
                break;
            case 4:
                setContentView(R.layout.activity_child2);
                break;
            case 5:
                setContentView(R.layout.activity_cobbler2);
                break;
            case 6:
                setContentView(R.layout.activity_cow2);
                break;
            case 7:
                setContentView(R.layout.activity_playji2);
                break;
            case 8:
                setContentView(R.layout.activity_pausseji2);
                break;
            case 9:
                setContentView(R.layout.activity_plank2);
                break;
            case 10:
                setContentView(R.layout.activity_crunches2);
                break;
            case 11:
                setContentView(R.layout.activity_situp2);
                break;
            case 12:
                setContentView(R.layout.activity_rotation2);
                break;
            case 13:
                setContentView(R.layout.activity_twist2);
                break;
            case 14:
                setContentView(R.layout.activity_windmill2);
                break;
            case 15:
                setContentView(R.layout.activity_legup2);
                break;
        }

        start = findViewById(R.id.Startbutton);
        mtext = findViewById(R.id.time);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MTimeRunning) {
                    stoptimer();
                } else {
                    startTimer();
                }
            }
        });
    }

    private void stoptimer() {
        countDownTimer.cancel();
        MTimeRunning = false;
        start.setText("START");
    }

    private void startTimer() {
        CharSequence value1 = mtext.getText();
        String num1 = value1.toString();
        String num2 = num1.substring(0, 2);
        String num3 = num1.substring(3, 5);

        int number = Integer.parseInt(num2) * 60 + Integer.parseInt(num3);
        MTimeLeft = number * 1000;
        countDownTimer = new CountDownTimer(MTimeLeft, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                MTimeLeft = millisUntilFinished;
                updateTimer();
            }

            @Override
            public void onFinish() {
                int newvalue = Integer.parseInt(buttonvalue) + 1;
                if (newvalue <= 7) {
                    Intent intent = new Intent(ThirdActivity2.this, ThirdActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.putExtra("value", String.valueOf(newvalue));
                    startActivity(intent);
                } else {
                    newvalue = 1;
                    Intent intent = new Intent(ThirdActivity2.this, ThirdActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.putExtra("value", String.valueOf(newvalue));
                    startActivity(intent);
                }
            }
        }.start();
        start.setText("Pause");
        MTimeRunning = true;
    }

    private void updateTimer() {
        int minutes = (int) MTimeLeft / 60000;
        int seconds = (int) MTimeLeft % 60000 / 1000;
        String timeLeftText = "";

        if (minutes < 10) timeLeftText += "0";
        timeLeftText += minutes + ":";

        if (seconds < 10) timeLeftText += "0";
        timeLeftText += seconds;

        mtext.setText(timeLeftText);
    }
}
