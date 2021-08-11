package com.example.miwokapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.mlkit.common.model.DownloadConditions;
import com.google.mlkit.nl.translate.NaturalLanguageTranslateRegistrar;
import com.google.mlkit.nl.translate.TranslateLanguage;
import com.google.mlkit.nl.translate.Translation;
import com.google.mlkit.nl.translate.Translator;
import com.google.mlkit.nl.translate.TranslatorOptions;

import java.util.Locale;

public class TextToVoiceGenerator extends AppCompatActivity {

    private EditText typeHereTxt;
    private TextToSpeech tts;
    private Button translateButton;
    private Spinner fromLanguageSpinner;
    private TextView translateTxtView;
    private Button sayIt;
    private Spinner toLanguageSpinner;
    private String ttsS = "Error";
    String[] fromLanguageString = {"Hindi", "English", "Spanish", "German", "Russian", "English", "French", "Arabic"};
    String[] toLanguageString = {"Hindi", "English", "Spanish", "German", "Russian", "English", "French", "Arabic"};

    private static final int requestPermissionCode = 1;
    String languageCode, fromLanguageCode, toLanguageCode = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_to_voice_generator);

        typeHereTxt = findViewById(R.id.TypeHereTxt);
        translateButton = findViewById(R.id.translateButton);
        fromLanguageSpinner = findViewById(R.id.spinnerFrom);
        toLanguageSpinner = findViewById(R.id.spinnerTo);
        translateTxtView = findViewById(R.id.translation_textView);
        sayIt = findViewById(R.id.say_it_button);
        translateTxtView.setText("...");

        fromLanguageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                fromLanguageCode = getLanguageCode(fromLanguageString[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter fromAdapter = new ArrayAdapter(this, R.layout.spinner_item, fromLanguageString);
        fromAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fromLanguageSpinner.setAdapter(fromAdapter);

        toLanguageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                toLanguageCode = getLanguageCode(toLanguageString[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter toAdapter = new ArrayAdapter(this, R.layout.spinner_item, toLanguageString);
        toAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        toLanguageSpinner.setAdapter(toAdapter);


        translateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                translateTxtView.setText("");
                if (typeHereTxt.getText().toString().isEmpty()) {
                    Toast.makeText(TextToVoiceGenerator.this, "Please enter the text", Toast.LENGTH_SHORT).show();
                } else if (fromLanguageCode == "") {
                    Toast.makeText(TextToVoiceGenerator.this, "Please select source Language", Toast.LENGTH_SHORT).show();
                } else if (toLanguageCode == ""){
                    Toast.makeText(TextToVoiceGenerator.this, "Please select desired Language To Translate", Toast.LENGTH_SHORT).show();
                } else {
                    getTranslatedText(fromLanguageCode, toLanguageCode, typeHereTxt.getText().toString());
                }
            }
        });

        tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    /*switch (toLanguageCode) {
                        case 11:
                            tts.setLanguage(Locale.ENGLISH);
                        case 22:
                            tts.setLanguage(new Locale("hin-IND"));
                        case 1:
                            tts.setLanguage(new Locale("ar-DZ"));
                        case 13:
                            tts.setLanguage(new Locale("es-AR"));
                        case 9:
                            tts.setLanguage(Locale.GERMAN);
                        case 44:
                            tts.setLanguage(new Locale("ru-RU"));
                        case 17:
                            tts.setLanguage(Locale.FRENCH);
                    }*/
                }
            }
        });

        sayIt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String toSpeak = ttsS;
                Toast.makeText(getApplicationContext(), toSpeak, Toast.LENGTH_SHORT).show();
                tts.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
            }
        });


    }

    private void getTranslatedText(String fromLanguageCode, String toLanguageCode, String text) {

        translateTxtView.setText("Please Wait...");
        TranslatorOptions options = new TranslatorOptions.Builder().setSourceLanguage(fromLanguageCode)
                .setTargetLanguage(toLanguageCode).build();
        Translator translator = Translation.getClient(options);

        DownloadConditions conditions = new DownloadConditions.Builder().build();
        translator.downloadModelIfNeeded(conditions).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                translateTxtView.setText("Translating...");
                translator.translate(text).addOnSuccessListener(new OnSuccessListener<String>() {
                    @Override
                    public void onSuccess(String s) {
                        translateTxtView.setText(s);
                        ttsS = s;
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(TextToVoiceGenerator.this, "Failed to Translate: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(TextToVoiceGenerator.this, "Failed to Translate\nmay be Internet Issues: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    protected String getLanguageCode(String language) {
        String languageCode = "";
        switch (language) {
            case "English":
                languageCode = TranslateLanguage.ENGLISH;
                break;
            case "Hindi":
                languageCode = TranslateLanguage.HINDI;
                break;
            case "Arabic":
                languageCode = TranslateLanguage.ARABIC;
                break;
            case "Spanish":
                languageCode = TranslateLanguage.SPANISH;
                break;
            case "German":
                languageCode = TranslateLanguage.GERMAN;
                break;
            case "Russian":
                languageCode = TranslateLanguage.RUSSIAN;
                break;
            case "French":
                languageCode = TranslateLanguage.FRENCH;
                break;

            default:
                break;
        }
        return languageCode;
    }

    public void onPause() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onPause();
    }

}