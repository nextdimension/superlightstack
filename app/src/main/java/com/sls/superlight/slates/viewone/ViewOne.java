package com.sls.superlight.slates.viewone;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.EditText;
import com.sls.superlight.MainActivity;
import com.sls.superlight.R;
import com.sls.superlight.superlightstack.AnimationHandler;
import com.jakewharton.rxbinding.view.RxView;
import com.sls.superlight.superlightstack.BaseView;
import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class ViewOne extends BaseView {

    private final CompositeSubscription compositeSubscription = new CompositeSubscription();
    private Context context;

    @BindView(R.id.go_to_button)
    Button goToButton;

    @BindView(R.id.arg_text)
    EditText arg;

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
        ((MainActivity)context).transitioner.goTo(layoutResID, id, getData(), AnimationHandler.TransitionTypes.CROSSFADE, this);
    }

    @Override
    protected void onFinishInflate() {
        ButterKnife.bind(this);
        compositeSubscription.add(subscribeGoToButton());
        this.setId(R.id.ViewOne);
        super.onFinishInflate();
    }

    private Bundle getData() {
        Bundle bundle = new Bundle();
        bundle.putString("VIEW_ONE", arg.getText().toString());
        return bundle;
    }
}
