package com.example.nickbarratt.image_manipulation;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.PaintDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;

import static android.graphics.Color.GREEN;

/**
 * Created by nickbarratt on 29/12/2017.
 */

public class PinchZoomImageView extends android.support.v7.widget.AppCompatImageView {

    private Bitmap mBitmap;
    private Paint mPaint = new Paint();
    private boolean mtoggle = false;
    private int mImageWidth;
    private int mImageHeight;
    private int mColorRectangle;


    public PinchZoomImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.save();
        canvas.drawBitmap(mBitmap, 0, 0, null);
        canvas.drawRect(200, 200, 1200, 1980, mPaint);
        canvas.restore();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    public void setImageUri(Uri uri) {
        try {
         //   mColorBackground = ResourcesCompat.getColor(getResources(), R.color.colorBackground, null);
            mColorRectangle = ResourcesCompat.getColor(getResources(), R.color.colorRectangle, null);
          //  mColorAccent = ResourcesCompat.getColor(getResources(), R.color.colorAccent, null);
          //  Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), uri);
        //    float aspectRatio = (float) bitmap.getHeight() / (float) bitmap.getWidth();
       //     DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
       //     mImageWidth = displayMetrics.widthPixels;
      //      mImageHeight = Math.round(aspectRatio * mImageWidth);
      //      mBitmap = Bitmap.createScaledBitmap(bitmap, mImageWidth, mImageHeight, false);
            mBitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), uri);
            mPaint.setColor(mColorRectangle);
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setStrokeWidth(10);
            invalidate();
            requestLayout();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadFrame(View view){

        if (mtoggle==true) {
            mtoggle = false;
            mPaint.setColor(Color.parseColor("#FF00FF00"));
        }
        else {
            mtoggle = true;
            mPaint.setColor(Color.parseColor("#0000FF00"));
        }

        invalidate();
        requestLayout();
    }



}

