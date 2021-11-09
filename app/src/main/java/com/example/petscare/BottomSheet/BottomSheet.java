package com.example.petscare.BottomSheet;

import android.widget.ImageView;
import android.widget.TextView;

public class BottomSheet {
    ImageView imageView;
    TextView header,subheader;

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public TextView getHeader() {
        return header;
    }

    public void setHeader(TextView header) {
        this.header = header;
    }

    public TextView getSubheader() {
        return subheader;
    }

    public void setSubheader(TextView subheader) {
        this.subheader = subheader;
    }
}
