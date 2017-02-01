package com.sls.superlight.superlightstack;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.widget.FrameLayout;

public class BaseView extends FrameLayout {

    public BaseView(Context context) {
        super(context);
    }

    public BaseView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(21)
    public BaseView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public String getArg() {
        return arg;
    }

    public void setArg(String arg) {
        this.arg = arg;
    }

    private String arg;

    public Bundle getBundle() {
        return bundle;
    }

    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }

    private Bundle bundle;
    private SparseArray<Parcelable> container;


    @Override
    protected Parcelable onSaveInstanceState() {
        Log.d("Base", "onSaveInstanceState");
        return super.onSaveInstanceState();
    }

    @Override
    public void saveHierarchyState(SparseArray<Parcelable> container) {
        Log.d("base", "saveHierarchyState");
        super.saveHierarchyState(container);
    }

    @Override
    protected void dispatchSaveInstanceState(SparseArray<Parcelable> container) {
        Log.d("Base", "dispatchSaveInstanceState");
        super.dispatchSaveInstanceState(container);
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        Log.d("Base", "onRestoreInstanceState");
        super.onRestoreInstanceState(state);
    }

    public void setid(int id) {
        setId(id);
        setSaveEnabled(true);
    }


}
