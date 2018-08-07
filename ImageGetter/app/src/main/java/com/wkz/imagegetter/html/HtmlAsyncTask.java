package com.wkz.imagegetter.html;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.LevelListDrawable;
import android.os.AsyncTask;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.MalformedURLException;
import java.net.URL;

public class HtmlAsyncTask extends AsyncTask<String, Void, Bitmap> {

    private LevelListDrawable listDrawable;
    private SoftReference<TextView> mTvContent;

    public HtmlAsyncTask(TextView tv, LevelListDrawable listDrawable) {
        this.mTvContent = new SoftReference<>(tv);
        this.listDrawable = listDrawable;
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        String source = params[0];

        try {
            InputStream inputStream = new URL(source).openStream();
            return BitmapFactory.decodeStream(inputStream);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        super.onPostExecute(result);
        if (result != null) {

            HtmlDrawable htmlDrawable = new HtmlDrawable(result);
            listDrawable.addLevel(1, 1, htmlDrawable);
            listDrawable.setBounds(htmlDrawable.getBounds());
            listDrawable.setLevel(1);

            //textView.invalidate();
            //textView.postInvalidate();

            // update view
            CharSequence text = mTvContent.get().getText();
            mTvContent.get().setText(text);

        }
    }
}
