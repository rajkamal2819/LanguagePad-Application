package com.learn.Language_pad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.learn.Language_pad.R;

public class RussianMainActivity extends AppCompatActivity {

    CardView numbers;
    CardView family;
    CardView colors;
    CardView phrases;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_russian_main);


        numbers = findViewById(R.id.numbers_russian_CardView);
        numbers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent numbersIntent = new Intent(RussianMainActivity.this,RussianNumbers.class);
                startActivity(numbersIntent);
            }
        });

        family = findViewById(R.id.family_russian_CardView);
        family.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent familyIntent = new Intent(RussianMainActivity.this,RussianFamilyMembers.class);
                startActivity(familyIntent);
            }
        });
        colors = findViewById(R.id.colors_russian_CardView);
        colors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent colorsIntent = new Intent(RussianMainActivity.this,RussianColors.class);
                startActivity(colorsIntent);
            }
        });
        phrases = findViewById(R.id.phrases_russian_CardView);
        phrases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent phrasesIntent = new Intent(RussianMainActivity.this,RussianPhrases.class);
                startActivity(phrasesIntent);
            }
        });
    }
}