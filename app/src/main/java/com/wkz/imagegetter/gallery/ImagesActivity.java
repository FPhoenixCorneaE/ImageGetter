package com.wkz.imagegetter.gallery;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.chrisbanes.photoview.PhotoView;
import com.wkz.imagegetter.utils.ColorUtils;
import com.wkz.imagegetter.utils.ResourceUtils;
import com.wkz.imagegetter.utils.ScreenUtils;
import com.wkz.imagegetter.utils.StatusBarUtils;

import java.util.List;
import java.util.Locale;

public class ImagesActivity extends Activity implements ViewTreeObserver.OnPreDrawListener {

    public static final String IMAGE_ATTR = "image_attr";
    public static final String CUR_POSITION = "cur_position";
    public static final int ANIM_DURATION = 400; // ms

    private RelativeLayout rootView;
    private ViewPager viewPager;
    private TextView tvTip;
    private ImagesAdapter mAdapter;
    private List<ImageAttr> imageAttrs;

    private int curPosition;
    private int screenWidth;
    private int screenHeight;
    private float scaleX;
    private float scaleY;
    private float translationX;
    private float translationY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(ResourceUtils.getLayoutId("activity_images"));
        StatusBarUtils.setStatusBarTranslucent(this, false);

        viewPager = (ViewPager) findViewById(ResourceUtils.getId("viewPager"));
        tvTip = (TextView) findViewById(ResourceUtils.getId("tv_tip"));
        rootView = (RelativeLayout) findViewById(ResourceUtils.getId("rootView"));
        screenWidth = ScreenUtils.getScreenWidth();
        screenHeight = ScreenUtils.getScreenHeight();

        Intent intent = getIntent();
        imageAttrs = (List<ImageAttr>) intent.getSerializableExtra(IMAGE_ATTR);
        curPosition = intent.getIntExtra(CUR_POSITION, 0);
        tvTip.setText(String.format(Locale.getDefault(), "%1$d/%2$d", (curPosition + 1), imageAttrs.size()));

        mAdapter = new ImagesAdapter(this, imageAttrs);
        viewPager.setAdapter(mAdapter);
        viewPager.setCurrentItem(curPosition);
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                curPosition = position;
                tvTip.setText(String.format(Locale.getDefault(), "%1$d/%2$d", (curPosition + 1), imageAttrs.size()));
            }
        });
    }

    @Override
    public void onBackPressed() {
        finishWithAnim();
    }

    private void initImageAttr(ImageAttr attr) {
        int originalWidth = attr.getWidth();
        int originalHeight = attr.getHeight();
        int originalCenterX = attr.getLeft() + originalWidth / 2;
        int originalCenterY = attr.getTop() + originalHeight / 2;

        float widthRatio = screenWidth * 1.0f / originalWidth;
        float heightRatio = screenHeight * 1.0f / originalHeight;
        float ratio = widthRatio > heightRatio ? heightRatio : widthRatio;
        int finalWidth = (int) (originalWidth * ratio);
        int finalHeight = (int) (originalHeight * ratio);

        scaleX = originalWidth * 1.0f / finalWidth;
        scaleY = originalHeight * 1.0f / finalHeight;
        translationX = originalCenterX - screenWidth / 2;
        translationY = originalCenterY - screenHeight / 2;

        Log.e("--->", "(left, top): (" + attr.getLeft() + ", " + attr.getTop() + ")");
        Log.e("--->", "originalWidth: " + originalWidth + " originalHeight: " + originalHeight);
        Log.e("--->", "finalWidth: " + finalWidth + " finalHeight: " + finalHeight);
        Log.e("--->", "scaleX: " + scaleX + " scaleY: " + scaleY);
        Log.e("--->", "translationX: " + translationX + " translationY: " + translationY);
        Log.e("--->", "" + attr.toString());
        Log.e("--->", "----------------------------------------------------------------");
    }

    @Override
    public boolean onPreDraw() {
        viewPager.getViewTreeObserver().removeOnPreDrawListener(this);
        PhotoView photoView = mAdapter.getPhotoView(curPosition);
        ImageAttr attr = imageAttrs.get(curPosition);
        initImageAttr(attr);

        setBackgroundColor(0f, 1f, null);
        translateXAnim(photoView, translationX, 0);
        translateYAnim(photoView, translationY, 0);
        scaleXAnim(photoView, scaleX, 1);
        scaleYAnim(photoView, scaleY, 1);
        return true;
    }

    public void finishWithAnim() {
//        PhotoView photoView = mAdapter.getPhotoView(curPosition);
//        scaleXAnim(photoView, 1, 2);
//        scaleYAnim(photoView, 1, 2);
//        alphaAnim(photoView, 1, 0);
        finish();
    }

    private void alphaAnim(final PhotoView photoView, float from, float to) {
        ValueAnimator anim = ValueAnimator.ofFloat(from, to);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                photoView.setAlpha((Float) valueAnimator.getAnimatedValue());
            }
        });
        anim.setDuration(ANIM_DURATION);
        anim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                finish();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        anim.start();
    }

    private void translateXAnim(final PhotoView photoView, float from, float to) {
        ValueAnimator anim = ValueAnimator.ofFloat(from, to);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                photoView.setX((Float) valueAnimator.getAnimatedValue());
            }
        });
        anim.setDuration(ANIM_DURATION);
        anim.start();
    }

    private void translateYAnim(final PhotoView photoView, float from, float to) {
        ValueAnimator anim = ValueAnimator.ofFloat(from, to);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                photoView.setY((Float) valueAnimator.getAnimatedValue());
            }
        });
        anim.setDuration(ANIM_DURATION);
        anim.start();
    }

    private void scaleXAnim(final PhotoView photoView, float from, float to) {
        ValueAnimator anim = ValueAnimator.ofFloat(from, to);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                photoView.setScaleX((Float) valueAnimator.getAnimatedValue());
            }
        });
        anim.setDuration(ANIM_DURATION);
        anim.start();
    }

    private void scaleYAnim(final PhotoView photoView, float from, float to) {
        ValueAnimator anim = ValueAnimator.ofFloat(from, to);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                photoView.setScaleY((Float) valueAnimator.getAnimatedValue());
            }
        });
        anim.setDuration(ANIM_DURATION);
        anim.start();
    }

    private void setBackgroundColor(float from, float to, Animator.AnimatorListener listener) {
        ValueAnimator anim = ValueAnimator.ofFloat(from, to);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                rootView.setBackgroundColor(ColorUtils.evaluate((Float) valueAnimator.getAnimatedValue(), Color.TRANSPARENT, Color.BLACK));
            }
        });
        anim.setDuration(ANIM_DURATION);
        if (listener != null) {
            anim.addListener(listener);
        }
        anim.start();
    }

}
