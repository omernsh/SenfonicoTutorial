package com.omernsh.android.senfonicotutorial;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import com.omernsh.android.senfonicotutorial.R;

import java.util.List;


public class RecyclerViewAdapter extends RecyclerView.Adapter<ImageViewCatcher> {
    private List<Photo> mPhotosList;
    private Context mContext;
    private final String LOG_TAG = RecyclerViewAdapter.class.getSimpleName();

    public RecyclerViewAdapter(Context context, List<Photo> photosList) {
        mContext = context;
        this.mPhotosList = photosList;
    }

    @Override
    public ImageViewCatcher onCreateViewHolder(ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_view_item, null);
        ImageViewCatcher imageViewCatcher = new ImageViewCatcher(view);
        return imageViewCatcher;
    }

    @Override
    public void onBindViewHolder(ImageViewCatcher imageViewCatcher, int i) {
        Photo photoItem = mPhotosList.get(i);
        Log.d(LOG_TAG,"Processing: " + photoItem.getTitle() + " --> " + Integer.toString(i));

        Picasso.with(mContext).load(photoItem.getImage())
                .error(R.drawable.placeholder)
                .placeholder(R.drawable.placeholder)
                .into(imageViewCatcher.thumbnail);
        imageViewCatcher.title.setText(photoItem.getTitle());
        imageViewCatcher.info.setText(photoItem.getTags());
    }

    @Override
    public int getItemCount() {
        return (null != mPhotosList ? mPhotosList.size() : 0);
    }

    public void loadNewData(List<Photo> newPhotos) {
        mPhotosList = newPhotos;
        notifyDataSetChanged();
    }

    public Photo getPhoto(int position) {
        return (null != mPhotosList ? mPhotosList.get(position) : null);
    }
}
