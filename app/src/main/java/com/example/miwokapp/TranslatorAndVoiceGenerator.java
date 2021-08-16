package com.example.miwokapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
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
import com.google.mlkit.nl.translate.TranslateLanguage;
import com.google.mlkit.nl.translate.Translation;
import com.google.mlkit.nl.translate.Translator;
import com.google.mlkit.nl.translate.TranslatorOptions;

import java.util.Locale;

public class TranslatorAndVoiceGenerator extends AppCompatActivity {

    private EditText typeHereTxt;
    private TextToSpeech tts;
    private Button translateButton;
    private Spinner fromLanguageSpinner;
    private TextView translateTxtView;
    private Button sayIt;
    private Spinner toLanguageSpinner;
    private String ttsS = "Error";
    private Button moduleManage;
    String[] fromLanguageString = {"Hindi", "English", "Spanish", "German", "Russian", "French", "Arabic"};
    String[] toLanguageString = {"Hindi", "English", "Spanish", "German", "Russian", "French", "Arabic"};

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
                    Toast.makeText(TranslatorAndVoiceGenerator.this, "Please enter the text", Toast.LENGTH_SHORT).show();
                } else if (fromLanguageCode == "") {
                    Toast.makeText(TranslatorAndVoiceGenerator.this, "Please select source Language", Toast.LENGTH_SHORT).show();
                } else if (toLanguageCode == ""){
                    Toast.makeText(TranslatorAndVoiceGenerator.this, "Please select desired Language To Translate", Toast.LENGTH_SHORT).show();
                } else {
                    getTranslatedText(fromLanguageCode, toLanguageCode, typeHereTxt.getText().toString());
                }
            }
        });

        tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    switch (toLanguageCode) {
                        case "en":
                            tts.setLanguage(Locale.ENGLISH); break;
                        case "hi":
                            tts.setLanguage(new Locale("hin-IND")); break;
                        case "ar":
                            tts.setLanguage(new Locale("ar-DZ")); break;
                        case "es":
                            tts.setLanguage(new Locale("es-AR")); break;
                        case "de":
                            tts.setLanguage(Locale.GERMAN); break;
                        case "ru":
                            tts.setLanguage(new Locale("ru-RU")); break;
                        case "fr":
                            tts.setLanguage(Locale.FRENCH); break;
                    }
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

        moduleManage = findViewById(R.id.manage_download_modules_button);
        moduleManage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TranslatorAndVoiceGenerator.this,ManageTranslationModules.class);
                startActivity(i);
            }
        });

    }

    Translator translator;

    protected void getTranslatedText(String fromLanguageCode, String toLanguageCode, String text) {

        translateTxtView.setText("Please Wait...\nDownloading Module");
        TranslatorOptions options = new TranslatorOptions.Builder().setSourceLanguage(fromLanguageCode)
                .setTargetLanguage(toLanguageCode).build();
         translator = Translation.getClient(options);

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
                        Toast.makeText(TranslatorAndVoiceGenerator.this, "Failed to Translate: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(TranslatorAndVoiceGenerator.this, "Failed to Translate\nmay be Internet Issues: " + e.getMessage(), Toast.LENGTH_SHORT).show();
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
        if (translator != null) {
            translator.close();
        }

        super.onPause();
    }

}