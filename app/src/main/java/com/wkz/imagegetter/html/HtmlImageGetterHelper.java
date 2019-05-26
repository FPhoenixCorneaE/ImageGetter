package com.wkz.imagegetter.html;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.LevelListDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.Html.ImageGetter;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.lang.ref.SoftReference;


public class HtmlImageGetterHelper implements ImageGetter {

    private static volatile HtmlImageGetterHelper mHelper;
    private SoftReference<TextView> mTvContent;

    private HtmlImageGetterHelper() {
    }

    public static HtmlImageGetterHelper getHelper() {
        if (mHelper == null) {
            synchronized (HtmlImageGetterHelper.class) {
                if (mHelper == null) {
                    mHelper = new HtmlImageGetterHelper();
                }
            }
        }
        return mHelper;
    }

    public void setHtmlString(TextView targetView, String htmlString) {
        this.mTvContent = new SoftReference<>(targetView);
        this.mTvContent.get().setText(Html.fromHtml(htmlString, this, new HtmlTagHandler(targetView.getContext())));
        //使图片点击事件生效
        this.mTvContent.get().setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    public Drawable getDrawable(String source) {

        final LevelListDrawable listDrawable = new LevelListDrawable();

        HtmlDrawable empty = new HtmlDrawable();
        listDrawable.addLevel(0, 0, empty);
        listDrawable.setBounds(empty.getBounds());

        //使用Glide框架加载图片
        Glide.with(mTvContent.get())
                .load(source)
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {

                        HtmlDrawable htmlDrawable = new HtmlDrawable(resource);
                        listDrawable.addLevel(1, 1, htmlDrawable);
                        listDrawable.setBounds(htmlDrawable.getBounds());
                        listDrawable.setLevel(1);


                        // update view
                        CharSequence text = mTvContent.get().getText();
                        mTvContent.get().setText(text);
                    }
                });

        //使用异步任务加载图片
//        new HtmlAsyncTask(mTvContent.get(), listDrawable).execute(source);

        return listDrawable;
    }

}
