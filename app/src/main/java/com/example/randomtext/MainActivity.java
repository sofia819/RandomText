package com.example.randomtext;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity {

    private int regularText = 5, maxText = 80;
    private int startAuto = regularText + (int)(Math.random() * regularText);
    private Button btn;
    private RelativeLayout layout;
    private String logTag = "myApp", initialText = "Click to add text", errorText = "System error";
    private int counter;
    private float maxX, maxY;
    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = (Button)findViewById(R.id.button);
        btn.setText(initialText);
        layout = (RelativeLayout) findViewById(R.id.bkg);

        counter = 0;
    }

    public void onTextClick(View v){
        if(counter > startAuto) {
            auto();
            btn.setText("Something broke hahaha");
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Do nothing but button still appears clicked
                }
            });
        }
        else
            click();
    }

    private void click(){
        calendar = Calendar.getInstance();

        counter++;
        int displayHeight = layout.getHeight();
        float textSize = pixelsToSp(this, btn.getTextSize());
        float factor = ((float)displayHeight + counter * 50) / displayHeight;
        float newTextSize = (float)(textSize * factor);
        int degreeRotate = (int)(Math.random() * 360);

        maxX = layout.getWidth() - newTextSize;
        maxY = layout.getHeight() - newTextSize;

        double newX, newY;
        newX = Math.random() * calendar.getTimeInMillis() % maxX;
        newY = Math.random() * calendar.getTimeInMillis() % maxY;

        TextView newText = new TextView(this);
        if(counter > startAuto) {
            newText.setText(nonsense());
            newText.setRotation(degreeRotate);
        }
        else
            newText.setText("Hello there!");

        int colorNum = (int)(newTextSize * newX * newY);
        if(counter % 2 == 0){
            newText.setTextColor(btn.getCurrentTextColor() + colorNum);
        }
        else
            newText.setTextColor(btn.getCurrentTextColor() - colorNum);

        newText.setX((float)newX);
        newText.setY((float)newY);
        newText.setTextSize(newTextSize);
        layout.addView(newText);
    }

    private float pixelsToSp(Context context, float px) {
        float scaledDensity = context.getResources().getDisplayMetrics().scaledDensity;
        return px / scaledDensity;
    }

    private String nonsense(){
        int n;
        int maxLen = 20;
        int randLen = (int)(Math.random() * maxLen);
        String str = "";

        for(int i = 0; i < randLen; i++){
            n = (int)(26 * Math.random());
            if((i + n) % randLen == 0){
                str += (char)('A' + n % 25);
            }
            else{
                str += (char)('a' + n % 25);
            }
        }

        return str;
    }

    private void auto(){
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new TimerTask() {
                    @Override
                    public void run() {
                        if(counter < maxText){
                            click();
                        }
                        else{
                            finish();
                            startActivity(getIntent());
                        }
                    }

                });
            }
        }, 0, 300);
    }
}
