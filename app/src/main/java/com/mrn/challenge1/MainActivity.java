package com.mrn.challenge1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.SystemClock;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button button_start, button_click;
    private TextView textView;
    private int ClicksCount = 0;
    public boolean running;
    public Chronometer timer;
    public double CPSresult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button_start = (Button) findViewById(R.id.button_start);
        button_click = (Button) findViewById(R.id.button_click);
        textView = (TextView) findViewById(R.id.tv_result);
        timer = (Chronometer) findViewById(R.id.time);
        textView.setMovementMethod(new ScrollingMovementMethod());
        textView.setText("");
        timer.setFormat("Time: %s");

        timer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                if ((SystemClock.elapsedRealtime() - timer.getBase()) >= 11000){
                    timer.setBase(SystemClock.elapsedRealtime());
                    CPSresult = ClicksCount / 10;
                    String result = "You have " + CPSresult + " CPS";
                    textView.setText(result);
                    running = false;
                    timer.stop();
                    ClicksCount = 0;
                }
            }
        });

        View.OnClickListener startButtonOnClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText("");
                timeStart();
            }
        };

        View.OnClickListener countButtonOnClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClicksCount += 1;
            }
        };

        button_start.setOnClickListener(startButtonOnClick);
        button_click.setOnClickListener(countButtonOnClick);

    }

    public void timeStart(){
        if (!running){
            ClicksCount = 0;
            timer.setBase(SystemClock.elapsedRealtime());
            timer.start();
            running = true;
        }
    }

}
