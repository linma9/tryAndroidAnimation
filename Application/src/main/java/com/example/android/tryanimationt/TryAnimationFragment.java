/*
* Copyright 2014 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package com.example.android.tryanimationt;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.ActivityOptions;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.view.animation.PathInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.CheckBox;
import android.widget.ImageButton;

import android.graphics.Outline;
import android.widget.RadioGroup;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.example.android.cardview.R;

/**
 * Fragment that demonstrates how to use CardView.
 */
public class TryAnimationFragment extends Fragment {

    private static final String TAG = TryAnimationFragment.class.getSimpleName();
    CardView mCardView;

    public static TryAnimationFragment newInstance() {
        TryAnimationFragment fragment = new TryAnimationFragment();
        fragment.setRetainInstance(true);
        return fragment;
    }

    public TryAnimationFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main_view, container, false);
    }

    int mAnimationStateIndex = 1;
    android.widget.ImageButton fab1st;
    android.widget.ImageButton fab2nd;
    android.widget.ImageButton fab3rd;
    View footer;

    void animButtons(ImageButton bt, boolean in, int animTime, int delay) {

        //delay = 0;

        float rotateStart, rotateEnd;
        float scaleXStart, scaleXEnd;
        float scaleYStart, scaleYEnd;
        float transitionXStart, transitionXEnd;
        float transitionYStart, transitionYEnd;

        if (in) {
            rotateStart = 0;
            rotateEnd = 359;
            scaleXStart = 0.66f;
            scaleXEnd = 1;
            scaleYStart = 0.66f;
            scaleYEnd = 1;
            transitionXStart = 60;
            transitionXEnd = 0;
            transitionYStart = 10;
            transitionYEnd = 0;

        } else {
            rotateStart = 359;
            rotateEnd = 0;
            scaleXStart = 1;
            scaleXEnd = 0.66f;
            scaleYStart = 1;
            scaleYEnd = 0.66f;
            transitionXStart = 0;
            transitionXEnd = 60;
            transitionYStart = 0;
            transitionYEnd = 10;
        }
        bt.setTranslationX(transitionXStart);
        bt.setTranslationY(transitionYStart);

        AnimatorSet animSet = new AnimatorSet();
        ObjectAnimator animRotate = ObjectAnimator.ofFloat(bt, "rotation", rotateStart, rotateEnd);
        animRotate.setDuration(animTime);

        ObjectAnimator animScaleX = ObjectAnimator.ofFloat(bt, "scaleX", scaleXStart, scaleXEnd);
        animScaleX.setDuration(animTime);
        animScaleX.setStartDelay(Math.round(delay * 0.66));
        ObjectAnimator animScaleY = ObjectAnimator.ofFloat(bt, "scaleY", scaleYStart, scaleYEnd);
        animScaleY.setDuration(animTime);
        animScaleY.setStartDelay(Math.round(delay*0.66));

        ObjectAnimator animTrx = ObjectAnimator.ofFloat(bt, "translationX", transitionXStart, transitionXEnd);
        animTrx.setDuration(animTime);
        animTrx.setStartDelay(delay);
        ObjectAnimator animTry = ObjectAnimator.ofFloat(bt, "translationY", transitionYStart, transitionYEnd);
        animTry.setDuration(animTime);
        animTry.setStartDelay(delay);

        animSet.setInterpolator(new BounceInterpolator());
        animSet.playTogether(animRotate, animScaleX, animScaleY, animTrx, animTry);

        animSet.start();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mCardView = (CardView) view.findViewById(R.id.cardview);

        fab1st = (android.widget.ImageButton) view.findViewById(R.id.fabBt);
        fab2nd = (android.widget.ImageButton) view.findViewById(R.id.fabBt2);
        fab3rd = (android.widget.ImageButton) view.findViewById(R.id.fab3);

        footer = view.findViewById(R.id.footer);

        animButtons(fab1st, true, 2500, 0);
        animButtons(fab2nd, true, 1000, 150);
        animButtons(fab3rd, true, 2000, 250);

        fab1st.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimatorSet animSet = new AnimatorSet();
                ObjectAnimator anim2 = ObjectAnimator.ofFloat(fab1st, "rotationX", 0, 359);
                anim2.setDuration(1500);

                ObjectAnimator anim3 = ObjectAnimator.ofFloat(fab1st, "rotationY", 0, 359);
                anim3.setDuration(1500);

                ObjectAnimator animTrx = ObjectAnimator.ofFloat(fab1st, "translationX", 0, -20);
                animTrx.setDuration(2500);
                ObjectAnimator animTry = ObjectAnimator.ofFloat(fab1st, "translationY", 0, -20);
                animTry.setDuration(2500);

                animSet.setInterpolator(new BounceInterpolator());
                animSet.playTogether(anim2, anim3, animTry, animTrx);

                animSet.start();

            }
        });

        fab1st.setOutlineProvider(new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                int size = getResources().getDimensionPixelSize(R.dimen.fab_size);
                outline.setOval(0, 0, size, size);
                outline.setRoundRect(0, 0, size, size, size / 2);
            }
        });

        fab1st.setClipToOutline(true);

        final View vImage = view.findViewById(R.id.image);
        final View vCard = view.findViewById(R.id.cardview);
        final View vCardTextPart = view.findViewById(R.id.cardview_textpart2);
        final View vCardContentContainer = view.findViewById(R.id.cardContentContainer);

        vCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        getActivity(),

                        // Now we provide a list of Pair items which contain the view we can transitioning
                        // from, and the name of the view it is transitioning to, in the launched activity

                        android.support.v4.util.Pair.create(vImage, "photo_hero"),
                        android.support.v4.util.Pair.create(vCardTextPart, "sharedSceneTrasintionText")
                );

                Intent intent = new Intent(getActivity(), sceneTransitionActivity.class);
                intent.putExtra("photo_hero", R.drawable.image1);
                ActivityCompat.startActivity(getActivity(), intent, options.toBundle());
            }
        });

        fab2nd.setOutlineProvider(new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                int size = getResources().getDimensionPixelSize(R.dimen.fab_size);
                outline.setOval(0, 0, size, size);
                outline.setRoundRect(0, 0, size, size, size / 2);
            }
        });

        fab2nd.setClipToOutline(true);

        final AnimationDrawable[] animDrawables = new AnimationDrawable[2];
        animDrawables[0] = (AnimationDrawable) getResources().getDrawable(R.drawable.anim_off_to_on);
        animDrawables[1] = (AnimationDrawable) getResources().getDrawable(R.drawable.anim_on_to_off);
        animDrawables[0].setOneShot(true);
        animDrawables[1].setOneShot(true);


        fab2nd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final int fab2InconIndex = mAnimationStateIndex;
                mAnimationStateIndex = (mAnimationStateIndex + 1) % 2;

                /*****************************************************/
                // animate the card

                //final Animation myRotation = AnimationUtils.loadAnimation(getActivity(), R.anim.rotate_anim);
                //mCardView.startAnimation(myRotation);

                int start;
                int end;
                if (mAnimationStateIndex == 0) {
                    start = Color.rgb(0x71, 0xc3, 0xde);
                    end = Color.rgb(0x68, 0xe8, 0xee);
                } else {
                    start = Color.rgb(0x68, 0xe8, 0xee);
                    end = Color.rgb(0x71, 0xc3, 0xde);
                }

                AnimatorSet animSet = new AnimatorSet();

                ValueAnimator valueAnimator = ObjectAnimator.ofInt(vCardContentContainer, "backgroundColor", start, end);
                valueAnimator.setInterpolator(new BounceInterpolator());
                valueAnimator.setDuration(2000);
                valueAnimator.setEvaluator(new ArgbEvaluator());

                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        int animProgress = (Integer) animation.getAnimatedValue();

                    }
                });
                valueAnimator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mCardView.setRadius(8);
                        //mCardView.setElevation(0);
                    }
                });

                float rotateStart, rotateEnd;
                float scaleXStart, scaleXEnd;
                float rotateXStart, rotateXEnd;
                float rotateYStart, rotateYEnd;
                float transitionXStart, transitionXEnd;
                float transitionYStart, transitionYEnd;

                if (mAnimationStateIndex == 0) {
                    rotateStart = 0f;
                    rotateEnd = 80f;
                    scaleXStart = 1f;
                    scaleXEnd = 0.66f;
                    rotateXStart = 0f;
                    rotateXEnd = 30f;
                    rotateYStart = 0f;
                    rotateYEnd = 30f;
                    transitionYStart = 0f;
                    transitionYEnd = -100f;
                    transitionXStart = 0f;
                    transitionXEnd = 100f;
                } else {
                    rotateStart = 80f;
                    rotateEnd = 0f;
                    scaleXStart = 0.66f;
                    scaleXEnd = 1;
                    rotateXStart = 30;
                    rotateXEnd = 0f;
                    rotateYStart = 30f;
                    rotateYEnd = 0f;
                    transitionYStart = -100f;
                    transitionYEnd = 0f;
                    transitionXStart = 100f;
                    transitionXEnd = 0f;
                }

                ObjectAnimator anim = ObjectAnimator.ofFloat(mCardView, "rotation", rotateStart, rotateEnd);
                anim.setDuration(2000);

                ObjectAnimator anim1 = ObjectAnimator.ofFloat(mCardView, "scaleX", scaleXStart, scaleXEnd);
                anim1.setDuration(2000);

                ObjectAnimator anim2 = ObjectAnimator.ofFloat(mCardView, "rotationX", rotateXStart, rotateXEnd);
                anim2.setDuration(2000);

                ObjectAnimator anim3 = ObjectAnimator.ofFloat(mCardView, "rotationY", rotateYStart, rotateYEnd);
                anim3.setDuration(2000);

                ObjectAnimator animTry = ObjectAnimator.ofFloat(mCardView, "translationY", transitionYStart, transitionYEnd);
                animTry.setDuration(2000);
                ObjectAnimator animTrx = ObjectAnimator.ofFloat(mCardView, "translationX", transitionXStart, transitionXEnd);
                animTrx.setDuration(2000);
                animSet.setInterpolator(new BounceInterpolator());
                animSet.playTogether(valueAnimator, anim, anim2, anim3, anim1, animTry, animTrx);

                float controlX1, controlY1, controlX2, controlY2;
                if (mAnimationStateIndex == 0) {
                    controlX1 = 0f;
                    controlY1 = 0.25f;
                    controlX2 = 1;
                    controlY2 = 1;
                } else {
                    controlX1 = 1;
                    controlY1 = 1;
                    controlX2 = 0.25f;
                    controlY2 = 1;
                }

                PathInterpolator pathInterpolator = new PathInterpolator(controlX1, controlY1, controlX2, controlY2);
                animTrx.setInterpolator(pathInterpolator);

                animSet.start();

                /*****************************************************/
                // animate rotate white button

                RotateAnimation r = new RotateAnimation(0, 359, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                r.setDuration(2000);
                r.setFillAfter(true);

                r.setInterpolator(new BounceInterpolator());

                fab2nd.startAnimation(r);

                // change 2nd button image
                fab2nd.setImageDrawable(animDrawables[fab2InconIndex]);
                animDrawables[fab2InconIndex].start();

                /*****************************************************/
                // animate changing 3rd button image
                fab3rd.setImageDrawable(animDrawables[mAnimationStateIndex]);
                animDrawables[mAnimationStateIndex].start();

                /*****************************************************/
                // using AnimatedStateListDrawable to animate the 1st button image by its state
                {
                    Drawable drawable = getActivity().getResources().getDrawable(R.drawable.icon_anim);
                    fab1st.setImageDrawable(drawable);

                    final int[] STATE_CHECKED = new int[]{android.R.attr.state_checked};
                    final int[] STATE_UNCHECKED = new int[]{};

                    // set state
                    fab1st.setImageState((mAnimationStateIndex!=0) ? STATE_UNCHECKED : STATE_CHECKED, false);
                    drawable.jumpToCurrentState();
                    // change to state
                    fab1st.setImageState((mAnimationStateIndex!=0) ? STATE_CHECKED : STATE_UNCHECKED, false);
                }

            }
        });

        fab3rd.setOutlineProvider(new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                int size = getResources().getDimensionPixelSize(R.dimen.fab_size);
                outline.setOval(0, 0, size, size);
                outline.setRoundRect(0, 0, size, size, size / 2);
            }
        });

        fab3rd.setClipToOutline(true);
        final CheckBox circleFadeout = (CheckBox) view.findViewById(R.id.circleFadeout);

        circleFadeout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {
                }
            }
        });

        final ImageButton vLogoBt = fab3rd;
        vLogoBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                animButtons(fab1st, false, 2000, 0);
                animButtons(fab2nd, false, 600, 150);
                animButtons(fab3rd, false, 1500, 250);

                Handler delayHandler = new Handler();
                delayHandler.postDelayed(new Runnable() {

                    @Override
                    public void run() {

                        Intent logoIntent = new Intent(getActivity(), LogoActivity.class);

                        logoIntent.putExtra(LogoActivity.LOGO_VIEW_IMAGE_FADEOUT, (circleFadeout.isChecked() ? 1 : 0));
                        logoIntent.putExtra(LogoActivity.LOGO_VIEW_TRANSTION_TYPE, logoActivityTransitionType);

                        startActivityForResult(logoIntent, mRequestCode,
                                ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());

                    }
                }, 1000);

                // footer slide down
                slideView(footer, false);
            }
        });

        mRadioGrp = (RadioGroup)view.findViewById(R.id.radioGroup);
        mRadioGrp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                int selectedId = mRadioGrp.getCheckedRadioButtonId();

                String transitionType = "using";
                switch (selectedId) {
                    case R.id.radioFade:
                        logoActivityTransitionType = 0;
                        transitionType = transitionType + " Fade";
                        break;
                    case R.id.radioExplode:
                        logoActivityTransitionType = 1;
                        transitionType = transitionType + " Explode";
                        break;
                    default:
                        logoActivityTransitionType = 2;
                        transitionType = transitionType + " Slide";
                }
                mSwitcher.setText(transitionType + " transition");
            }
        });

        mSwitcher = (TextSwitcher) view.findViewById(R.id.textSwitcher);
        mSwitcher.setFactory(mFactory);

        Animation in = AnimationUtils.loadAnimation(getActivity(),R.anim.slide_in_top);
        Animation out = AnimationUtils.loadAnimation(getActivity(),R.anim.slide_out_top);
        mSwitcher.setInAnimation(in);
        mSwitcher.setOutAnimation(out);
        mSwitcher.setCurrentText("using Fade transition");

        // footer slide up
        slideView(footer, true);
    }

    void slideView(View view, boolean up) {
        int animResourceId = up ? R.anim.slide_up : R.anim.slide_down;
        Animation slide = AnimationUtils.loadAnimation(getActivity(), animResourceId);
        slide.setFillAfter(true);
        view.startAnimation(slide);
    }

    private ViewSwitcher.ViewFactory mFactory = new ViewSwitcher.ViewFactory() {

        @Override
        public View makeView() {

            // Create a new TextView
            TextView t = new TextView(getActivity());
            t.setGravity(Gravity.LEFT | Gravity.CENTER_HORIZONTAL);
            t.setTextAppearance(getActivity(), android.R.style.TextAppearance);
            return t;
        }
    };

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        Log.e("+++&&& onActivityResult()", "requestCode:"+requestCode);

        if (requestCode == mRequestCode) {
            animButtons(fab1st, true, 3000, 0);
            animButtons(fab2nd, true, 1000, 250);
            animButtons(fab3rd, true, 2000, 500);

            // footer slide up
            Animation slideUp = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_up);
            footer.startAnimation(slideUp);
        }
    }

    RadioGroup mRadioGrp;
    TextSwitcher mSwitcher;
    int mRequestCode = 100;
    int logoActivityTransitionType = 0;
}
