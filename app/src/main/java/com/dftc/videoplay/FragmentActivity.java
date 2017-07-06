package com.dftc.videoplay;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import fragment.CameraFrament;
import fragment.VideoFragment;

public class FragmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        Fragment video_fragment = new VideoFragment();
        Fragment camera_fragment = new CameraFrament();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,video_fragment).commitAllowingStateLoss();
//        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,camera_fragment).commitAllowingStateLoss();
    }


}
