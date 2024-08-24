package com.example.flippingcardlibrary;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

public class FlippingCardView extends ConstraintLayout {

    private CardView frontView;
    private CardView backView;
    private ImageView frontImageView;
    private ImageView backImageView;
    private boolean isFlipped = false;
    private int flipDuration = 150; // Default flip duration

    public static final int FLIP_STYLE_ROTATION = 0;
    public static final int FLIP_STYLE_FADE = 1;
    public static final int FLIP_STYLE_ZOOM_IN_FLIP = 2;

    public static final int FLIP_STYLE_SLIDE_RIGHT = 3;
    public static final int FLIP_STYLE_SLIDE_LEFT = 4;
    public static final int FLIP_STYLE_PENDULUM_SLIDE = 5;

    private boolean slideRight = true; // To track slide direction

    private int flipStyle = FLIP_STYLE_ROTATION;

    public FlippingCardView(Context context) {
        super(context);
        init(context, null);
    }

    public FlippingCardView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public FlippingCardView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        frontView = (CardView) inflater.inflate(R.layout.front_card, this, false);
        backView = (CardView) inflater.inflate(R.layout.back_card, this, false);

        frontImageView = frontView.findViewById(R.id.front_image);
        backImageView = backView.findViewById(R.id.back_image);

        addView(frontView);
        addView(backView);

        backView.setVisibility(GONE);


        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.FlippingCardView);

            float cardRadius = a.getDimension(R.styleable.FlippingCardView_cardRadius, 0);
            flipDuration = a.getInt(R.styleable.FlippingCardView_flipDuration, 150);
            flipStyle = a.getInt(R.styleable.FlippingCardView_flipStyle, FLIP_STYLE_ROTATION);

            frontView.setRadius(cardRadius);
            backView.setRadius(cardRadius);

            int frontImageResId = a.getResourceId(R.styleable.FlippingCardView_frontImage, -1);
            int backImageResId = a.getResourceId(R.styleable.FlippingCardView_backImage, -1);
            frontImageView.setImageResource(frontImageResId != -1 ? frontImageResId : android.R.color.transparent);
            backImageView.setImageResource(backImageResId != -1 ? backImageResId : android.R.color.transparent);

            a.recycle();
        }
    }

    public void setFlipDuration(int duration) {
        this.flipDuration = duration;
    }

    public void flipCard() {
        switch (flipStyle) {
            case FLIP_STYLE_ROTATION:
                performFlipAnimation(isFlipped ? backView : frontView, isFlipped ? frontView : backView);
                break;
            case FLIP_STYLE_FADE:
                performFadeAnimation(isFlipped ? backView : frontView, isFlipped ? frontView : backView);
                break;
            case FLIP_STYLE_ZOOM_IN_FLIP:
                performZoomInFlipAnimation(isFlipped ? backView : frontView, isFlipped ? frontView : backView);
                break;
            case FLIP_STYLE_SLIDE_RIGHT:
                performSlideFromRightAnimation(isFlipped ? backView : frontView, isFlipped ? frontView : backView);
                break;
            case FLIP_STYLE_SLIDE_LEFT:
                performSlideFromLeftAnimation(isFlipped ? backView : frontView, isFlipped ? frontView : backView);
                break;
            case FLIP_STYLE_PENDULUM_SLIDE:
                performPendulumSlideAnimation(isFlipped ? backView : frontView, isFlipped ? frontView : backView);
                break;
            default:
                // Handle other cases or provide a default behavior
                break;
        }
        isFlipped = !isFlipped;
    }

    public void setFlipStyle(int style) {
        this.flipStyle = style;
    }

    private void performFlipAnimation(final CardView fromView, final CardView toView) {
        fromView.animate().rotationY(90).setDuration(flipDuration).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                fromView.setVisibility(GONE);
                toView.setVisibility(VISIBLE);
                toView.setRotationY(-90);
                toView.animate().rotationY(0).setDuration(flipDuration).setListener(null);
            }
        });
    }

    private void performFadeAnimation(final CardView fromView, final CardView toView) {
        // Prepare the toView before animation starts
        toView.setAlpha(0f);
        toView.setVisibility(VISIBLE);

        // Fade out animation for fromView
        ObjectAnimator fadeOutFrom = ObjectAnimator.ofFloat(fromView, "alpha", 1f, 0f);
        fadeOutFrom.setDuration(flipDuration );

        // Fade in animation for toView
        ObjectAnimator fadeInTo = ObjectAnimator.ofFloat(toView, "alpha", 0f, 1f);
        fadeInTo.setDuration(flipDuration );

        final AnimatorSet animationSet = new AnimatorSet();

        animationSet.play(fadeInTo).after(fadeOutFrom);

        animationSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                // Ensure both views are visible at the start of the animation
                fromView.setVisibility(VISIBLE);
                toView.setVisibility(VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                // Hide the fromView when the animation is complete
                fromView.setVisibility(GONE);
            }
        });

        animationSet.start();
    }

    private void performZoomInFlipAnimation(final CardView fromView, final CardView toView) {
        // Create AnimationSet for zoom out
        AnimationSet zoomOutSet = new AnimationSet(true);
        ScaleAnimation zoomOut = new ScaleAnimation(1f, 0.5f, 1f, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        AlphaAnimation fadeOut = new AlphaAnimation(1f, 0f);

        zoomOut.setDuration(flipDuration / 2);
        fadeOut.setDuration(flipDuration / 2);

        zoomOutSet.addAnimation(zoomOut);
        zoomOutSet.addAnimation(fadeOut);

        // Create AnimationSet for zoom in
        AnimationSet zoomInSet = new AnimationSet(true);
        ScaleAnimation zoomIn = new ScaleAnimation(0.5f, 1f, 0.5f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        AlphaAnimation fadeIn = new AlphaAnimation(0f, 1f);

        zoomIn.setDuration(flipDuration / 2);
        fadeIn.setDuration(flipDuration / 2);

        zoomInSet.addAnimation(zoomIn);
        zoomInSet.addAnimation(fadeIn);

        // Set up the animation listener
        zoomOutSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                // No action needed here
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                fromView.setVisibility(GONE); // Hide the fromView after zoom out
                toView.setVisibility(VISIBLE); // Show the toView

                // Start zoom in animation on the toView
                toView.startAnimation(zoomInSet);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // No action needed here
            }
        });

        // Start zoom out animation on the fromView
        fromView.startAnimation(zoomOutSet);
    }

    private void performSlideFromRightAnimation(final CardView fromView, final CardView toView) {
        AnimatorSet animatorSet = new AnimatorSet();

        // Slide out the current view to the left
        ObjectAnimator slideOutFrom = ObjectAnimator.ofFloat(fromView, "translationX", 0f, -getWidth());

        // Prepare the new view
        toView.setVisibility(VISIBLE);
        toView.setTranslationX(getWidth());

        // Slide in the new view from the right
        ObjectAnimator slideInTo = ObjectAnimator.ofFloat(toView, "translationX", getWidth(), 0f);

        // Set up the animation sequence
        animatorSet.play(slideOutFrom).with(slideInTo);

        // Set the duration
        animatorSet.setDuration(flipDuration);

        // Add a listener to handle visibility
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                fromView.setVisibility(GONE);
                fromView.setTranslationX(0f);
            }
        });

        // Start the animation
        animatorSet.start();
    }

    private void performSlideFromLeftAnimation(final CardView fromView, final CardView toView) {
        AnimatorSet animatorSet = new AnimatorSet();

        // Slide out the current view to the right
        ObjectAnimator slideOutFrom = ObjectAnimator.ofFloat(fromView, "translationX", 0f, getWidth());

        // Prepare the new view
        toView.setVisibility(VISIBLE);
        toView.setTranslationX(-getWidth());

        // Slide in the new view from the left
        ObjectAnimator slideInTo = ObjectAnimator.ofFloat(toView, "translationX", -getWidth(), 0f);

        // Set up the animation sequence
        animatorSet.play(slideOutFrom).with(slideInTo);

        // Set the duration
        animatorSet.setDuration(flipDuration);

        // Add a listener to handle visibility
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                fromView.setVisibility(GONE);
                fromView.setTranslationX(0f);
            }
        });

        // Start the animation
        animatorSet.start();
    }

    private void performPendulumSlideAnimation(final CardView fromView, final CardView toView) {
        AnimatorSet animatorSet = new AnimatorSet();

        float slideDistance = getWidth();

        // Determine slide directions based on the slideRight flag
        float fromViewStart = 0f;
        float fromViewEnd = slideRight ? -slideDistance : slideDistance;
        float toViewStart = slideRight ? slideDistance : -slideDistance;
        float toViewEnd = 0f;

        // Slide out the current view
        ObjectAnimator slideOutFrom = ObjectAnimator.ofFloat(fromView, "translationX", fromViewStart, fromViewEnd);

        // Prepare the new view
        toView.setVisibility(VISIBLE);
        toView.setTranslationX(toViewStart);

        // Slide in the new view
        ObjectAnimator slideInTo = ObjectAnimator.ofFloat(toView, "translationX", toViewStart, toViewEnd);

        // Set up the animation sequence
        animatorSet.play(slideOutFrom).with(slideInTo);

        // Set the duration
        animatorSet.setDuration(flipDuration);

        // Add a listener to handle visibility and reset positions
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                fromView.setVisibility(GONE);
                fromView.setTranslationX(0f);
                toView.setTranslationX(0f);

                // Toggle the slide direction for next time
                slideRight = !slideRight;
            }
        });

        // Start the animation
        animatorSet.start();
    }


}