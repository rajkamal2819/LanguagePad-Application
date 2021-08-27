package com.learn.Language_pad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.learn.Language_pad.R;

public class MiwokMainActivity extends AppCompatActivity {

    CardView numbers;
    CardView family;
    CardView colors;
    CardView phrases;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.miwok_activity_main);


        numbers = findViewById(R.id.numbers_Miwok_CardView);
        numbers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                   Intent numbersIntent = new Intent(MiwokMainActivity.this,NumberActivity.class);
                   startActivity(numbersIntent);
                }
        });

        family = findViewById(R.id.family_Miwok_CardView);
        family.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent familyIntent = new Intent(MiwokMainActivity.this,FamilyActivity.class);
                startActivity(familyIntent);
            }
        });
        colors = findViewById(R.id.colors_Miwok_CardView);
        colors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent colorsIntent = new Intent(MiwokMainActivity.this,ColorsActivity.class);
                startActivity(colorsIntent);
            }
        });
        phrases = findViewById(R.id.phrases_Miwok_CardView);
        phrases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent phrasesIntent = new Intent(MiwokMainActivity.this,PhrasesActivity.class);
                startActivity(phrasesIntent);
            }
        });
    }

    /*public void openNumbersActivity(View view) {
        Intent intent = new Intent(this,NumberActivity.class);
        TextView text = (TextView) findViewById(R.id.numbers);       //-->one way of creating intent
        String message = text.getText().toString();
        startActivity(intent);
    }*/
    /*public void openNumbersActivity(View view) {
        Intent intent = new Intent(this,NumberActivity.class); //or directly do this we have given the onClick attribute to the
        startActivity(intent);                                            //text to which we want to open the intent
    }*/


}