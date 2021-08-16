package com.example.miwokapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class HindiMainActivity extends AppCompatActivity {

    CardView numbers;
    CardView family;
    CardView colors;
    CardView phrases;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hindi_main);


        numbers = findViewById(R.id.numbers_hindi_CardView);
        numbers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent numbersIntent = new Intent(HindiMainActivity.this, HindiNumbers.class);
                startActivity(numbersIntent);
            }
        });

        family = findViewById(R.id.family_hindi_CardView);
        family.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent familyIntent = new Intent(HindiMainActivity.this, HindiFamilyMembers.class);
                startActivity(familyIntent);
            }
        });
        colors = findViewById(R.id.colors_hindi_CardView);
        colors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent colorsIntent = new Intent(HindiMainActivity.this, HindiColors.class);
                startActivity(colorsIntent);
            }
        });
        phrases = findViewById(R.id.phrases_hindi_CardView);
        phrases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent phrasesIntent = new Intent(HindiMainActivity.this, HindiPhrases.class);
                startActivity(phrasesIntent);
            }
        });
    }
}