package com.example.miwokapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class GermanMainActivity extends AppCompatActivity {

    CardView numbers;
    CardView family;
    CardView colors;
    CardView phrases;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_german_main);


        numbers = findViewById(R.id.numbers_german_CardView);
        numbers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent numbersIntent = new Intent(GermanMainActivity.this, GermanNumbers.class);
                startActivity(numbersIntent);
            }
        });

        family = findViewById(R.id.family_german_CardView);
        family.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent familyIntent = new Intent(GermanMainActivity.this, GermanFamilyMembers.class);
                startActivity(familyIntent);
            }
        });
        colors = findViewById(R.id.colors_german_CardView);
        colors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent colorsIntent = new Intent(GermanMainActivity.this, GermanColors.class);
                startActivity(colorsIntent);
            }
        });
        phrases = findViewById(R.id.phrases_german_CardView);
        phrases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent phrasesIntent = new Intent(GermanMainActivity.this, GermanPhrases.class);
                startActivity(phrasesIntent);
            }
        });
    }
}