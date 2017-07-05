package com.dftc.videoplay;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.dftc.videoijkplayer.PlayerManager;
import com.dftc.videoijkplayer.media.IjkVideoView;

public class TestActivity extends AppCompatActivity {

    private IjkVideoView ijkVideoView;
    private PlayerManager playerManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ijkVideoView = (IjkVideoView) findViewById(R.id.video_view);


    }
}
