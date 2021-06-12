package com.bahaadev.videostreaming;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;

public class MainActivity extends AppCompatActivity {
    String videoUrl = "";
    String videoOne="https://www.learningcontainer.com/wp-content/uploads/2020/05/sample-mp4-file.mp4";
    String videoTwo = "https://samplelib.com/lib/preview/mp4/sample-5s.mp4";
    String videoThree = "http://techslides.com/demos/sample-videos/small.mp4";
    String videoFour = "http://www.convoi77.org/wp-content/uploads/2016/01/MP4/Undo.mp4";
    private boolean playWhenReady = true;
    private int currentWindow = 0;
    private long playpackPosition = 0;

    Button btnOne,btnTwo,btnThree,btnFour;
    PlayerView pvMain;
    SimpleExoPlayer player;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pvMain = findViewById(R.id.pvMain);

        btnOne = findViewById(R.id.btnOne);
        btnTwo = findViewById(R.id.btnTwo);
        btnThree = findViewById(R.id.btnThree);
        btnFour = findViewById(R.id.btnFour);

        btnOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetVideo();
                videoUrl = videoOne;
                initVideo();
            }
        });

        btnTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetVideo();
                videoUrl = videoTwo;
                initVideo();
            }
        });

        btnThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetVideo();
                videoUrl = videoThree;
                initVideo();
            }
        });

        btnFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetVideo();
                videoUrl = videoFour;
                initVideo();
            }
        });
    }

    public void initVideo(){
        //player
        player = new SimpleExoPlayer.Builder(pvMain.getContext()).build();
        pvMain.setPlayer(player);

        Uri uri = Uri.parse(videoUrl);

        DataSource.Factory dataSorceFactory = new DefaultDataSourceFactory(this,"exoplayer-code1sb");

        MediaSource mediaSource = new ProgressiveMediaSource.Factory(dataSorceFactory).createMediaSource(uri);

        player.setPlayWhenReady(playWhenReady);
        player.seekTo(currentWindow,playpackPosition);
        player.prepare(mediaSource,false,false);

    }

    public void releaseVideo(){
        if (player != null){
            playWhenReady = player.getPlayWhenReady();
            playpackPosition = player.getCurrentPosition();
            currentWindow = player.getCurrentWindowIndex();
            player.release();
            player = null;
        }
    }
    public void resetVideo(){
        if (player != null){
            playWhenReady = true;
            currentWindow = 0;
            playpackPosition = 0;
            player.release();
            player = null;

        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (player != null){
            initVideo();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        releaseVideo();

    }

    @Override
    protected void onStop() {
        super.onStop();
        releaseVideo();
    }
}