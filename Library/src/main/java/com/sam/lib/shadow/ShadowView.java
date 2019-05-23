package com.sam.lib.shadow;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class ShadowView extends View implements OnTouchListener {

    private int mAnimationInId, mAnimationOutId;
    private Animation mAlphaIn;
    private Animation mAlphaOut;

    private static final int DEFAULT_COLOR = Color.parseColor("#55000000");
    private static final int DEFAULT_DURATION = 500;
    private int mShadowColor;
    private int mDuration;

    public interface TouchListener {
        void onTouch();
    }

    private TouchListener mTouchListener;

    public ShadowView(Context context) {
        this(context, null);
    }

    public ShadowView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShadowView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ShadowView);
        mShadowColor = ta.getColor(R.styleable.ShadowView_svShadowColor, DEFAULT_COLOR);
        mDuration = ta.getInteger(R.styleable.ShadowLayout_android_duration, -1);
        mAnimationInId = ta.getResourceId(R.styleable.ShadowLayout_android_inAnimation, -1);
        mAnimationOutId = ta.getResourceId(R.styleable.ShadowLayout_android_outAnimation, -1);

        ta.recycle();

        if (mAnimationInId != -1) {
            mAlphaIn = AnimationUtils.loadAnimation(getContext(), mAnimationInId);
        } else {
            mAlphaIn = new AlphaAnimation(0, 1);
            mAlphaIn.setDuration(DEFAULT_DURATION);
        }

        if (mAnimationOutId != -1) {
            mAlphaOut = AnimationUtils.loadAnimation(getContext(), mAnimationOutId);
        } else {
            mAlphaOut = new AlphaAnimation(1, 0);
            mAlphaOut.setDuration(DEFAULT_DURATION);
        }

        if (mDuration != -1) {
            mAlphaIn.setDuration(mDuration);
            mAlphaOut.setDuration(mDuration);
        }

        setOnTouchListener(this);
        setBackgroundColor(mShadowColor);
    }


    @Override
    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
        if (visibility == VISIBLE) {
            this.startAnimation(mAlphaIn);
        } else if (visibility == GONE) {
            this.startAnimation(mAlphaOut);
        }
    }

    public void setOnTouchListener(TouchListener listener) {
        mTouchListener = listener;
    }

    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (mTouchListener != null) {
                    mTouchListener.onTouch();
                }
                break;
        }
        return true;
    }

}
