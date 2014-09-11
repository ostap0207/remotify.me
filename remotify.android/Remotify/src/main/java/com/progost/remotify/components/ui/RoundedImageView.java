package com.progost.remotify.components.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by Ostap on 6/5/2014.
 */
public class RoundedImageView extends ImageView {

    public RoundedImageView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    public RoundedImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RoundedImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        Drawable drawable = getDrawable();

        if (drawable == null) {
            return;
        }

        if (getWidth() == 0 || getHeight() == 0) {
            return;
        }
        Bitmap b =  ((BitmapDrawable)drawable).getBitmap() ;
        Bitmap bitmap = b.copy(Bitmap.Config.ARGB_8888, true);

        int w = getWidth(), h = getHeight();


        BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);

// init paint
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(shader);

        int circleCenter = w / 2;

        canvas.drawCircle(circleCenter, circleCenter, w, paint);
    }

//    public static Bitmap getCroppedBitmap(Bitmap bmp, int radius) {
//        BitmapShader shader;
//        shader = new BitmapShader(bmp, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
//
//// init paint
//        Paint paint = new Paint();
//        paint.setAntiAlias(true);
//        paint.setShader(shader);
//
//        int circleCenter = radius / 2;
//
//// circleCenter is the x or y of the view's center
//// radius is the radius in pixels of the cirle to be drawn
//// paint contains the shader that will texture the shape
//
//    }

}
