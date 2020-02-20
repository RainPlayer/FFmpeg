package com.zhuoren.aveditor.edit;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zhuoren.aveditor.R;
import com.zhuoren.aveditor.common.Click;

import java.util.List;

public class ItemMediaAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private List<MediaFile> mediaFiles;

    private Click.OnObjectClickListener<MediaFile> mMediaFileOnClickListener;

    public ItemMediaAdapter(List<MediaFile> mediaFiles) {
        this.mediaFiles = mediaFiles;
    }

    public void setEditItemOnClickListener(Click.OnObjectClickListener<MediaFile> mEditItemOnClickListener) {
        this.mMediaFileOnClickListener = mEditItemOnClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_media, viewGroup, false);
        return new ItemVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof ItemVH) {
            ItemVH itemVH = (ItemVH) viewHolder;
            itemVH.bind(mediaFiles.get(i), i);
        }
    }

    @Override
    public int getItemCount() {
        return mediaFiles == null ? 0 : mediaFiles.size();
    }

    @Override
    public void onClick(View v) {
        if (v.getTag() instanceof MediaFile) {
            if (mMediaFileOnClickListener != null) {
                mMediaFileOnClickListener.onObjectClick((MediaFile) v.getTag());
            }
        }
    }

    class ItemVH extends RecyclerView.ViewHolder {
        private ImageView mIvMedia;
        private TextView mTvMediaTitle;
        private TextView mTvMediaPath;

        ItemVH(@NonNull View itemView) {
            super(itemView);
            mIvMedia = itemView.findViewById(R.id.iv_media);
            mTvMediaTitle = itemView.findViewById(R.id.tv_media_title);
            mTvMediaPath = itemView.findViewById(R.id.tv_media_path);
            itemView.setOnClickListener(ItemMediaAdapter.this);
        }

        void bind(MediaFile mediaFile, int pos) {
            switch (mediaFile.getType()) {
                case MediaFile.TYPE_AUDIO:
                    Glide.with(mIvMedia).load(R.drawable.audio_black).into(mIvMedia);
                    break;
                case MediaFile.TYPE_VIDEO:
                    Glide.with(mIvMedia).asBitmap().load(mediaFile.getPath()).into(mIvMedia);
                    break;
                case MediaFile.TYPE_IMG:
                    Glide.with(mIvMedia).asBitmap().load(mediaFile.getPath()).into(mIvMedia);
                    break;
            }
            mTvMediaPath.setText(mediaFile.getPath());
            mTvMediaTitle.setText(mediaFile.getName());
            itemView.setTag(mediaFile);
        }
    }
}
