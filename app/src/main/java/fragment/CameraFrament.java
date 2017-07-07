package fragment;


import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dftc.videoijkplayer.PlayerManager;
import com.dftc.videoijkplayer.media.IjkVideoView;
import com.dftc.videoplay.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * A simple {@link Fragment} subclass.
 */
public class CameraFrament extends Fragment {
    private final static String TAG = CameraFrament.class.getSimpleName();
    private IjkVideoView ijkVideoView;
    private PlayerManager playerManager;
    private String url = "rtsp://192.168.2.67:8554/MainStream";    //监控摄像头测试可行
    private Timer timer = new Timer();
    private Handler handler = new Handler();
    public CameraFrament() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_camera_frament, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ijkVideoView = (IjkVideoView)view.findViewById(R.id.camera_ijkview);
        play();
        Log.e(TAG, "开始播放监控画面" );

        nextFragment();
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    public void nextFragment(){
        /*TimerTask task = new TimerTask() {
            @Override
            public void run() {
           getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new VideoFragment()).commit();

            }
        };
        timer.schedule(task,10,10 * 1000);*/

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new VideoFragment()).commit();
            }
        }, 30*1000);
    }

    private void play(){
        playerManager = new PlayerManager(ijkVideoView);
        playerManager.play(url, new PlayerManager.PlayerStateListener() {
            @Override
            public void onStart(boolean isStart, long currentTime, long videoTotalDuration) {

            }

            @Override
            public void onComplete(boolean isFinish, long currentTime) {

            }

            @Override
            public void onError(Object info, Object result) {

            }
        });
    }
}
