package com.sls.superlight.slates.viewthree;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Parcelable;
import android.support.design.widget.Snackbar;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.widget.Button;
import com.sls.superlight.R;
import com.jakewharton.rxbinding.view.RxView;
import com.sls.superlight.superlightstack.BaseView;
import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class ViewThree extends BaseView {

    private final CompositeSubscription compositeSubscription = new CompositeSubscription();
    private Context context;

    @BindView(R.id.show_message_button)
    Button showMessageButton;

    SparseArray<Parcelable> container;

    public ViewThree(Context context) {
        super(context);
        this.context = context;
    }

    public ViewThree(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public ViewThree(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    @TargetApi(21)
    public ViewThree(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context = context;
    }

    private Observable<Void> showMessageButton() {
        return RxView.clicks(showMessageButton);
    }

    private Subscription subscribeShowMessageButton() {
        return showMessageButton()
                .subscribe(a ->  showMessage(super.getBundle().getString("VIEW_TWO")));
    }

    private void showMessage(String message) {
        Snackbar.make(findViewById(R.id.ViewThree), message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    protected void onFinishInflate() {
        ButterKnife.bind(this);
        compositeSubscription.add(subscribeShowMessageButton());
        this.setId(R.id.ViewThree);
        super.onFinishInflate();
    }

    @Override
    protected void onDetachedFromWindow() {
        compositeSubscription.clear();
        super.onDetachedFromWindow();
    }

}
