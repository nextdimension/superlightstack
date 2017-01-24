package com.sls.superlight.slates.viewtwo;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.design.widget.Snackbar;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.EditText;
import com.sls.superlight.MainActivity;
import com.sls.superlight.R;
import com.jakewharton.rxbinding.view.RxView;
import com.sls.superlight.superlightstack.AnimationHandler;
import com.sls.superlight.superlightstack.BaseView;
import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class ViewTwo extends BaseView {

    private final CompositeSubscription compositeSubscription = new CompositeSubscription();
    private Context context;

    @BindView(R.id.go_to_button)
    Button goToButton;

    @BindView(R.id.show_message_button)
    Button showMessageButton;

    @BindView(R.id.arg_text)
    EditText arg;


    public ViewTwo(Context context) {
        super(context);
        this.context = context;
    }

    public ViewTwo(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public ViewTwo(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    @TargetApi(21)
    public ViewTwo(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context = context;
    }

    private Observable<Void> goToButton() {
        return RxView.clicks(goToButton);
    }
    private Observable<Void> showMessageButton() {
        return RxView.clicks(showMessageButton);
    }

    private Subscription subscribeGoToButton() {
        return goToButton()
                .subscribe(a ->  switchView(R.layout.view_three, R.id.ViewThree));
    }

    private Subscription subscribeShowMessageButton() {
        return showMessageButton()
                .subscribe(a ->  showMessage(super.getBundle().getString("VIEW_ONE")));
    }

    private void switchView(@LayoutRes int layoutResID, int id) {
        ((MainActivity)context).transitioner.goTo(layoutResID, id, getData(), AnimationHandler.TransitionTypes.CROSSFADE, this);
    }

    private void showMessage(String message) {
        Snackbar.make(findViewById(R.id.ViewTwo), message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    protected void onFinishInflate() {
        ButterKnife.bind(this);
        compositeSubscription.add(subscribeGoToButton());
        compositeSubscription.add(subscribeShowMessageButton());
        this.setId(R.id.ViewTwo);
        super.onFinishInflate();
    }

    private Bundle getData() {
        Bundle bundle = new Bundle();
        bundle.putString("VIEW_TWO", arg.getText().toString());
        return bundle;
    }

    @Override
    protected void onDetachedFromWindow() {
        compositeSubscription.clear();
        super.onDetachedFromWindow();
    }
}
