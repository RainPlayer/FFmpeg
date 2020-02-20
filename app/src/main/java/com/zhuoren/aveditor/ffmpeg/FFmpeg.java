package com.zhuoren.aveditor.ffmpeg;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.zhuoren.aveditor.common.App;
import com.zhuoren.aveditor.common.AppExecutors;
import com.zhuoren.aveditor.common.Logger;
import com.zhuoren.aveditor.common.PermissionHelper;
import com.zhuoren.aveditor.common.VideoUtil;

import java.util.List;
import java.util.Objects;



enum FFmpeg {
    instance;

    static FFmpeg getInstance() {
        return instance;
    }

    private volatile boolean mIsRunning = false;

    boolean isRunning() {
        return mIsRunning;
    }

    FFmpeg() {
//        AppExecutors.executeWork(mProgressRunnable);
    }

    private Callback mCallback;

    void run(@NonNull List<String> list, @Nullable final Callback callback) {
        String[] commands = new String[list.size()];
        list.toArray(commands);
        run(commands, callback);
    }

    private void run(@NonNull final String[] cmd, @Nullable final Callback callback) {
        if (!PermissionHelper.hasWriteAndReadStoragePermission(App.get())) {
            AppExecutors.executeMain(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(App.get(), "请开启读写权限！", Toast.LENGTH_SHORT).show();
                }
            });
            return;
        }
        Objects.requireNonNull(cmd);
        if (mIsRunning) {
            throw new IllegalStateException("FFmpeg IsRunning");
        }
        mCallback = callback;
        AppExecutors.executeWork(new Runnable() {
            @Override
            public void run() {
                mIsRunning = true;
                int ret = 1;
                try {
                    ret = FFmpegJni.execute(cmd);
                    done(callback, ret != 1);
                } catch (Exception e) {
                    done(callback, ret != 1);
                }
                mIsRunning = false;
            }
        });
    }

    private void done(final Callback callback, final boolean success) {
        if (callback != null) {
            AppExecutors.executeMain(new Runnable() {
                @Override
                public void run() {
                    if (success) {
                        callback.onSuccess();
                    } else {
                        callback.onFail();
                    }
                }
            });
        }
    }


    private final Runnable mProgressRunnable = new Runnable() {
        @Override
        public void run() {
            for (; ; ) {
                if (isRunning()) {
                    if (mCallback != null) {
                        String log = FFmpegJni.getLog();
                        Logger.d("FFmpeg", log);
                        mCallback.onLog(log);
                    }
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };
}
