package com.mrn.challenge1;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button button_start, button_click;
    private TextView textView;
    private double ClicksCount = 0.0;
    public boolean running;
    public Chronometer timer;
    public double CPSresult;
    public String result;
    public final String CPS_FINALE = "TextContent";

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
                    result();
                }
            }
        });

        View.OnClickListener startButtonOnClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setTextColor(Color.parseColor("#b0aeac"));
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
            ClicksCount = 0.0;
            timer.setBase(SystemClock.elapsedRealtime());
            timer.start();
            running = true;
        }
    }

    public void result(){
        CPSresult = ClicksCount / 10;
        if (ClicksCount <= 60) {
            result = "Well..you are an Octopus, smart but not fast\nYou clicked with the speed of " + CPSresult + " CPS\nYou made a total of " + ClicksCount + " clicks in 10 seconds";
        }else if (ClicksCount > 60 ){
            textView.setTextColor(Color.parseColor("#ffffff"));
            result = "Wow..you are a Cheetah.Hail to the king of clicking\nYou clicked with the speed of " + CPSresult + " CPS\nYou made a total of " + ClicksCount + " clicks in 10 seconds";
        }
        textView.setText(result);
        running = false;
        timer.stop();
        ClicksCount = 0.0;
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        textView.setText(savedInstanceState.getString(CPS_FINALE));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState){
        outState.putString(CPS_FINALE, textView.getText().toString());
        super.onSaveInstanceState(outState);
    }

}
