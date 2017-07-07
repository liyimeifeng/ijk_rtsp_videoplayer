rtsp_player**目前支持的视频格式：rtsp、MP4、avi、rmvb、mkv、mov**

- **本项目有两个module可以选择依赖，libijkplayer、VideoIjkplayer**
	
		libijkplayer 建议使用，支持多种视频格式，可能会有1秒左右的延迟

		VideoIjkplayer 不建议使用，虽然也支持多种格式，但播放视频前会有2秒到3秒左右的白屏现象

* **具体使用参考app模块中的代码**

    	PlayerManager manager = new PlayerManager(ijkVideoView);
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

It could play rtsp stream by use ijkplayer 此工程基于IJKPlayer修改 当前IJKPlayer使用的库版本为k0.7.9 修改IJKPlayer的so文件支持rtsp 协议。 可以根据具体的需求修改参数

**用下面的方式设置参数，使用硬解码，可减少播放本地视频（如MP4）的卡顿现象**

					String pixelFormat = mSettings.getPixelFormat();
                    if (TextUtils.isEmpty(pixelFormat)) {
                        ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "overlay-format", IjkMediaPlayer.SDL_FCC_RV32);
                    } else {
                        ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "overlay-format", pixelFormat);
                    }
                    ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "framedrop", 1);
                    ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "start-on-prepared", 0);

                    ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "http-detect-range-support", 0);

                    ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_CODEC, "skip_loop_filter", 48);

                    //                    mHeaders.put("rtsp_transport", "tcp");
                    //                    mHeaders.put("rtsp_flags", "prefer_tcp");
                    ////////////////////////////////////////////////////
                    ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "packet-buffering", 0);
                    ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "fflags", "nobuffer");
                    ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "max-buffer-size", 512 * 1024);
                    ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "min-frames", 5);
                    ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "start-on-prepared", 1);
                    ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "probsize", 4096);
                    ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "analyzeduration", 2000000);
                    ///////////////////////////////////////////////////////
                    ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "probesize", 32);
                    ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "fflags", "nobuffer");
                    ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "sync", "ext");
                    ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "rtsp_transport", "tcp");
