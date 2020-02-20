package com.zhuoren.aveditor.edit.video;

import android.view.Menu;

import com.zhuoren.aveditor.common.App;
import com.zhuoren.aveditor.edit.EditMediaListActivity;
import com.zhuoren.aveditor.edit.MediaFile;
import com.zhuoren.aveditor.ffmpeg.Callback;
import com.zhuoren.aveditor.ffmpeg.FFmpegVideo;
import com.zhuoren.aveditor.util.FileUtil;
import com.zhuoren.aveditor.util.SnackBarUtil;

import java.io.File;


public class VideoWaterMarkActivity extends EditMediaListActivity {
    private static final String TAG = "VideoWaterMarkActivity";
    public static final String TITLE = "加水印";


    @Override
    protected String getEditTitle() {
        return TITLE;
    }

    @Override
    protected void createOptionsMenu(Menu menu) {
        menu.add("选择视频");
        menu.add("删除");
        menu.add("添加水印");
    }

    @Override
    protected void onMenuClick(int order) {
        if (order == 0) {
            for (MediaFile file : mMediaFileList) {
                if (file.getType() == MediaFile.TYPE_VIDEO) {
                    SnackBarUtil.showError(mRoot, "已经选择视频了");
                    return;
                }
            }
            pickVideo();
        } else if (order == 1) {
            deleteLastMediaFile();
        } else {
            if (mMediaFileList.size() == 0) {
                SnackBarUtil.showError(mRoot, "未选择视频");
                return;
            }
            showLoadingDialog();

            File file = new File(FileUtil.OUTPUT_VIDEO_DIR + File.separator +
                    "watermark.jpg");
            if (!file.exists()) {
                FileUtil.copyFromAssets(App.get(), "mark.jpg", file.getAbsolutePath());
            }
            final String output = FileUtil.OUTPUT_VIDEO_DIR + File.separator +
                    "watermark_" + TAG + ".mp4";
            FFmpegVideo.addWaterMark(mMediaFileList.get(0).getPath(),
                    file.getAbsolutePath(), 1, output, new Callback() {
                        @Override
                        public void onSuccess() {
                            dismissLoadingDialog();
                            showSaveDoneAndPlayDialog(output, true);
                        }

                        @Override
                        public void onLog(String log) {
                        }

                        @Override
                        public void onFail() {
                            dismissLoadingDialog();
                        }
                    });
        }
    }

}
