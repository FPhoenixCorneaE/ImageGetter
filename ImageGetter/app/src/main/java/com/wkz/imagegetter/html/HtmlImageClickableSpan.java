package com.wkz.imagegetter.html;

import android.content.Context;
import android.content.Intent;
import android.text.style.ClickableSpan;
import android.view.View;

import com.wkz.imagegetter.gallery.ImageAttr;
import com.wkz.imagegetter.gallery.ImagesActivity;

import java.util.ArrayList;

public class HtmlImageClickableSpan extends ClickableSpan {

    private Context mContext;
    private ArrayList<ImageAttr> mImageList;
    private int mPosition;

    public HtmlImageClickableSpan(Context context, ArrayList<ImageAttr> images, int position) {
        this.mContext = context;
        this.mImageList = images;
        this.mPosition = position;
    }

    @Override
    public void onClick(View widget) {
        if (mImageList != null && !mImageList.isEmpty()) {
            Intent intent = new Intent(mContext, ImagesActivity.class);
            intent.putExtra(ImagesActivity.IMAGE_ATTR, mImageList);
            intent.putExtra(ImagesActivity.CUR_POSITION, mPosition);
            mContext.startActivity(intent);
        }
    }
}
