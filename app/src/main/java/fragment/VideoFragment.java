package fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dftc.videoijkplayer.PlayerManager;
import com.dftc.videoijkplayer.media.IjkVideoView;
import com.dftc.videoplay.R;


public class VideoFragment extends Fragment {

    private final static String TAG = VideoFragment.class.getSimpleName();
    private IjkVideoView ijkVideoView;
    private PlayerManager playerManager;
    private String url2 = "file:///storage/emulated/0/Download/thor-mp4.mp4";       //本地高清mp4视频，测试可行
    private String url3 = "file:///storage/emulated/0/Download/split.avi";      //avi可行

    public VideoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_video_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ijkVideoView = (IjkVideoView)view.findViewById(R.id.video_ijkview);
        Log.e(TAG, "开始播放视频画面");
        play();
    }

    public void play(){
        playerManager = new PlayerManager(ijkVideoView);
        playerManager.play(url2, new PlayerManager.PlayerStateListener() {
            @Override
            public void onStart(boolean isStart, long currentTime, long videoTotalDuration) {

            }

            @Override
            public void onComplete(boolean isFinish, long currentTime) {
                Fragment camera_fragment = new CameraFrament();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,camera_fragment).commit();
            }

            @Override
            public void onError(Object info, Object result) {

            }
        });

    }
}
