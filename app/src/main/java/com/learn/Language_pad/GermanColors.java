package com.learn.Language_pad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.learn.Language_pad.R;

import java.util.ArrayList;

public class GermanColors extends AppCompatActivity {

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

        ArrayList<Word> color = new ArrayList<>();
        color.add(new Word("red","rot",R.drawable.color_red,R.raw.german_red));
        color.add(new Word("green","Grün",R.drawable.color_green,R.raw.german_green));
        color.add(new Word("brown","Braun",R.drawable.color_brown,R.raw.german_brown));
        color.add(new Word("gray","grau",R.drawable.color_gray,R.raw.german_gray));
        color.add(new Word("black","Schwarz",R.drawable.color_black,R.raw.german_black));
        color.add(new Word("white","Weiß",R.drawable.color_white,R.raw.german_white));
        color.add(new Word("dust yellow","Staub gelb",R.drawable.color_dusty_yellow,R.raw.german_dust_yellow));
        color.add(new Word("must yellow","senfgelb",R.drawable.color_mustard_yellow,R.raw.german_must_yellow));

        WordAdapter itemColors = new WordAdapter(this,color,R.color.category_colors);
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(itemColors);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Release the media player if it currently exists because we are about to
                // play a different sound file
                releaseMediaPlayer();

                Word w = color.get(position);

                int result = myAudioFocus.requestAudioFocus(mOnAudioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    mMediaPlayer = MediaPlayer.create(GermanColors.this, w.getAudioResourceId());
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