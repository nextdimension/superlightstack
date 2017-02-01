package com.sls.superlight.slates.viewone;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.LayoutRes;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.widget.Button;
import android.widget.EditText;
import com.sls.superlight.MainActivity;
import com.sls.superlight.R;
import com.jakewharton.rxbinding.view.RxView;
import com.sls.superlight.superlightstack.AnimationHandler;
import com.sls.superlight.superlightstack.BaseView;
import com.sls.superlight.slates.Listener;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class ViewOne extends BaseView implements Listener {

    private final CompositeSubscription compositeSubscription = new CompositeSubscription();
    private Context context;

    @BindView(R.id.go_to_button)
    Button goToButton;

    @BindView(R.id.arg_text)
    EditText arg;

    private int PICK_IMAGE_REQUEST = 1;

    SparseArray<Parcelable> container;

    public ViewOne(Context context) {
        super(context);
        this.context = context;
    }

    public ViewOne(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public ViewOne(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    @TargetApi(21)
    public ViewOne(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context = context;
    }

    private Observable<Void> goToButton() {
        return RxView.clicks(goToButton);
    }

    private Subscription subscribeGoToButton() {
        return goToButton()
                .subscribe(a -> switchView(R.layout.view_two, R.id.ViewTwo));
    }

    public void switchView(@LayoutRes int layoutResID, int id) {
        //launchGallery();
        ((MainActivity)context).transitioner.goTo(layoutResID, id, getData(), AnimationHandler.TransitionTypes.CROSSFADE, container);
    }

    public void launchGallery() {
        Intent intent = new Intent();
        // Show only images, no videos or anything else
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.putExtra("VIEW_CALLBACK", R.layout.view_one);
        ((MainActivity) context).startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }


    @Override
    protected void onFinishInflate() {
        ButterKnife.bind(this);
        this.setId(R.id.ViewOne);
        setSaveEnabled(true);
        super.setid(R.id.ViewOne);
        super.onFinishInflate();
    }

    private Bundle getData() {
        Bundle bundle = new Bundle();
        bundle.putString("VIEW_ONE", arg.getText().toString());
        return bundle;
    }

    @Override
    protected void onAttachedToWindow() {
        if(super.getBundle() != null) {
            arg.setText(super.getBundle().getString("VIEW_ONE"));
        }
        compositeSubscription.add(subscribeGoToButton());
       // ((MainActivity)context).addListener(this);
        super.onAttachedToWindow();
    }

    @Override
    protected void onDetachedFromWindow() {
        compositeSubscription.clear();
        super.onDetachedFromWindow();
    }


    @Override
    public void resultCallback(int requestCode, int resultCode, Intent data) {
            Log.d("view_one", "callback");

    }

    @Override
    public void saveHierarchyState(SparseArray<Parcelable> container) {
        Log.d("View_one", "saveHierarchyState");
        super.saveHierarchyState(container);
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Log.d("View_one", "onSaveInstanceState");
        return super.onSaveInstanceState();
    }

    @Override
    protected void dispatchSaveInstanceState(SparseArray<Parcelable> container) {
        Log.d("View_one", "dispatchSaveInstanceState");
        super.dispatchSaveInstanceState(container);
    }


}
