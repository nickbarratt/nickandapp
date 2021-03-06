package com.example.nickbarratt.image_manipulation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.FileDescriptor;
import java.io.IOException;

public class Test1MainActivity extends AppCompatActivity {

    private ImageView mImageView;
    private com.example.nickbarratt.image_manipulation.PinchZoomImageView mPinchZoomImageView;
    private Uri mImageUri, mStoreImageUri;
    private Animator mCurrentAnimator;
    private int mLongAnimationDuration;
    private Button mButton;

    private static final int REQUEST_OPEN_RESULT_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test1_main);

        mImageView = findViewById(R.id.imageView);
        mPinchZoomImageView = findViewById(R.id.pinchZoomImageView);
        mButton = findViewById(R.id.button);

        mImageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                //zoomImageFromThumb();
                //Toast.makeText(getApplicationContext(),"Image View Long Pressed", Toast.LENGTH_SHORT).show();
                pinchZoomPan();
                return true;
            }
        });

        mLongAnimationDuration = getResources().getInteger(android.R.integer.config_longAnimTime);

   //     Toast.makeText(getApplicationContext(),"OnCreate()", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_OPEN_RESULT_CODE);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    //    Toast.makeText(getApplicationContext(),"onSaveInstanceState", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
     //   Toast.makeText(getApplicationContext(),"onResume()", Toast.LENGTH_LONG).show();
        mImageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                //zoomImageFromThumb();
                //Toast.makeText(getApplicationContext(),"Image View Long Pressed", Toast.LENGTH_SHORT).show();
                pinchZoomPan();
                return true;
            }
        });

        if (mStoreImageUri != null)
            mImageUri = mStoreImageUri;
        Glide.with(this) // with Glide - separate thread - more memory efficient and runs faster
                .load(mImageUri)
                .into(mImageView);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    //    Toast.makeText(getApplicationContext(),"onRestoreInstanceState", Toast.LENGTH_LONG).show();
    }

    public void btnClick(View v){

       // Toast.makeText(getApplicationContext(), "Button Pressed", Toast.LENGTH_SHORT).show();
        loadFrameGraphics(v);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        View decorView = getWindow().getDecorView();

        if (hasFocus) {
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            );
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {
        if (requestCode == REQUEST_OPEN_RESULT_CODE && resultCode == RESULT_OK) {
            if (resultData != null) {
                mImageUri = resultData.getData();
              //  Toast.makeText(getApplicationContext(),"onActivityResult", Toast.LENGTH_LONG).show();
              //  Toast.makeText(getApplicationContext(), "onActivityResult", Toast.LENGTH_SHORT).show();
                /*
                try {
                    Bitmap bitmap = getBitmapFromUri(uri);
                    mImageView.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                */
                //Glide.with(this).load(uri).into(mImageView); // separate thread - more memory efficient and runs faster
                Glide.with(this) // with Glide - separate thread - more memory efficient and runs faster
                        .load(mImageUri)
                        .into(mImageView);

            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent=new Intent(this, Test1MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    //    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void pinchZoomPan() {
        mPinchZoomImageView.setImageUri(mImageUri);
        mImageView.setAlpha(0.f);
        mPinchZoomImageView.setVisibility(View.VISIBLE);
    }

    private void loadFrameGraphics(View v) {
        mPinchZoomImageView.loadFrame(v);
    }

}
