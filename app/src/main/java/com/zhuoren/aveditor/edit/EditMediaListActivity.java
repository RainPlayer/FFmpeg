package com.zhuoren.aveditor.edit;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.zhuoren.aveditor.R;
import com.zhuoren.aveditor.common.Click;

import java.util.ArrayList;
import java.util.List;


public abstract class EditMediaListActivity extends BaseEditActivity {


    protected RecyclerView mRecyclerView;
    protected ItemMediaAdapter mAdapter;
    protected List<MediaFile> mMediaFileList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_list);
        mRecyclerView = findViewById(R.id.recycleView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mMediaFileList = new ArrayList<>();
        mAdapter = new ItemMediaAdapter(mMediaFileList);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setEditItemOnClickListener(new Click.OnObjectClickListener<MediaFile>() {
            @Override
            public void onObjectClick(MediaFile mediaFile) {
                onMediaFileClick(mediaFile);
            }
        });
    }

    protected void onMediaFileClick(@NonNull MediaFile mediaFile) {
        if (mediaFile.getType() == MediaFile.TYPE_VIDEO) {
            playVideo(mediaFile.getPath());
        } else if (mediaFile.getType() == MediaFile.TYPE_AUDIO) {
            playAudio(mediaFile.getPath());
        }
    }


    protected void deleteLastMediaFile() {
        if (mMediaFileList.size() < 1) {
            return;
        }
        mMediaFileList.remove(mMediaFileList.size() - 1);
        mAdapter.notifyItemRemoved(mMediaFileList.size());
    }


    @Override
    protected void onPickFile(@NonNull MediaFile mediaFile) {
        super.onPickFile(mediaFile);
        mMediaFileList.add(mediaFile);
        mAdapter.notifyItemInserted(mMediaFileList.size());
    }
}
