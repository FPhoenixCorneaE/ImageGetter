package com.wkz.imagegetter.html;

import android.content.Context;
import android.text.Editable;
import android.text.Html;
import android.text.Spanned;
import android.text.style.ImageSpan;

import com.wkz.imagegetter.gallery.ImageAttr;

import org.xml.sax.XMLReader;

import java.util.ArrayList;
import java.util.Locale;

public class HtmlTagHandler implements Html.TagHandler {

    private Context context;
    private ArrayList<ImageAttr> mImageAttrs;

    public HtmlTagHandler(Context context) {
        this.context = context;
        this.mImageAttrs = new ArrayList<>();
    }

    @Override
    public void handleTag(boolean opening, String tag, Editable output, XMLReader xmlReader) {
        // 处理标签<img>
        if ("img".equals(tag.toLowerCase(Locale.getDefault()))) {
            // 获取长度
            int len = output.length();
            // 获取图片地址
            ImageSpan[] images = output.getSpans(len - 1, len, ImageSpan.class);
            String imgURL = images[0].getSource();
            // 记录所有图片地址
            mImageAttrs.add(new ImageAttr()
                    .setUrl(imgURL)
                    .setThumbnailUrl(imgURL)
                    .setWidth(images[0].getDrawable().getIntrinsicWidth())
                    .setHeight(images[0].getDrawable().getIntrinsicHeight())
            );
            // 记录是第几张图片
            int position = mImageAttrs.size() - 1;

            // 使图片可点击并监听点击事件
            output.setSpan(new HtmlImageClickableSpan(context, mImageAttrs, position),
                    len - 1, len, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
    }
}
