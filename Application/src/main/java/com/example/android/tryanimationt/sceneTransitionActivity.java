package com.example.android.tryanimationt;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.Fragment;
import android.graphics.Outline;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.widget.ImageView;

import com.example.android.cardview.R;

public class sceneTransitionActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_scene_transition);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.scene_transition, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            final View rootView = inflater.inflate(R.layout.fragment_scene_transition, container, false);
            final View containerView = (View) rootView.findViewById(R.id.sceneTransition_rootview);
            final View imageCardView = (View) rootView.findViewById(R.id.sceneTransition_cardview_full);
            ImageView image = (ImageView) rootView.findViewById(R.id.image);
            final int rid = getActivity().getIntent().getIntExtra("photo_hero", R.drawable.image1);

            if (rid != R.drawable.image1) {// from LogoView

                containerView.setBackgroundColor(0xffffff);

                image = (ImageView) rootView.findViewById(R.id.full_image);

                final View txtCardView = (View) rootView.findViewById(R.id.sceneTransition_cardview);
                txtCardView.setVisibility(View.GONE);

                final View imgCardView = (View) rootView.findViewById(R.id.sceneTransition_cardview_image);
                imgCardView.setVisibility(View.GONE);

                final View fabBlue = (View) rootView.findViewById(R.id.fab_blue);
                fabBlue.setVisibility(View.VISIBLE);
                fabBlue.setOutlineProvider(new ViewOutlineProvider() {
                    @Override
                    public void getOutline(View view, Outline outline) {
                        int size = getResources().getDimensionPixelSize(R.dimen.fab_size);
                        outline.setOval(0, 0, size, size);
                        outline.setRoundRect(0, 0, size, size, size / 2);
                    }
                });
                fabBlue.setClipToOutline(true);
                fabBlue.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        imageCardView.setVisibility(View.GONE);
                        fabBlue.setVisibility(View.GONE);

                        ImageView smallImage = (ImageView) rootView.findViewById(R.id.image);
                        smallImage.setImageDrawable(getResources().getDrawable(rid));

                        txtCardView.setAlpha(0);
                        imgCardView.setAlpha(0);
                        txtCardView.setVisibility(View.VISIBLE);
                        imgCardView.setVisibility(View.VISIBLE);

                        AnimatorSet animSet = new AnimatorSet();
                        ObjectAnimator animTxtY = ObjectAnimator.ofFloat(txtCardView, "translationY", 600, 0);
                        animTxtY.setDuration(1500);
                        animTxtY.setInterpolator(new AccelerateDecelerateInterpolator());

                        ObjectAnimator animTxtAlpha = ObjectAnimator.ofFloat(txtCardView, "alpha", 0, 1);
                        animTxtAlpha.setDuration(500);
                        ObjectAnimator animImgY = ObjectAnimator.ofFloat(imgCardView, "translationY", 1200, 0);
                        animImgY.setDuration(2000);
                        animImgY.setInterpolator(new BounceInterpolator());

                        ObjectAnimator animImgAlpha = ObjectAnimator.ofFloat(imgCardView, "alpha", 0, 1);
                        animImgAlpha.setDuration(500);

                        //animSet.setInterpolator(new BounceInterpolator());
                        animSet.playTogether(animTxtAlpha, animTxtY, animImgAlpha, animImgY);

                        animSet.start();

                    }
                });
            }

            ViewCompat.setTransitionName(image, "photo_hero");
            image.setImageDrawable(getResources().getDrawable(rid));

            return rootView;
        }
    }

    static ImageView[] logoArr = new ImageView[4];


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    static void startOneLogoAnimation(ImageView animLogo, int delay, int duration, final ImageView fadeOutLogo) {

        animLogo.animate()
                .alpha(1f)
                .setStartDelay(delay)
                .setDuration(duration)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        if (fadeOutLogo != null) {
                            fadeOutLogo.setAlpha(0f);
                        }
                    }
                });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAfterTransition();
    }
}
