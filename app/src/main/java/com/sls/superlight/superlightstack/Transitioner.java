package com.sls.superlight.superlightstack;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;

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

    public void goTo(int layoutResID, int id, Bundle bundle, AnimationHandler.TransitionTypes type) {
        Slate slate = new Slate(layoutResID, bundle, id);
        saveCurrentState(bundle);
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
            restoreState(nView);
            root.addView(nView);
        } else {
            AnimationHandler animationHandler = new AnimationHandler();
            BaseView from = (BaseView) root.findViewById(backStack.get(backStack.size() - 1).getId());
            backStack.remove(backStack.size() - 1);
            BaseView to = (BaseView) LayoutInflater.from(this.context).inflate(backStack.get(backStack.size() - 1).getLayoutID(), root, false);
            to.setBundle(backStack.get(backStack.size() - 1).getBundle());
            restoreState(to);
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
            goTo(baseLayoutID, baseId, null, AnimationHandler.TransitionTypes.NONE);
        }

    }

    private void saveCurrentState(Bundle bundle) {
        if(backStack.size() >= 1) {
            backStack.get(backStack.size() - 1).setBundle(bundle);
            BaseView view = (BaseView) root.findViewById(backStack.get(backStack.size() - 1).getId());
            persistState(view);

        }
    }

    public void persistState(View view) {
        if(view != null) {
            SparseArray<Parcelable> viewHierarchyState = new SparseArray<>();
            view.saveHierarchyState(viewHierarchyState);
            backStack.get(backStack.size() -1).setContainer(viewHierarchyState);
        }

    }

    public void restoreState(View view) {
         view.restoreHierarchyState(backStack.get(backStack.size() -1).getContainer());
    }

}
