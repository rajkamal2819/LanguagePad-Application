package com.example.miwokapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.mlkit.common.model.DownloadConditions;
import com.google.mlkit.common.model.RemoteModelManager;
import com.google.mlkit.nl.translate.TranslateLanguage;
import com.google.mlkit.nl.translate.TranslateRemoteModel;

import java.util.ArrayList;
import java.util.Set;

public class ManageTranslationModules extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);

        ArrayList<ManageModule> languageModels = new ArrayList<>();
        ArrayList<TranslateRemoteModel> modules = new ArrayList<>();

        RemoteModelManager modelManager = RemoteModelManager.getInstance();

        // Get translation models stored on the device.
        modelManager.getDownloadedModels(TranslateRemoteModel.class).addOnSuccessListener(new OnSuccessListener<Set<TranslateRemoteModel>>() {
            @Override
            public void onSuccess(Set<TranslateRemoteModel> translateRemoteModels) {
                modules.addAll(translateRemoteModels);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure( Exception e) {
                Toast.makeText(ManageTranslationModules.this,"No Modules Downloaded",Toast.LENGTH_SHORT).show();
            }
        });

        for (int i = 0;i<modules.size();i++){
            languageModels.add(new ManageModule(getLanguageName(modules.get(i)),true,modules.get(i)));
        }

        ModuleAdapter itemsAdapter = new ModuleAdapter(this,languageModels,R.color.category_manage_module);
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(itemsAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ManageModule currentModel = languageModels.get(position);
                if(currentModel.isDownloaded()){
                    TranslateRemoteModel model = new TranslateRemoteModel.Builder(currentModel.getModel().getLanguage()).build();
                    modelManager.deleteDownloadedModel(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            //Here we need to add the confirmation prompt to delete that module
                            Toast.makeText(ManageTranslationModules.this, "Deleting the module "+currentModel.getModel().getLanguage(), Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(Exception e) {
                            Toast.makeText(ManageTranslationModules.this, "Module can't be deleted "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                else{
                    TranslateRemoteModel model = new TranslateRemoteModel.Builder(currentModel.getModel().getLanguage()).build();
                    DownloadConditions conditions = new DownloadConditions.Builder().requireWifi().build();
                    modelManager.download(currentModel.model,conditions).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(ManageTranslationModules.this, "Downloaded Module", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(ManageTranslationModules.this, "Module can't be downloaded", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

    }

    protected String getLanguageName(TranslateRemoteModel t){
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

}