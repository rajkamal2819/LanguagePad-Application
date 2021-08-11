package com.example.miwokapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class HindiFamilyMembers extends AppCompatActivity {

    MediaPlayer mMediaPlayer;
    /**
     * This listener gets triggered when the {@link MediaPlayer} has completed
     * playing the audio file.
     */
    private MediaPlayer.OnCompletionListener mOnCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMediaPlayer();
        }
    };

    private AudioManager myAudioFocus;

    private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if(focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK || focusChange == AudioManager.AUDIOFOCUS_GAIN_TRANSIENT){
                mMediaPlayer.pause();
                mMediaPlayer.seekTo(0);  //so that if any interruption comes and it gets paused so then it should start from beginning so because
            }                                                                                           //our audio files are small
            else if(focusChange == AudioManager.AUDIOFOCUS_LOSS){
                releaseMediaPlayer();
            }
            else if(focusChange == AudioManager.AUDIOFOCUS_GAIN){
                mMediaPlayer.start();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);

        myAudioFocus = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        ArrayList<Word> family = new ArrayList<>();
        family.add(new Word("father","पिता",R.drawable.family_father,R.raw.hindi_father));
        family.add(new Word("mother","माँ",R.drawable.family_mother,R.raw.hindi_mother));
        family.add(new Word("son","बेटा",R.drawable.family_son,R.raw.hindi_son));
        family.add(new Word("daughter","बेटी",R.drawable.family_daughter,R.raw.hindi_daughter));
        family.add(new Word("older brother","बड़ा भाई",R.drawable.family_older_brother,R.raw.hindi_older_brother));
        family.add(new Word("younger brother","छोटे भाई",R.drawable.family_younger_brother,R.raw.hindi_younger_brother));
        family.add(new Word("older sister","बड़ी बहन",R.drawable.family_older_sister,R.raw.hindi_older_sister));
        family.add(new Word("younger sister","छोटी बहन",R.drawable.family_older_sister,R.raw.hindi_younger_sister));
        family.add(new Word("grandmother","दादी",R.drawable.family_grandmother,R.raw.hindi_grandmother));
        family.add(new Word("grandfather","दादा",R.drawable.family_grandfather,R.raw.hindi_grandfather));

        WordAdapter itemFamily = new WordAdapter(this,family,R.color.category_family);
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(itemFamily);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               /* MediaPlayer mMediaPlayer = MediaPlayer.create(FamilyActivity.this,family.get(position).getAudioResourceId());
                mMediaPlayer.start();*/
                //or else we can do by creating an word object and then we can retrieve word with specific position of list view item

                // Release the media player if it currently exists because we are about to
                // play a different sound file
                releaseMediaPlayer();

                Word w = family.get(position);

                int result = myAudioFocus.requestAudioFocus(mOnAudioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    mMediaPlayer = MediaPlayer.create(HindiFamilyMembers.this, w.getAudioResourceId());
                    mMediaPlayer.start();

                    // Setup a listener on the media player, so that we can stop and release the
                    // media player once the sound has finished playing.
                    mMediaPlayer.setOnCompletionListener(mOnCompletionListener);
                }
            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();
        // When the activity is stopped, release the media player resources because we won't
        // be playing any more sounds.
        releaseMediaPlayer();
    }

    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mMediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mMediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mMediaPlayer = null;

            myAudioFocus.abandonAudioFocus(mOnAudioFocusChangeListener);

        }
    }
}