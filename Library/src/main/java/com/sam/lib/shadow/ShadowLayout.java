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
import android.widget.FrameLayout;

public class ShadowLayout extends FrameLayout implements OnTouchListener {


    private int mAnimationInId, mAnimationOutId;
    private View mShadowView;
    private Animation mAlphaIn;
    private Animation mAlphaOut;
    private Animation mAlphaNone;

    private static final int DEFAULT_COLOR = Color.parseColor("#55000000");
    private static final int DEFAULT_DURATION = 500;
    private int mShadowColor;
    private int mDuration;

    public interface TouchListener {
        void onTouch();
    }

    private TouchListener mTouchListener;

    public ShadowLayout(Context context) {
        this(context, null);
    }

    public ShadowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShadowLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ShadowLayout);
        mShadowColor = ta.getColor(R.styleable.ShadowLayout_slShadowColor, DEFAULT_COLOR);
        mDuration = ta.getInteger(R.styleable.ShadowLayout_android_duration, -1);

        mAnimationInId = ta.getResourceId(R.styleable.ShadowLayout_android_inAnimation, -1);
        mAnimationOutId = ta.getResourceId(R.styleable.ShadowLayout_android_outAnimation, -1);

        ta.recycle();


        initData();
    }


    private void initData() {
        setOnTouchListener(this);
        mShadowView = new View(getContext());
        mShadowView.setBackgroundColor(mShadowColor);
        addView(mShadowView, 0);

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

        mAlphaNone = new AlphaAnimation(1, 1);
        mAlphaNone.setDuration(DEFAULT_DURATION);

        if (mDuration != -1) {
            mAlphaIn.setDuration(mDuration);
            mAlphaOut.setDuration(mDuration);
            mAlphaNone.setDuration(mDuration);
        }
    }

    public void setDuration(int duration) {
        this.mDuration = duration;
        mAlphaIn.setDuration(mDuration);
        mAlphaOut.setDuration(mDuration);
        mAlphaNone.setDuration(mDuration);
    }

    public void setShadowColor(int shadowColor) {
        this.mShadowColor = shadowColor;
        mShadowView.setBackgroundColor(mShadowColor);
    }

    public void setShadowColorResourceId(int resourceId) {
        this.mShadowColor = getContext().getResources().getColor(resourceId);
        mShadowView.setBackgroundColor(mShadowColor);
    }

    @Override
    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
        mShadowView.setVisibility(visibility);
        if (visibility == VISIBLE) {
            mShadowView.startAnimation(mAlphaIn);
            startAnimation(mAlphaNone);
        } else if (visibility == GONE) {
            mShadowView.startAnimation(mAlphaOut);
            startAnimation(mAlphaNone);
        }
    }

    public void setOnTouchListener(TouchListener listener) {
        mTouchListener = listener;
    }

    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (mTouchListener != null) {
                mTouchListener.onTouch();
            }
        }
        return true;
    }

}
