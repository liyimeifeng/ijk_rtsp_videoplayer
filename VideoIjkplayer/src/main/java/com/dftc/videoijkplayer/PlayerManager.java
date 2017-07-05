package com.dftc.videoijkplayer;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.media.AudioManager;
import android.net.Uri;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;

import com.dftc.videoijkplayer.media.IjkVideoView;

import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

/**
 * ijkplayer视频播放管理类
 * Created by Lee on 2017/7/5 0005.
 */

public class PlayerManager {

    private final static String TAG = PlayerManager.class.getSimpleName();
    private String url;
    private static IjkVideoView ijkVideoView;
    private PlayerStateListener playerStateListener;

    private static final class Instance{
        private static PlayerManager instance = new PlayerManager();
    }

    //单例
    public static PlayerManager getInstance(Activity activity){
//        ijkVideoView = VideoView;
        ijkVideoView = (IjkVideoView) activity.findViewById(R.id.main_video);
        return Instance.instance;
    }

    /**
     * 开始播放
     * @param videoPath     本地视频路径，rtsp流链接
     * @param ijkVideoView
     */
    public void play(String videoPath,PlayerStateListener listener) {
        url  = videoPath;
        long currentTime = System.currentTimeMillis();
        playerStateListener.onStart(true,currentTime);
        ijkVideoView.setVideoPath(url);
        ijkVideoView.start();


        ijkVideoView.setOnCompletionListener(new IMediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(IMediaPlayer mp) {
                    playerStateListener.onComplete(true,System.currentTimeMillis());
            }
        });
        ijkVideoView.setOnErrorListener(new IMediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(IMediaPlayer mp, int what, int extra) {
                playerStateListener.onError(what,extra);
                return true;
            }
        });
        ijkVideoView.setOnInfoListener(new IMediaPlayer.OnInfoListener() {
                                        @Override
                                        public boolean onInfo(IMediaPlayer mp, int what, int extra) {
                                            switch (what) {
                                                case IMediaPlayer.MEDIA_INFO_BUFFERING_START:
                                                    break;
                                                case IMediaPlayer.MEDIA_INFO_BUFFERING_END:break;
                                                case IMediaPlayer.MEDIA_INFO_NETWORK_BANDWIDTH:
                                                    //显示下载速度
//                      Toast.show("download rate:" + extra);
                                                    break;
                                                case IMediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START:
                                                    break;
                                            }
                                            return false;
                                        }
    });
    }

    /**
     * 停止播放
     * @param ijkVideoView
     */
    public void stopPlay(IjkVideoView ijkVideoView){
        if (ijkVideoView.isPlaying()) {
            ijkVideoView.stopPlayback();
        }
        ijkVideoView.release(true);
    }

    public void onComlete(){
        ijkVideoView.setOnCompletionListener(new IMediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(IMediaPlayer iMediaPlayer) {
                Log.e(TAG, "onCompletion: "   +  System.currentTimeMillis());
            }
        });

    }


    public interface PlayerStateListener{
        /**
         * 开始播放回调，返回当前时间戳
         * @param isStart    返回true即表示开始播放
         * @param currentTime
         */
        void onStart(boolean isStart,long currentTime);

        /**
         * 播放完成回调， 返回当前时间戳
         * @param isFinish   返回true即表示播放完毕
         * @param currentTime
         */
        void onComplete(boolean isFinish,long currentTime);

        /**
         * 播放失败回调，返回相应信息
         * @param info
         * @param result
         */
        void onError(Object info ,Object result);

    }
}
