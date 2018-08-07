package com.wkz.imagegetter.html;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.wkz.imagegetter.utils.ContextUtils;
import com.wkz.imagegetter.utils.ResourceUtils;
import com.wkz.imagegetter.utils.ScreenUtils;
import com.wkz.imagegetter.utils.SizeUtils;

public class HtmlDrawable extends Drawable {

    // 定义Bitmap的默认配置
    private static final Bitmap.Config BITMAP_CONFIG = Bitmap.Config.ARGB_8888;
    private static final int COLOR_DRAWABLE_DIMENSION = 1;
    @DrawableRes
    private static int DefaultDrawableId = ResourceUtils.getDrawableId("pic_bg_default");
    private static int DefaultDrawableWidth = ScreenUtils.getScreenWidth() - SizeUtils.dp2px(32);
    private static int DefaultDrawableHeight = SizeUtils.dp2px(185);

    private Drawable mDrawable;
    private Drawable mPlaceHolderDrawable;
    private int mWidth;
    private int mHeight;
    private int mRadius;

    public HtmlDrawable() {
        this(0, 0, SizeUtils.dp2px(5f));
    }

    public HtmlDrawable(@NonNull Bitmap bitmap) {
        this(new BitmapDrawable(ContextUtils.getContext().getResources(), bitmap));
    }

    public HtmlDrawable(@Nullable Drawable drawable) {
        this(0, 0, SizeUtils.dp2px(5f));
        this.mDrawable = drawable != null ? drawable : mPlaceHolderDrawable;
    }

    public HtmlDrawable(int width, int height, @IntRange(from = 0) int radius) {
        super();
        this.mWidth = width > 0 ? width : DefaultDrawableWidth;
        this.mHeight = height > 0 ? height : DefaultDrawableHeight;
        this.mRadius = radius;
        this.mDrawable = this.mPlaceHolderDrawable = ContextUtils.getContext().getResources().getDrawable(DefaultDrawableId);

        //设置宽高边界
        setBounds(new Rect(0, 0, mWidth, mHeight));
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        if (mDrawable == null || mWidth <= 0 || mHeight <= 0) {
            return;
        }
        Paint paint = new Paint();
        paint.setColor(0xffffffff);
        paint.setAntiAlias(true);

        canvas.saveLayer(0, 0, mWidth, mHeight, null, Canvas.ALL_SAVE_FLAG);

        RectF rectf = new RectF(0, 0, mWidth, mHeight);
        canvas.drawRoundRect(rectf, mRadius, mRadius, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN)); // SRC_IN 只显示两层图像交集部分的上层图像

        //Bitmap缩放,CenterCrop
        float scale;
        float dx = 0, dy = 0;
        if (mDrawable.getIntrinsicWidth() * mHeight > mWidth * mDrawable.getIntrinsicHeight()) {
            scale = (float) mHeight / (float) mDrawable.getIntrinsicHeight();
            dx = (mWidth - mDrawable.getIntrinsicWidth() * scale) * 0.5f;
        } else {
            scale = (float) mWidth / (float) mDrawable.getIntrinsicWidth();
            dy = (mHeight - mDrawable.getIntrinsicHeight() * scale) * 0.5f;
        }

        Matrix matrix = new Matrix();
        matrix.setScale(scale, scale);
        matrix.postTranslate(Math.round(dx), Math.round(dy));

        Bitmap bitmap = getBitmapFromDrawable(mDrawable);

        if (bitmap != null) {
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            //绘制图像 将bitmap对象显示在坐标 dx,dy上
            canvas.drawBitmap(bitmap, dx, dy, paint);
            canvas.restore();

            bitmap.recycle();
        }
    }

    // 获取Bitmap内容
    private Bitmap getBitmapFromDrawable(Drawable drawable) {
        try {
            Bitmap bitmap;
            if (drawable instanceof BitmapDrawable) {
                return ((BitmapDrawable) drawable).getBitmap();
            } else if (drawable instanceof ColorDrawable) {
                bitmap = Bitmap.createBitmap(COLOR_DRAWABLE_DIMENSION, COLOR_DRAWABLE_DIMENSION, BITMAP_CONFIG);
            } else {
                bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), BITMAP_CONFIG);
            }
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
            return bitmap;
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void setAlpha(@IntRange(from = 0, to = 255) int alpha) {
        if (mDrawable != null) {
            mDrawable.setAlpha(alpha);
        }
    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        if (mDrawable != null) {
            mDrawable.setColorFilter(colorFilter);
        }
    }

    @Override
    public int getOpacity() {
        if (mDrawable != null) {
            return mDrawable.getOpacity();
        }
        return PixelFormat.UNKNOWN;
    }

}
