package com.sls.superlight.superlightstack;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;
import android.view.ViewGroup;

public class AnimationHandler {

    public void animate(TransitionTypes type, View from, View to, ViewGroup container, int duration){

        switch (type) {
            case CROSSFADE:
                doCrossFade(from, to, container, duration);
                break;
            default:
                break;

        }
    }

    private static final void doCrossFade(View from, View to, ViewGroup container, int duration) {
        if(from != null) {
            from.animate()
            .alpha(0f)
            .setDuration(duration);

            to.setAlpha(0f);
            to.setVisibility(View.VISIBLE);
            container.addView(to);
        }

        if(to != null) {
            to.animate()
            .alpha(1f)
            .setDuration(duration)
            .setListener(new AnimatorListenerAdapter() {
                 @Override
                 public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    container.removeView(from);
                 }});
        }
    }

    public enum TransitionTypes {
        NONE, CROSSFADE
    }
}
