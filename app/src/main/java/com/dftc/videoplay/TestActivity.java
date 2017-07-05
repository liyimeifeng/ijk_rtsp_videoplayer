package com.dftc.videoplay;

import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.dftc.videoijkplayer.PlayerManager;
import com.dftc.videoijkplayer.media.IjkVideoView;

import tv.danmaku.ijk.media.player.IMediaPlayer;

public class TestActivity extends AppCompatActivity {

    private  final static String TAG = TestActivity.class.getSimpleName();
    private IjkVideoView ijkVideoView;
    private Button stopButton ,restartButton;
    private PlayerManager playerManager;
    private String url = "file:///storage/emulated/0/Download/thor-mp4.mp4";    //本地高清mp4视频，测试可行
    private String url2 = "rtsp://192.168.2.67:8554/MainStream";       //监控摄像头测试可行

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

//        playerManager = new PlayerManager(this);
        ijkVideoView = (IjkVideoView) findViewById(R.id.video_view);
        stopButton = (Button)findViewById(R.id.stop_Button);
        restartButton = (Button)findViewById(R.id.restart_Button);
//        playerManager.play(url);
        playerManager = PlayerManager.getInstance(this);
//        playerManager.setPlayerStateListener()
        playerManager.play(url, new PlayerManager.PlayerStateListener() {
            @Override
            public void onStart(boolean isStart, long currentTime) {

            }

            @Override
            public void onComplete(boolean isFinish, long currentTime) {

            }

            @Override
            public void onError(Object info, Object result) {

            }
        });




        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerManager.stopPlay(ijkVideoView);
            }
        });

        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerManager.play(url2, new PlayerManager.PlayerStateListener() {
                    @Override
                    public void onStart(boolean isStart, long currentTime) {

                    }

                    @Override
                    public void onComplete(boolean isFinish, long currentTime) {

                    }

                    @Override
                    public void onError(Object info, Object result) {

                    }
                });
            }
        });
    }
}
