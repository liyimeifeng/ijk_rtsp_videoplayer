package com.dftc.videoijkplayer;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.media.AudioManager;
import android.util.Log;
import android.view.GestureDetector;
import android.view.View;
import android.view.ViewGroup;

import com.dftc.videoijkplayer.media.IjkVideoView;

import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

/**
 * 视频播放管理类
 *
 * Created by Lee on 2017/7/5 0005.
 */

public class PlayerManager {

    private IjkVideoView videoPlayer;
    private String url;
    private Boolean playerSupport = false;
//    private final IjkVideoView videoView;


    /**
     * 开始播放
     * @param context
     * @param videoPath   支持rtsp流，离线MP4、avi、rmvb、mkv等格式
     */
//    public void startPlay(Context context,String videoPath){
//        videoPlayer = new VideoPlayView(context);
//        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.MATCH_PARENT);
//        videoPlayer.setLayoutParams(params);
//        videoPlayer.start(videoPath);
//    }
//
//    public void stopPlay(){
//
//    }


    public PlayerManager(final Activity activity) {
    }


//    public void startPlay(String videoPath){
//        this.url = videoPath;
//        videoView.setVideoPath(url);
//        videoView.start();
//
//    }




}
