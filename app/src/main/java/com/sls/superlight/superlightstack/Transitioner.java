package com.sls.superlight.superlightstack;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;

import static android.R.attr.data;

public class Transitioner {

    private Context context;
    private ViewGroup root;
    private ArrayList<Slate> backStack = new ArrayList<>();
    private int baseLayoutID;
    private int baseId;

    public Transitioner(Context context, ViewGroup root, int baseLayoutID, int baseId) {
        this.context = context;
        this.root = root;
        this.baseLayoutID = baseLayoutID;
        this.baseId = baseId;
    }

    public void goTo(int layoutResID, int id, Bundle bundle, AnimationHandler.TransitionTypes type, SparseArray<Parcelable> container) {
        Slate slate = new Slate(layoutResID, bundle, id, container);
        saveCurrentState(bundle, container);
        this.transitionForward(slate, type);
    }
    public void goBack(AnimationHandler.TransitionTypes type) {
        transitionBackward(type);
    }
    public void replace(AnimationHandler.TransitionTypes type) {
        transitionReplace(type);
    }

    private void transitionForward(Slate slate, AnimationHandler.TransitionTypes type) {

        if (type == AnimationHandler.TransitionTypes.NONE) {
            backStack.add(slate);
            root.removeAllViews();
            BaseView nView = (BaseView) LayoutInflater.from(this.context).inflate(backStack.get(backStack.size() - 1).getLayoutID(), root, false);
            nView.setBundle(backStack.get(backStack.size() - 1).getBundle());
            root.addView(nView);
        } else {
            AnimationHandler animationHandler = new AnimationHandler();
            BaseView from = (BaseView) root.findViewById(backStack.get(backStack.size() - 1).getId());
            backStack.add(slate);
            BaseView to = (BaseView) LayoutInflater.from(this.context).inflate(backStack.get(backStack.size() - 1).getLayoutID(), root, false);
            to.setBundle(backStack.get(backStack.size() - 1).getBundle());
            animationHandler.animate(type, from, to, root, 500);
        }
    }
    private void transitionBackward(AnimationHandler.TransitionTypes type) {

        if (type == AnimationHandler.TransitionTypes.NONE) {
            backStack.remove(backStack.size() - 1);
            root.removeAllViews();
            BaseView nView = (BaseView) LayoutInflater.from(this.context).inflate(backStack.get(backStack.size() - 1).getLayoutID(), root, false);
            nView.setBundle(backStack.get(backStack.size() - 1).getBundle());
            root.addView(nView);
        } else {
            AnimationHandler animationHandler = new AnimationHandler();
            BaseView from = (BaseView) root.findViewById(backStack.get(backStack.size() - 1).getId());
            backStack.remove(backStack.size() - 1);
            BaseView to = (BaseView) LayoutInflater.from(this.context).inflate(backStack.get(backStack.size() - 1).getLayoutID(), root, false);
            to.setBundle(backStack.get(backStack.size() - 1).getBundle());
            animationHandler.animate(type, from, to, root, 500);
        }
    }
    private void transitionReplace(AnimationHandler.TransitionTypes type) {

        if (type == AnimationHandler.TransitionTypes.NONE) {
            root.removeAllViews();
            BaseView nView = (BaseView) LayoutInflater.from(this.context).inflate(backStack.get(backStack.size() - 1).getLayoutID(), root, false);
            nView.setBundle(backStack.get(backStack.size() - 1).getBundle());
            root.addView(nView);
        } else {
            AnimationHandler animationHandler = new AnimationHandler();
            BaseView from = (BaseView) root.findViewById(backStack.get(backStack.size() - 1).getId());
            BaseView to = (BaseView) LayoutInflater.from(this.context).inflate(backStack.get(backStack.size() - 1).getLayoutID(), root, false);
            to.setBundle(backStack.get(backStack.size() - 1).getBundle());
            animationHandler.animate(type, from, to, root, 500);
        }
    }
    
    public Integer getSize() {
        return backStack.size();
    }

    public Bundle saveState(Bundle bundle) {
        bundle.putParcelableArrayList("TRANSITIONER", backStack);
        return bundle;
    }
    public void setup(Bundle bundle) {
        if(bundle != null) {
           backStack = bundle.getParcelableArrayList("TRANSITIONER");
            replace(AnimationHandler.TransitionTypes.NONE);
        } else {
            goTo(baseLayoutID, baseId, null, AnimationHandler.TransitionTypes.NONE, null);
        }

    }

    private void saveCurrentState(Bundle bundle, SparseArray<Parcelable> container) {
        if(backStack.size() >= 1) {
            backStack.get(backStack.size() - 1).setBundle(bundle);
            backStack.get(backStack.size() - 1).setContainer(container);
        }
    }

}
