package com.example.miwokapp;

import com.google.mlkit.nl.translate.TranslateRemoteModel;

public class ManageModule {
    private static final boolean NOT_DOWNLOADED = false;
    private boolean downloaded = NOT_DOWNLOADED;
    private String LanguageName;
    TranslateRemoteModel model;

    public ManageModule(String LanguageName,boolean downloaded,TranslateRemoteModel model){
        this.downloaded = downloaded;
        this.LanguageName = LanguageName;
        this.model = model;
    }

    public String getLanguageName() {
        return LanguageName;
    }

    public boolean isDownloaded() {
        return downloaded;
    }

    public TranslateRemoteModel getModel() {
        return model;
    }
}
