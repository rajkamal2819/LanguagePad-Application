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
    TextView hindiStatus;
    TextView spanishStatus;
    TextView germanStatus;
    TextView russianStatus;
    TextView frenchStatus;
    TextView arabicStatus;

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
         hindiStatus = findViewById(R.id.hindi_status);
         spanishStatus = findViewById(R.id.spanish_status);
         germanStatus = findViewById(R.id.german_status);
         russianStatus = findViewById(R.id.russina_status);
         frenchStatus = findViewById(R.id.french_status);
         arabicStatus = findViewById(R.id.arabic_status);

         englishDelete.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 deleteModel(getModel("English"));

                 if(getModel("English")!=null){
                     englishStatus.setText("Downloaded");
                 }

             }
         });
        spanishDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteModel(getModel("Spanish"));
            }
        });
        germanDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteModel(getModel("German"));
            }
        });
        hindiDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteModel(getModel("Hindi"));
            }
        });
        frenchDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteModel(getModel("French"));
            }
        });
        arabicDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteModel(getModel("Arabic"));
            }
        });
        russianDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteModel(getModel("Russian"));
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

         englishStatus = findViewById(R.id.english_status);
         hindiStatus = findViewById(R.id.hindi_status);
         spanishStatus = findViewById(R.id.spanish_status);
         germanStatus = findViewById(R.id.german_status);
         russianStatus = findViewById(R.id.russina_status);
         frenchStatus = findViewById(R.id.french_status);
         arabicStatus = findViewById(R.id.arabic_status);

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

}