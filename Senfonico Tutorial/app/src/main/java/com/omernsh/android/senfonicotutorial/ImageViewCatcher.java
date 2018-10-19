package com.omernsh.android.senfonicotutorial;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;

import com.omernsh.android.senfonicotutorial.R;


public class ImageViewCatcher extends RecyclerView.ViewHolder {

    protected TextView title;
    protected TextView info;
    protected RoundedImageView thumbnail;

    public ImageViewCatcher(View view) {
        super(view);
        this.thumbnail = (RoundedImageView) view.findViewById(R.id.thumbnail);
        this.title = (TextView) view.findViewById(R.id.title);
        this.info = (TextView) view.findViewById(R.id.info);

    }
}
