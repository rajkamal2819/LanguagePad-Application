package com.learn.Language_pad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.learn.Language_pad.R;

public class SpanishMainActivity extends AppCompatActivity {

    CardView numbers;
    CardView family;
    CardView colors;
    CardView phrases;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spanish_main);


        numbers = findViewById(R.id.numbers_spanish_CardView);
        numbers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent numbersIntent = new Intent(SpanishMainActivity.this,SpanishNumbers.class);
                startActivity(numbersIntent);
            }
        });

        family = findViewById(R.id.family_spanish_CardView);
        family.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent familyIntent = new Intent(SpanishMainActivity.this,SpanishFamilyMembers.class);
                startActivity(familyIntent);
            }
        });
        colors = findViewById(R.id.colors_spanish_CardView);
        colors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent colorsIntent = new Intent(SpanishMainActivity.this,SpanishColors.class);
                startActivity(colorsIntent);
            }
        });
        phrases = findViewById(R.id.phrases_spanish_CardView);
        phrases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent phrasesIntent = new Intent(SpanishMainActivity.this,SpanishPhrases.class);
                startActivity(phrasesIntent);
            }
        });
    }
}