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
    private String url = "rtsp://192.168.2.67:8554/MainStream";    //监控摄像头测试可行
    private String url2 = "file:///storage/emulated/0/Download/thor-mp4.mp4";       //本地高清mp4视频，测试可行
    private String url3 = "file:///storage/emulated/0/Download/split.avi";      //avi可行

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ijkVideoView = (IjkVideoView) findViewById(R.id.test_video_view);
        stopButton = (Button)findViewById(R.id.stop_Button);
        restartButton = (Button)findViewById(R.id.restart_Button);
//        playerManager = PlayerManager.getInstance(this);


//        stopButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                playerManager.stopPlay(ijkVideoView);
//            }
//        });
//
//        restartButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                playerManager.play(url, new PlayerManager.PlayerStateListener() {
//                    @Override
//                    public void onStart(boolean isStart, long currentTime,long videoTotalDuration) {
//
//                    }
//
//                    @Override
//                    public void onComplete(boolean isFinish, long currentTime) {
//
//                    }
//
//                    @Override
//                    public void onError(Object info, Object result) {
//
//                    }
//                });
//            }
//        });

        playerManager = new PlayerManager(ijkVideoView);

            playerManager.play(url, new PlayerManager.PlayerStateListener() {
                @Override
                public void onStart(boolean isStart, long currentTime,long videoTotalDuration) {
                    Log.e(TAG, "onStart: " + isStart + "  //beginTime： "  + currentTime + "  //videoDuration：" + videoTotalDuration);
                }

                @Override
                public void onComplete(boolean isFinish, long currentTime) {
                    Log.e(TAG, "onStart: " + isFinish + "  //overTime： "  + currentTime);

                }

                @Override
                public void onError(Object info, Object result) {
                    Log.e(TAG, "onError: " + info + "==" + result );
                }
            });







    }


}
