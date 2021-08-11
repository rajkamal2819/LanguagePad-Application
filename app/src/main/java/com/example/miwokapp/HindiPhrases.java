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

public class HindiPhrases extends AppCompatActivity {

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

        ArrayList<Word> phrases = new ArrayList<>();
        phrases.add(new Word("Where are you going?","तुम कहाँ जा रहे हो?",-1,R.raw.hindi_where_are_you_going));
        phrases.add(new Word("What is your name?","तुम्हारा नाम क्या हे?",-1,R.raw.hindi_what_is_your_name));
        phrases.add(new Word("My name is...", "मेरा नाम है...",-1,R.raw.hindi_my_name_is));
        phrases.add(new Word("How are you feeling?","तुम्हे कैसा लग रहा है?",-1,R.raw.hindi_how_are_you_feeling));
        phrases.add(new Word("I’m feeling good.","मैं अच्छा महसूस कर रहा हूँ।",-1,R.raw.hindi_i_am_feeling_good));
        phrases.add(new Word("Are you coming?","क्या आप आ रहे हैं?",-1,R.raw.hindi_are_you_coming));
        phrases.add(new Word("Yes, I’m coming.","हाँ, आ रहा हूं।",-1,R.raw.hindi_yes_i_am_coming));
        phrases.add(new Word("I’m coming.","मैं आ रहा हूं।",-1,R.raw.hindi_i_am_coming));
        phrases.add(new Word("Let’s go.","चलो चलते हैं।",-1,R.raw.hindi_lets_go));
        phrases.add(new Word("Come here.","यहां आओ।",-1,R.raw.hindi_come_here));

        WordAdapter itemPhrases = new WordAdapter(this,phrases,R.color.category_phrases);
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(itemPhrases);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Release the media player if it currently exists because we are about to
                // play a different sound file
                releaseMediaPlayer();

                Word w = phrases.get(position);

                int result = myAudioFocus.requestAudioFocus(mOnAudioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    mMediaPlayer = MediaPlayer.create(HindiPhrases.this, w.getAudioResourceId());
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