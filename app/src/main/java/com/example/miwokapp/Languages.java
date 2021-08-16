package com.example.miwokapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Languages extends AppCompatActivity {

    CardView hindi;
    CardView german;
    CardView spanish;
    CardView russian;
    CardView miwok;
    CardView tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.languages_main2);

        hindi = findViewById(R.id.hindi_language_card);
        hindi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Intent i = new Intent(Languages.this, HindiMainActivity.class);
              startActivity(i);
            }
        });
        german = findViewById(R.id.german_language_card);
        german.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Intent i = new Intent(Languages.this,GermanMainActivity.class);
              startActivity(i);
            }
        });
        spanish = findViewById(R.id.spanish_language_card);
        spanish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Intent i = new Intent(Languages.this,SpanishMainActivity.class);
              startActivity(i);
            }
        });
        russian = findViewById(R.id.russian_language_card);
        russian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Intent i = new Intent(Languages.this,RussianMainActivity.class);
              startActivity(i);
            }
        });
        hindi = findViewById(R.id.hindi_language_card);
        hindi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Intent i = new Intent(Languages.this, HindiMainActivity.class);
              startActivity(i);
            }
        });
         miwok = findViewById(R.id.miwok_language_card);
        miwok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Intent i = new Intent(Languages.this, MiwokMainActivity.class);
              startActivity(i);
            }
        });
        tts = findViewById(R.id.tts_generator_card);
        tts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Intent i = new Intent(Languages.this, TranslatorAndVoiceGenerator.class);
              startActivity(i);
            }
        });

    }
}