package com.dftc.libijkplayer;


import android.net.Uri;
import android.util.Log;
import android.view.View;

import com.dftc.libijkplayer.widget.media.IRenderView;
import com.dftc.libijkplayer.widget.media.IjkVideoView;

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
    private long videoTotalDuration;
    private static int MEDIA_ERROR_UNSUPPORTED = 0;
    private static int MEDIA_INFO_BAD_INTERLEAVING =1;

    static {
        try {
            IjkMediaPlayer.loadLibrariesOnce(null);
            IjkMediaPlayer.native_profileBegin("libijkplayer.so");
        } catch (Throwable e) {
            Log.e("ijkPlayer", "loadLibraries error", e);
        }
    }

    public PlayerManager(IjkVideoView ijkView){
        ijkVideoView = ijkView;
        ijkVideoView.setAspectRatio(IRenderView.AR_MATCH_PARENT);  //默认拉伸填充整个布局
    }

    private static final class Instance{
        private static PlayerManager instance = new PlayerManager(ijkVideoView);
    }

    //单例
    public static PlayerManager getInstance(IjkVideoView ijkview){
        try {
            IjkMediaPlayer.loadLibrariesOnce(null);
            IjkMediaPlayer.native_profileBegin("libijkplayer.so");
        } catch (Throwable e) {
            Log.e("ijkPlayer", "loadLibraries error", e);
        }
        ijkVideoView = ijkview;
        ijkVideoView.setAspectRatio(IRenderView.AR_MATCH_PARENT);
        return Instance.instance;
    }

    /**
     * 开始播放
     * @param videoPath     接受本地视频路径，rtsp流链接
     */
    public void play(String videoPath,PlayerStateListener listener) {

        play(Uri.parse(videoPath),listener);
//        url  = videoPath;
//        this.playerStateListener = listener;
//        ijkVideoView.setVideoPath(url);
//        ijkVideoView.start();
    }

    public void play(Uri uri,PlayerStateListener listener){
        playerStateListener = listener;
        ijkVideoView.setVideoURI(uri);
        ijkVideoView.start();

        getPlayState();
    }

    /**
     * 设置播放状态监听
     */
    public void getPlayState(){
        ijkVideoView.setOnCompletionListener(new IMediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(IMediaPlayer mp) {
                Log.e(TAG, "播放完毕" );
                playerStateListener.onComplete(true,System.currentTimeMillis());
                //播放完毕释放资源
                if(ijkVideoView != null){
                    ijkVideoView.stopPlayback();
                    ijkVideoView.release(true);
                    ijkVideoView.setVisibility(View.INVISIBLE);
                }
            }
        });
        ijkVideoView.setOnErrorListener(new IMediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(IMediaPlayer mp, int what, int extra) {
                playerStateListener.onStart(false,System.currentTimeMillis(),-1);
                playerStateListener.onError(what,extra);
                if(ijkVideoView != null){
                    ijkVideoView.stopPlayback();
                    ijkVideoView.release(true);
                }
                return true;
            }
        });

        ijkVideoView.setOnInfoListener(new IMediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(IMediaPlayer mp, int what, int extra) {
                switch (what) {
                    case IMediaPlayer.MEDIA_INFO_BUFFERING_START:
                        Log.e(TAG, "MEDIA_INFO_BUFFERING_START");
                        break;
                    case IMediaPlayer.MEDIA_INFO_BUFFERING_END:
                        Log.e(TAG, "MEDIA_INFO_BUFFERING_END");
                        break;
                    case IMediaPlayer.MEDIA_INFO_NETWORK_BANDWIDTH:
                        Log.e(TAG, "MEDIA_INFO_NETWORK_BANDWIDTH");
                        break;
                    case IMediaPlayer.MEDIA_ERROR_UNSUPPORTED:     //播放失败的情况下，返回false和相应信息
                        playerStateListener.onStart(false,System.currentTimeMillis(),-1);
                        playerStateListener.onError(MEDIA_ERROR_UNSUPPORTED," video format unsupport");
                        break;
                    case IMediaPlayer.MEDIA_INFO_BAD_INTERLEAVING:   //播放失败的情况下，返回false和相应信息
                        playerStateListener.onStart(false,System.currentTimeMillis(),-1);
                        playerStateListener.onError(MEDIA_INFO_BAD_INTERLEAVING,"media bad interleaving");
                        break;
                    case IMediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START:
                        long currentTime = System.currentTimeMillis();
                        videoTotalDuration = mp.getDuration();
                        playerStateListener.onStart(true,currentTime,mp.getDuration());
                        Log.e(TAG, "MEDIA_INFO_VIDEO_RENDERING_START" + "====="+  mp.getDuration());
                        break;
                }
                return false;
            }
        });
    }


    /**
     * 获取视频总时长
     * @return
     */
    public long getVideoDuration(){
        if (ijkVideoView != null){
            return videoTotalDuration;
        }
        return -1;
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

    public PlayerManager setPlayerStateListener(PlayerStateListener Listener){
        this.playerStateListener = Listener;
        return this;
    }

    public interface PlayerStateListener{
        /**
         * 开始播放回调，返回当前时间戳、视频总时长（播放监控则返回的总时长为0）
         * @param isStart    true表示开始播放
         * @param currentTime
         */
        void onStart(boolean isStart, long currentTime, long videoTotalDuration);

        /**
         * 播放完成回调， 返回当前时间戳
         * @param isFinish   true表示播放完毕
         * @param currentTime
         */
        void onComplete(boolean isFinish, long currentTime);

        /**
         * 播放失败回调，返回相应失败信息
         * @param info
         * @param result
         */
        void onError(Object info, Object result);

    }
}
