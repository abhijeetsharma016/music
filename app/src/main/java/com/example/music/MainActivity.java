package com.example.music;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

import java.io.IOException; import java.util.ArrayList; import java.util.Collection; import java.util.Collections;

public class MainActivity extends AppCompatActivity { private Button play; private Button pause; private SeekBar seekBar; ArrayList<String> songs; int currentSongIndex = 0;
    @Override protected void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        play = findViewById(R.id.play);
        pause = findViewById(R.id.pause);
        seekBar = findViewById(R.id.seekBar);
        //Initilising media player MediaPlayer mediaPlayer = new MediaPlayer();
    MediaPlayer mediaPlayer = new MediaPlayer();
    //from loacal source
    //mediaPlayer= MediaPlayer.create(this,R.raw.music1);

    //From remote source
    songs = new ArrayList<>();
    songs.add("https://paglasongs.com/files/download/id/14824");//heeriye
    songs.add("https://paglasongs.com/files/download/id/15342");//Hu Main Jaha Tum Ho Waha Mp3 Download Mohit Lalwani
    songs.add("https://paglasongs.com/files/download/id/11687");//Aap jaisa koi
    songs.add("https://paglasongs.com/files/download/id/13881");//Bye

    //shuffling songs
    Collections.shuffle(songs);
    try {
        mediaPlayer.setDataSource(songs.get(currentSongIndex));
    }catch (Exception e){
        throw new RuntimeException(e);
    }

    mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
        @Override
        public void onPrepared(MediaPlayer mp) {
            Toast.makeText(MainActivity.this, "Ready to play", Toast.LENGTH_SHORT).show();
            //mp.start(); // removed this line as it is unnecessary and can cause errors
            seekBar.setMax(mediaPlayer.getDuration());
            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if(fromUser){
                        mediaPlayer.seekTo(progress);
                    }
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
        }
    });
    mediaPlayer.prepareAsync();

    //mediaPlayer.start(); // removed this line as it is unnecessary and can cause errors
    play.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mediaPlayer.start();
        }
    });
    pause.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mediaPlayer.pause();
        }
    });
    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            // increment the current song index
            currentSongIndex++;
            // check if the index is valid
            if (currentSongIndex < songs.size()) {
                // play the next song
                try {
                    mediaPlayer.reset(); // added this line to reset the media player before setting a new data source
                    mediaPlayer.setDataSource(songs.get(currentSongIndex));
                }catch (Exception e){
                    throw new RuntimeException(e);
                }
            } else {
                // reset the index and stop the media player
                currentSongIndex = 0;
                mediaPlayer.stop();
            }
        }
    });

    mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() { // added this listener to handle errors while playing
        @Override
        public boolean onError(MediaPlayer mp, int what, int extra) {
            Toast.makeText(MainActivity.this, "An error occurred while playing", Toast.LENGTH_SHORT).show();
            mediaPlayer.stop(); // stop the media player on error
            return true; // indicate that the error was handled
        }
    });
}

}