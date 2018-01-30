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
    private Bitmap mBitmapGraphics;
    private int mImageWidth;
    private int mImageHeight;
    private final static float mMinZoom = 1.f;
    private final static float mMaxZoom = 2.f;
    private float mScaleFactor = 1.f;
    private ScaleGestureDetector mScaleGestureDetector;
    private final static int NONE = 0;
    private final static int PAN = 1;
    private final static int ZOOM = 2;
    private int mEventState;
    private float mStartX = 0;
    private float mStartY = 0;
    private float mTranslateX = 0;
    private float mTranslateY = 0;
    private float mPreviousTranslateX = 0;
    private float mPreviousTranslateY = 0;
    private View view;
    private Paint mPaint = new Paint();
    private Paint mPaintText = new Paint(Paint.UNDERLINE_TEXT_FLAG);
    private Rect mRect = new Rect();
    private Rect mRectF = new Rect();
    private Rect mBounds = new Rect();
    private int mColorBackground;
    private int mColorRectangle;
    private int mColorAccent;
    private boolean mtoggle = false;

/*
    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {

        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            mScaleFactor *= detector.getScaleFactor();
            mScaleFactor = Math.max(mMinZoom, Math.min(mMaxZoom, mScaleFactor));
        //    invalidate();
        //    requestLayout();
            return super.onScale(detector);
        }
    }




    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                mEventState = PAN;
                mStartX = event.getX() - mPreviousTranslateX;
                mStartY = event.getY() - mPreviousTranslateY;
                break;
            case MotionEvent.ACTION_UP:
                mEventState = NONE;
                mPreviousTranslateX = mTranslateX;
                mPreviousTranslateY = mTranslateY;
                break;
            case MotionEvent.ACTION_MOVE:
                mTranslateX = event.getX() - mStartX;
                mTranslateY = event.getY() - mStartY;
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                mEventState = ZOOM;
                break;
        }
        mScaleGestureDetector.onTouchEvent(event);
        if ((mEventState == PAN && mScaleFactor != mMinZoom) || mEventState == ZOOM) {
            invalidate();
            requestLayout();
        }
        return true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int imageWidth = MeasureSpec.getSize(widthMeasureSpec);
        int imageHeight = MeasureSpec.getSize(heightMeasureSpec);
        int scaledWidth = Math.round(mImageWidth * mScaleFactor);
        int scaledHeight = Math.round(mImageHeight * mScaleFactor);

        setMeasuredDimension(
                Math.min(imageWidth, scaledWidth),
                Math.min(imageHeight,scaledHeight)
        );
    }
*/

    public PinchZoomImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

   //     mScaleGestureDetector = new ScaleGestureDetector(getContext(), new ScaleListener());
    }

    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);

        canvas.save();
/*
        canvas.scale(mScaleFactor, mScaleFactor);
        if ((mTranslateX * -1) < 0) {
            mTranslateX = 0;
        } else if ((mTranslateX * -1) > mImageWidth * mScaleFactor - getWidth()){
            mTranslateX = (mImageWidth * mScaleFactor - getWidth()) * -1;
        }
        if ((mTranslateY * -1) < 0) {
            mTranslateY = 0;
        } else if ((mTranslateY * -1) > mImageHeight * mScaleFactor - getHeight()){
            mTranslateY = (mImageHeight * mScaleFactor - getHeight()) * -1;
        }

        canvas.translate(mTranslateX / mScaleFactor, mTranslateY / mScaleFactor);
*/
        canvas.drawBitmap(mBitmap, 0, 0, null);

        canvas.drawRect(200, 200, 1200, 1980, mPaint);
     //   canvas.drawCircle(400,400,100, mPaint);

        canvas.restore();


    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    public void setImageUri(Uri uri) {
        try {
            mColorBackground = ResourcesCompat.getColor(getResources(), R.color.colorBackground, null);
            mColorRectangle = ResourcesCompat.getColor(getResources(), R.color.colorRectangle, null);
            mColorAccent = ResourcesCompat.getColor(getResources(), R.color.colorAccent, null);
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), uri);
            float aspectRatio = (float) bitmap.getHeight() / (float) bitmap.getWidth();
            DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
            mImageWidth = displayMetrics.widthPixels;
            mImageHeight = Math.round(aspectRatio * mImageWidth);
            mBitmap = Bitmap.createScaledBitmap(bitmap, mImageWidth, mImageHeight, false);
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

