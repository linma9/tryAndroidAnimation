package com.example.android.tryanimationt;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.CardView;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.Transition;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.cardview.R;

import java.util.HashMap;

public class LogoActivity extends Activity {

    static public String LOGO_VIEW_TRANSTION_TYPE = "int_extra_transition_type";
    static public String LOGO_VIEW_IMAGE_FADEOUT = "LOGO_VIEW_IMAGE_FADEOUT";
    static int activityTransitionType;
    static int imageFadeoutType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(android.view.Window.FEATURE_CONTENT_TRANSITIONS);
        Transition ts; // = new Fade();  //Slide(); //Explode();

        imageFadeoutType = getIntent().getIntExtra(LOGO_VIEW_IMAGE_FADEOUT, 0);
        activityTransitionType = getIntent().getIntExtra(LOGO_VIEW_TRANSTION_TYPE, 0);

        switch (activityTransitionType) {
            case 0:
                ts = new Fade();
                break;
            case 1:
                ts = new Explode();
                break;
            default:
                ts = new Slide();
        }
        //ts.setStartDelay(2000);
        ts.setDuration(800);

        /*
        If you have set an enter transition for the second activity,
        the transition is also activated when the activity starts.
         */

        getWindow().setEnterTransition(ts);
        //getWindow().setExitTransition(ts);

        setContentView(R.layout.activity_logo);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.logo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        View rootView = null;
        ImageView[] logoArr = new ImageView[5];

        HashMap<Integer, Integer> indexToImageResourceId = new HashMap<Integer, Integer>();

        public PlaceholderFragment() {
            // image source
            indexToImageResourceId.put(0, R.drawable.img1);
            indexToImageResourceId.put(1, R.drawable.img2);
            indexToImageResourceId.put(2, R.drawable.img3);
            indexToImageResourceId.put(3, R.drawable.img4);
            indexToImageResourceId.put(4, R.drawable.img5);
            indexToImageResourceId.put(5, R.drawable.img6);
            indexToImageResourceId.put(6, R.drawable.img7);
            indexToImageResourceId.put(7, R.drawable.img8);

            indexToImageResourceId.put(8, R.drawable.img9);
            indexToImageResourceId.put(9, R.drawable.img10);
            indexToImageResourceId.put(10, R.drawable.img11);
            indexToImageResourceId.put(11, R.drawable.img12);
            indexToImageResourceId.put(12, R.drawable.img13);
            indexToImageResourceId.put(13, R.drawable.img14);
            indexToImageResourceId.put(14, R.drawable.img15);

        }

        void fadeinBaseLogo(View rootView) {
            View logoBaseView = (View) rootView.findViewById(R.id.base_image1);
            final ViewPropertyAnimator animator = logoBaseView.animate();
            animator.alpha(1f)
                    .setInterpolator(new AccelerateInterpolator())
                    .setStartDelay(300)
                    .setDuration(1000)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            animator.setListener(null);
                        }
                    });

        }

        void fadeinMessageCard(final View rootView) {
            CardView messageCardView = (CardView) rootView.findViewById(R.id.cardview);
            messageCardView.setScaleX(0);
            messageCardView.setScaleY(0);
            messageCardView.setPivotX(0);
            messageCardView.setPivotY(1);

            final ViewPropertyAnimator animator = messageCardView.animate();
            animator.scaleX(1)
                    .scaleY(1)
                    .translationZBy(10)
                    .alpha(1f)
                    .setInterpolator(new AccelerateInterpolator())
                    .setDuration(1000)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            animator.setListener(null);
                        }
                    });

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            rootView = inflater.inflate(R.layout.fragment_logo, container, false);

            mDetector = new GestureDetectorCompat(getActivity(), new MyGestureListener());

            final View messageCardView = (View) rootView.findViewById(R.id.cardview);
//            messageCardView.setOutlineProvider(new ViewOutlineProvider() {
//                @Override
//                public void getOutline(View view, Outline outline) {
//                    int size = getResources().getDimensionPixelSize(R.dimen.fab_size);
//                    outline.setOval(0, 0, size, size);
//                    outline.setRoundRect(0, 0, size, size, size / 2);
//                }
//            });

            fadeinBaseLogo(rootView);
            fadeinMessageCard(rootView);

            String transitionType;
            switch (activityTransitionType) {
                case 0:
                    transitionType = "used Fade() transition to Logo activity";
                    break;
                case 1:
                    transitionType = "used Explode() transition to Logo activity";
                    break;
                default:
                    transitionType = "used Slide() transition to Logo activity";
            }

            final TextView tv = (TextView) rootView.findViewById(R.id.logoView_textpart);
            tv.setText(transitionType);

            // animate logo images
            logoArr[0] = (ImageView) rootView.findViewById(R.id.image1);
            logoArr[1] = (ImageView) rootView.findViewById(R.id.image2);
            logoArr[2] = (ImageView) rootView.findViewById(R.id.image3);
            logoArr[3] = (ImageView) rootView.findViewById(R.id.image4);
            logoArr[4] = (ImageView) rootView.findViewById(R.id.image5);

            logoArr[0].setAlpha(0f);
            logoArr[1].setAlpha(0f);
            logoArr[2].setAlpha(0f);
            logoArr[3].setAlpha(0f);
            logoArr[4].setAlpha(0f);
            logoArr[0].setVisibility(View.VISIBLE);
            logoArr[1].setVisibility(View.VISIBLE);
            logoArr[2].setVisibility(View.VISIBLE);
            logoArr[3].setVisibility(View.VISIBLE);
            logoArr[4].setVisibility(View.VISIBLE);

            fadeInNextLogo(0, 1200, 1500);

            final ImageView imgv1 = (ImageView) rootView.findViewById(R.id.logoView_image1);
            imgv1.setOnTouchListener(onTouchListener);

            final ImageView imgv2 = (ImageView) rootView.findViewById(R.id.logoView_image2);
            imgv2.setOnTouchListener(onTouchListener);
            return rootView;
        }

        /**
         * fade in one logo by duration
         * and start to fade in next logo at delay
         *
         * @param fadeInIndex
         * @param delay
         * @param duration
         */

        void fadeInNextLogo(final int fadeInIndex, final int delay, final int duration) {

            int fadeOutIndex = ((fadeInIndex - 1) >= 0) ? (fadeInIndex - 1) : 4;
            final ImageView fadInLogo = logoArr[fadeInIndex];
            final ImageView fadOutLogo = logoArr[fadeOutIndex];

            final int nextFadeInIndex = (fadeInIndex + 1) % 5;

            final ViewPropertyAnimator animator = fadInLogo.animate();
            animator.setUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    long playedTime = animation.getCurrentPlayTime();
                    if (playedTime >= delay) {
                        animator.setUpdateListener(null);
                        fadeInNextLogo(nextFadeInIndex, delay, duration);

                    }
                }
            });

            animator.alpha(1f)
                    .setDuration(duration)
                    .setListener(new AnimatorListenerAdapter() {// alternative .withEndAction(new Runnable() {}, which keeps view not to be recycled, YouTube: https://www.youtube.com/watch?v=8MIfSxgsHIs
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            animator.setListener(null);
                            if (fadOutLogo != null) {
                                fadOutLogo.setAlpha(0f);
                            }
                        }
                    });
        }

        boolean inTransition = false;

        /**
         *
         * @param v
         * @param showDetail true to tansition to large image, false to swap in next image
         * @param animFromLeft2right
         */
        void imageClickHandler(final View v, boolean showDetail, boolean animFromLeft2right) {

            if (!inTransition) {

                inTransition = true;

                ViewGroup vgr = (ViewGroup) rootView.findViewById(R.id.cardContentContainer);
                int selected = vgr.indexOfChild(v);
                final View fadeOutImg = v;

                int fadInImgIndex = ((selected + 1) % (vgr.getChildCount()));
                final ImageView fadeInImg = (ImageView) vgr.getChildAt(fadInImgIndex);

                final String sImgIndex = (String) v.getTag();

                // open image details view
                if (showDetail) {
                    inTransition = false;
                    int heroId = indexToImageResourceId.get(Integer.parseInt(sImgIndex));
                    showLargeImage(v, heroId);
                    return;
                }

                int temp = Integer.parseInt(sImgIndex);
                int imgIndex = (temp + 1) % (indexToImageResourceId.size());
                if (!animFromLeft2right) {
                    imgIndex = ((temp - 1) >= 0) ? (temp - 1) : (indexToImageResourceId.size() - 1);
                }

                int fadInImageId = indexToImageResourceId.get(imgIndex);

                // set new image
                fadeInImg.setImageDrawable(getResources().getDrawable(fadInImageId));
                fadeInImg.setTag("" + imgIndex);

                final AnimatorSet animSetFadeOut = new AnimatorSet();

                // restore fadeIn image scale
                fadeInImg.setVisibility(View.VISIBLE);
                fadeInImg.setScaleX(1);
                fadeInImg.setScaleY(1);

                float fadeinImeEleZ = fadeInImg.getElevation();
                float fadeoutImeEleZ = fadeOutImg.getElevation();

                ObjectAnimator animFadeInAlpha = ObjectAnimator.ofFloat(fadeInImg, "alpha", 1f);
                animFadeInAlpha.setDuration(1500);

                // animate fadeOut image
                ObjectAnimator animFadeOutAlpha = ObjectAnimator.ofFloat(fadeOutImg, "alpha", 0f);
                animFadeOutAlpha.setDuration(1200);

                if (imageFadeoutType != 1) {

                    fadeInImg.setTranslationX(animFromLeft2right ? -200f : 200);
                    fadeInImg.setTranslationY(-200f);

                    ObjectAnimator animFadeInElevationZ = ObjectAnimator.ofFloat(fadeInImg, "elevation", fadeinImeEleZ, 200);
                    animFadeInElevationZ.setDuration(50);

                    ObjectAnimator animFadeInTrX = ObjectAnimator.ofFloat(fadeInImg, "translationX", animFromLeft2right ? -200f : 200f, 0f);
                    animFadeInTrX.setDuration(500);

                    ObjectAnimator animFadeInTrY = ObjectAnimator.ofFloat(fadeInImg, "translationY", -200f, 0f);
                    animFadeInTrY.setDuration(500);

                    ///

                    ObjectAnimator animFadeOutElevationZ = ObjectAnimator.ofFloat(fadeOutImg, "elevation", fadeoutImeEleZ, 30);
                    animFadeOutElevationZ.setDuration(50);

                    ObjectAnimator animFadeOutScaleX = ObjectAnimator.ofFloat(fadeOutImg, "scaleX", 1f, 0.66f);
                    animFadeOutScaleX.setDuration(800);

                    ObjectAnimator animFadeOutScaleY = ObjectAnimator.ofFloat(fadeOutImg, "scaleY", 1f, 0.88f);
                    animFadeOutScaleY.setDuration(800);

                    ObjectAnimator animFadeOutTrX = ObjectAnimator.ofFloat(fadeOutImg, "translationX", 0f, animFromLeft2right ? 200f : -200f);
                    animFadeOutTrX.setDuration(800);

                    ObjectAnimator animFadeOutTrY = ObjectAnimator.ofFloat(fadeOutImg, "translationY", 0f, -200f);
                    animFadeOutTrY.setDuration(800);

                    animSetFadeOut.playTogether(animFadeInAlpha, animFadeInTrX, animFadeInTrY, animFadeInElevationZ,
                            animFadeOutScaleX, animFadeOutScaleY, animFadeOutTrX, animFadeOutTrY, animFadeOutAlpha, animFadeOutElevationZ);


                } else {
                    fadeInImg.setTranslationX(0f);
                    fadeInImg.setTranslationY(0f);

                    // get the center for the clipping circle
                    int cx = (fadeOutImg.getLeft() + fadeOutImg.getRight()) / 2;
                    int cy = (fadeOutImg.getTop() + fadeOutImg.getBottom()) / 2;

                    // get the initial radius for the clipping circle
                    int initialRadius = fadeOutImg.getWidth();

                    // create the animation (the final radius is zero)
                    Animator animCircleFadeOut =
                            ViewAnimationUtils.createCircularReveal(v, cx, cy, initialRadius, 0);
                    animCircleFadeOut.setDuration(1500);
                    animCircleFadeOut.setInterpolator(new BounceInterpolator());

                    ObjectAnimator animFadeOutTrX = ObjectAnimator.ofFloat(fadeOutImg, "translationX", 0f, animFromLeft2right ? 200f : -300f);
                    animFadeOutTrX.setDuration(1500);

                    ObjectAnimator animFadeOutTrY = ObjectAnimator.ofFloat(fadeOutImg, "translationY", 0f, -200f);
                    animFadeOutTrY.setDuration(1500);

                    animSetFadeOut.playTogether(animFadeInAlpha, animCircleFadeOut, animFadeOutTrX, animFadeOutTrY, animFadeOutAlpha);
                }

                mListner = new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {

                        fadeOutImg.setVisibility(View.GONE);
                        animSetFadeOut.removeListener(mListner);
                        inTransition = false;
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                };
                animSetFadeOut.addListener(mListner);
                //animSetFadeOut.setInterpolator(new BounceInterpolator());
                animSetFadeOut.start();

            }
        }

        void showLargeImage(final View v, int imgId) {

            ActivityOptionsCompat options =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(
                            getActivity(), v, "photo_hero");

            Intent intent = new Intent(getActivity(), sceneTransitionActivity.class);
            intent.putExtra("photo_hero", imgId);
            ActivityCompat.startActivity(getActivity(), intent, options.toBundle());
        }

        Animator.AnimatorListener mListner = null;
        private GestureDetectorCompat mDetector;

        View mLastTouchedImage = null;
        private final View.OnTouchListener onTouchListener = new View.OnTouchListener() {

            @Override
            public boolean onTouch(final View v, MotionEvent event) {

                mLastTouchedImage = v;

                boolean handled = mDetector.onTouchEvent(event);
                return handled;
            }

        };


        class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
            private static final String DEBUG_TAG = "Gestures";

            @Override
            public boolean onDoubleTap(MotionEvent e) {
                Log.d(DEBUG_TAG, "onDoubleTap, mLastTouchedImage:" + mLastTouchedImage);
                if (mLastTouchedImage != null) {
                    imageClickHandler(mLastTouchedImage, true, true);
                    return true;
                }
                return super.onDoubleTap(e);
            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                Log.d(DEBUG_TAG, "onSingleTapUp: ");
                return super.onSingleTapUp(e);
            }

            @Override
            public boolean onDown(MotionEvent event) {
                Log.d(DEBUG_TAG, "onDown: ");
                return true;
            }

            @Override
            public boolean onFling(MotionEvent event1, MotionEvent event2,
                                   float velocityX, float velocityY) {
                //Log.d(DEBUG_TAG, "onFling: " + event1.toString()+event2.toString());

                boolean left2right = true;
                if (event1.getX() - event2.getX() > 60
                        && Math.abs(velocityX) > 200) {
                    left2right = false;
                }

                Log.d(DEBUG_TAG, "onFling, event1.getX():" + event1.getX() + ", event2.getX():" + event2.getX() +
                        ", Math.abs(velocityX) :" + Math.abs(velocityX) + ", left2right:" + left2right);


                if (mLastTouchedImage != null) {
                    imageClickHandler(mLastTouchedImage, false, left2right);
                    return true;
                }
                return false;
            }
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAfterTransition();
    }

    @Override
    protected void onDestroy() {

        Log.e("+++ logo activity, onDestroy():", "++++++++++++++");

        super.onDestroy();
    }
}
