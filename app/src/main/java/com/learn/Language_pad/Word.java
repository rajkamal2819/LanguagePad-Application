package com.learn.Language_pad;

import android.text.Html;

public class Word {
    private String mDefaultTranslation;
    private String mMiwokTranslation;
    private int audioResourceId;
    private int imageResourceId = NO_IMAGE_PROVIDED;                 //first we just initiated it with -1 and if there will be an image it will be updated down
    private static final int NO_IMAGE_PROVIDED = -1;   //we have taken -1 since its an invalid image id and its not possible
    public Word(String defaultTranslation, String miwokTranslation){
        this.mDefaultTranslation = defaultTranslation;
        this.mMiwokTranslation = miwokTranslation;
    }

    public Word(String defaultTranslation, String miwokTranslation,int imageResourceId){
        this.mDefaultTranslation = defaultTranslation;
        this.mMiwokTranslation = miwokTranslation;
        this.imageResourceId = imageResourceId;             //now there are two options to use the constructors that either to use the
                                                           //constructor with 2 input Or constructor with 3 input
    }

    public Word(String defaultTranslation, String miwokTranslation,int imageResourceId,int audioResourceId){
        this.mDefaultTranslation = defaultTranslation;
        this.mMiwokTranslation = miwokTranslation;
        this.imageResourceId = imageResourceId;
        this.audioResourceId = audioResourceId;
    }

    public String getDefaultTranslation() {
        return mDefaultTranslation;
    }
    public String getMiwokTranslationTranslation() {
        return mMiwokTranslation;
    }
    public int getImageResourceId(){return imageResourceId;}
    public int getAudioResourceId(){return audioResourceId;};

    public boolean hasImage(){
        return imageResourceId !=NO_IMAGE_PROVIDED;      //will return true if its not equal to or will return false
    }
}
