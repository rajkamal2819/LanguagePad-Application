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

public class SpanishNumbers extends AppCompatActivity {

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

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        myAudioFocus = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        ArrayList<Word> numbers = new ArrayList<Word>();
        numbers.add(new Word("one","una",R.drawable.number_one,R.raw.spanish_one));
        numbers.add(new Word("two","dos",R.drawable.number_two,R.raw.spanish_two));
        numbers.add(new Word("three","Tres",R.drawable.number_three,R.raw.spanish_three));
        numbers.add(new Word("four","cuatro",R.drawable.number_four,R.raw.spanish_four));
        numbers.add(new Word("five","cinco",R.drawable.number_five,R.raw.spanish_five));
        numbers.add(new Word("six","seis",R.drawable.number_six,R.raw.spanish_six));
        numbers.add(new Word("seven","Siete",R.drawable.number_seven,R.raw.spanish_seven));
        numbers.add(new Word("eight","ocho",R.drawable.number_eight,R.raw.spanish_eight));
        numbers.add(new Word("nine","nueve",R.drawable.number_nine,R.raw.spanish_nine));
        numbers.add(new Word("ten","diez",R.drawable.number_ten,R.raw.spanish_ten));

        WordAdapter itemsAdapter = new WordAdapter(this,numbers,R.color.category_numbers);

        ListView listView = (ListView) findViewById(R.id.list);
        // GridView listView = (GridView) findViewById(R.id.list);
        //  listView.setNumColumns(2);
        // listView.setAdapter(itemsAdapter);

        listView.setAdapter(itemsAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Release the media player if it currently exists because we are about to
                // play a different sound file
                releaseMediaPlayer();

                Word w = numbers.get(position);

                //going to set my audioFocus here
                int result = myAudioFocus.requestAudioFocus(mOnAudioFocusChangeListener,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if(result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    //so now if its granted then we can play the music here

                    mMediaPlayer = MediaPlayer.create(SpanishNumbers.this, w.getAudioResourceId());
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

    /**
     * Clean up the media player by releasing its resources.
     */
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

            //if we are no longer needed or using the audioFocus so now we should release it
            // Abandon audio focus when playback complete
            myAudioFocus.abandonAudioFocus(mOnAudioFocusChangeListener);

        }
    }

}