package com.example.flippingcardlibraryproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.flippingcardlibrary.FlippingCardView;

public class MainActivity extends AppCompatActivity {

    private FlippingCardView flippingCardView1;
    private FlippingCardView flippingCardView2;
    private FlippingCardView flippingCardView3;
    private FlippingCardView flippingCardView4;

    private FlippingCardView flippingCardView5;
    private FlippingCardView flippingCardView6;
    private boolean isImageFirst = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        //hide purple bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        flippingCardView1 = findViewById(R.id.flippingCardView1);
        flippingCardView2 = findViewById(R.id.flippingCardView2);
        flippingCardView3 = findViewById(R.id.flippingCardView3);
        flippingCardView4 = findViewById(R.id.flippingCardView4);
        flippingCardView5 = findViewById(R.id.flippingCardView5);
        flippingCardView6 = findViewById(R.id.flippingCardView6);


        // Set the flip style for each card
       // flippingCardView1.setFlipStyle(0); // Normal flip
       // flippingCardView2.setFlipStyle(1); // Fade in Fade Out

        // Listener for the first card
        flippingCardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flippingCardView1.setFlipDuration(300);
                flippingCardView1.flipCard();
            }
        });

        // Listener for the second card
        flippingCardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flippingCardView2.setFlipDuration(300);
                flippingCardView2.flipCard();
            }
        });

        flippingCardView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flippingCardView3.setFlipDuration(300);
                flippingCardView3.flipCard();
            }
        });

        flippingCardView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flippingCardView4.setFlipDuration(400);
                flippingCardView4.flipCard();
            }
        });

        flippingCardView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flippingCardView5.setFlipDuration(400);
                flippingCardView5.flipCard();
            }
        });

        flippingCardView6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flippingCardView6.setFlipDuration(400);
                flippingCardView6.flipCard();
            }
        });
    }
}