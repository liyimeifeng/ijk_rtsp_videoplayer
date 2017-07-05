package com.dftc.videoplay;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import com.dftc.videoijkplayer.VideoPlayView;

public class MainActivity extends AbsVideoScreenSwitchActivity {

    private FrameLayout videoContainer;
    private Button btnPlay;
    private EditText editVideoUrl;

//    private String url = "rtsp://192.168.2.67:8554/MainStream";       //监控摄像头测试可行
//    private String url = "http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f30.mp4";   //测试可行
//    private String url = "rtsp://192.168.6.77:8554/730446.sdp";       //测试可行
//    private String url = "file:///storage/emulated/0/Download/apes.mp4";    //本地高清mp4视频，测试可行
//    private String url = "file:///storage/emulated/0/Download/rmvb-test.rmvb";    //rmvb不可行
//    private String url = "file:///storage/emulated/0/Download/avi-test.avi";      //avi不可行
        private String url = "file:///storage/emulated/0/Download/split.avi";      //avi不可行
//    private String url = "file:///storage/emulated/0/Download/mkv-test.mkv";        //mkv不可行

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        videoContainer = (FrameLayout) findViewById(R.id.video_view_container);
        editVideoUrl = (EditText) findViewById(R.id.edit_video_url);
        btnPlay = (Button) findViewById(R.id.btn_start);
        editVideoUrl.setText(url);
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = editVideoUrl.getText().toString();
                if (!TextUtils.isEmpty(url)) {
                    videoPlayer.start(url);
                }
            }
        });
    }

    @Override
    public void addVideoPlayerToContainer(VideoPlayView videoPlayer) {
        if (videoPlayer != null) {
            videoContainer.addView(videoPlayer, 0);
        }
    }
}
