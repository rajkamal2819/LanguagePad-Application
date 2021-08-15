package com.example.miwokapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.mlkit.common.model.RemoteModelManager;
import com.google.mlkit.nl.translate.TranslateLanguage;
import com.google.mlkit.nl.translate.TranslateRemoteModel;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Set;

public class ManageTranslationModules extends AppCompatActivity {

    LinearLayout languageItemList;
    Button englishDelete;
    Button spanishDelete;
    Button germanDelete;
    Button hindiDelete;
    Button russianDelete;
    Button frenchDelete;
    Button arabicDelete;
    TextView englishStatus;
    TextView germanStatus;
    TextView spanishStatus;
    TextView arabicStatus;
    TextView hindiStatus;
    TextView frenchStatus;
    TextView russianStatus;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_translation_list_view);

        languageItemList = findViewById(R.id.language_linear_layout);
        englishDelete = findViewById(R.id.english_delete);
        spanishDelete = findViewById(R.id.spanish_delete);
         germanDelete = findViewById(R.id.german_delete);
         hindiDelete = findViewById(R.id.hindi_delete);
         russianDelete = findViewById(R.id.russian_delete);
        frenchDelete = findViewById(R.id.french_delete);
         arabicDelete = findViewById(R.id.arabic_delete);

         englishStatus = findViewById(R.id.english_status);
         germanStatus = findViewById(R.id.german_status);
         spanishStatus = findViewById(R.id.spanish_status);
         arabicStatus = findViewById(R.id.arabic_status);
         hindiStatus = findViewById(R.id.hindi_status);
         frenchStatus = findViewById(R.id.french_status);
         russianStatus = findViewById(R.id.russina_status);

         someMethod(getModel("English"));
         someMethod(getModel("Hindi"));
         someMethod(getModel("Spanish"));
         someMethod(getModel("German"));
         someMethod(getModel("Russian"));
         someMethod(getModel("Arabic"));
         someMethod(getModel("French"));

         englishDelete.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 deleteModel(getModel("English"));
             }
         });
        spanishDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteModel(getModel("Spanish"));
                spanishStatus.setText("Not Downloaded");
            }
        });
        germanDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteModel(getModel("German"));
                germanStatus.setText("Not Downloaded");
            }
        });
        hindiDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteModel(getModel("Hindi"));
                hindiStatus.setText("Not Downloaded");
            }
        });
        frenchDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteModel(getModel("French"));
                frenchStatus.setText("Not Downloaded");
            }
        });
        arabicDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteModel(getModel("Arabic"));
                arabicStatus.setText("Not Downloaded");
            }
        });
        russianDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteModel(getModel("Russian"));
                russianStatus.setText("Not Downloaded");
            }
        });

    }

    protected String getLanguageType(TranslateRemoteModel t){
        String languageName = "";
        switch (t.getLanguage()) {
            case "en": languageName = "English"; break;

            case "hi": languageName = "Hindi"; break;

            case "ar": languageName = "Arabic"; break;

            case "es": languageName = "Spanish"; break;

            case "de": languageName = "German"; break;

            case "ru": languageName = "Russian"; break;

            case "fr": languageName = "French"; break;

        }
        return languageName;
    }

    protected TranslateRemoteModel getModel(String language){
        TranslateRemoteModel translateRemoteModel = null;
        switch (language) {
            case "English": translateRemoteModel = new TranslateRemoteModel.Builder(TranslateLanguage.ENGLISH).build(); break;

            case "Hindi": translateRemoteModel = new TranslateRemoteModel.Builder(TranslateLanguage.HINDI).build();break;

            case "Arabic": translateRemoteModel = new TranslateRemoteModel.Builder(TranslateLanguage.ARABIC).build();break;

            case "Spanish": translateRemoteModel = new TranslateRemoteModel.Builder(TranslateLanguage.SPANISH).build();break;

            case "German": translateRemoteModel = new TranslateRemoteModel.Builder(TranslateLanguage.GERMAN).build();break;

            case "Russian": translateRemoteModel = new TranslateRemoteModel.Builder(TranslateLanguage.RUSSIAN).build();break;

            case "French": translateRemoteModel = new TranslateRemoteModel.Builder(TranslateLanguage.FRENCH).build();break;

        }
        return translateRemoteModel;
    }

    protected void deleteModel(TranslateRemoteModel t){

        RemoteModelManager modelManager = RemoteModelManager.getInstance();

        modelManager.getDownloadedModels(TranslateRemoteModel.class)
                .addOnSuccessListener(new OnSuccessListener<Set<TranslateRemoteModel>>() {
                    @Override
                    public void onSuccess(Set<TranslateRemoteModel> translateRemoteModels) {

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Error.
            }
        });

        modelManager.deleteDownloadedModel(t)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                      Toast.makeText(ManageTranslationModules.this,"Deleted Module "+getLanguageType(t),Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ManageTranslationModules.this,"Failed to delete "+e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
        }

        protected void someMethod(TranslateRemoteModel t){

            RemoteModelManager modelManager = RemoteModelManager.getInstance();

            modelManager.isModelDownloaded(t).addOnSuccessListener(new OnSuccessListener<Boolean>() {
                @Override
                public void onSuccess(Boolean aBoolean) {
                    if(aBoolean) {
                        getTextView(t).setText("Downloaded");
                    }
                    else{
                        getTextView(t).setText("Not Downloaded");
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });

        }

        protected TextView getTextView(TranslateRemoteModel t){
          TextView txt = null;
          switch (t.getLanguage()){
              case "en": txt = englishStatus; break;
              case "hi": txt = hindiStatus; break;
              case "ar": txt = arabicStatus; break;
              case "es": txt = spanishStatus; break;
              case "de": txt = germanStatus; break;
              case "ru": txt = russianStatus; break;
              case "fr": txt = frenchStatus; break;
          }
          return txt;
        }

}