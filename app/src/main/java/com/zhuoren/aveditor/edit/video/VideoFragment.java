package com.zhuoren.aveditor.edit.video;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhuoren.aveditor.R;
import com.zhuoren.aveditor.common.Click;
import com.zhuoren.aveditor.edit.EditItem;
import com.zhuoren.aveditor.edit.EditItemAdapter;

import java.util.ArrayList;
import java.util.List;


public class VideoFragment extends Fragment {
    public static final String TAG = "VideoFragment";
    public static final String TITLE = "";

    private RecyclerView mRecycleView;
    private EditItemAdapter mAdapter;
    private List<EditItem> mEditItemList;

    public static VideoFragment newInstance() {

        Bundle args = new Bundle();

        VideoFragment fragment = new VideoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mEditItemList = new ArrayList<>();
        mEditItemList.add(new EditItem(R.drawable.ic_video_puzz_black_24dp,
                VideoPuzzActivity.TITLE, VideoPuzzActivity.class));
        mEditItemList.add(new EditItem(R.drawable.ic_filter_black_24dp,
                VideoFilterActivity.TITLE, VideoFilterActivity.class));
        mEditItemList.add(new EditItem(R.drawable.ic_trans_code_black_24dp,
                VideoFlipActivity.TITLE, VideoFlipActivity.class));

        mEditItemList.add(new EditItem(R.drawable.ic_video_watermark_black_24dp,
                VideoWaterMarkActivity.TITLE, VideoWaterMarkActivity.class));
        mEditItemList.add(new EditItem(R.drawable.ic_un_logo_black_24dp,
                VideoNoWaterMarkActivity.TITLE, VideoNoWaterMarkActivity.class));
        mEditItemList.add(new EditItem(R.drawable.ic_demux_audio_black_24dp,
                AudioDeMuxActivity.TITLE, AudioDeMuxActivity.class));
        mEditItemList.add(new EditItem(R.drawable.ic_demux_video_black_24dp,
                VideoDeMuxActivity.TITLE, VideoDeMuxActivity.class));

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecycleView = view.findViewById(R.id.recycleView);
        GridLayoutManager manager = new GridLayoutManager(getContext(), 3);
        mRecycleView.setLayoutManager(manager);
        mAdapter = new EditItemAdapter(mEditItemList);
        mRecycleView.setAdapter(mAdapter);
        mAdapter.setEditItemOnClickListener(new Click.OnObjectClickListener<EditItem>() {
            @Override
            public void onObjectClick(EditItem editItem) {
                Intent intent = new Intent(getContext(), editItem.activityClass);
                if (getContext() != null) {
                    getContext().startActivity(intent);
                }
            }
        });
    }
}
