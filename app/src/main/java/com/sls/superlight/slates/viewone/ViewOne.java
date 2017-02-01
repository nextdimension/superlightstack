package com.sls.superlight.slates.viewone;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.annotation.LayoutRes;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import com.sls.superlight.MainActivity;
import com.sls.superlight.R;
import com.jakewharton.rxbinding.view.RxView;
import com.sls.superlight.superlightstack.AnimationHandler;
import com.sls.superlight.superlightstack.BaseView;
import com.sls.superlight.slates.Listener;
import java.io.IOException;
import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

import static android.app.Activity.RESULT_OK;


public class ViewOne extends BaseView implements Listener {

    private final CompositeSubscription compositeSubscription = new CompositeSubscription();
    private Context context;

    @BindView(R.id.go_to_button)
    Button goToButton;

    @BindView(R.id.arg_text)
    EditText arg;

   @BindView(R.id.image_test)
   ImageView imageView;

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

    public Observable<Void> imageClicked() {
        return RxView.clicks(imageView);
    }

    private Subscription subscribeImageClick() {
        return imageClicked()
                .subscribe(a -> launchGallery());
    }

    private Observable<Void> goToButton() {
        return RxView.clicks(goToButton);
    }

    private Subscription subscribeGoToButton() {
        return goToButton()
                .subscribe(a -> switchView(R.layout.view_two, R.id.ViewTwo));
    }

    public void switchView(@LayoutRes int layoutResID, int id) {
        ((MainActivity)context).transitioner.goTo(layoutResID, id, getData(), AnimationHandler.TransitionTypes.CROSSFADE);
    }

    public void launchGallery() {

        ((MainActivity)context).addListener(this);
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        ((MainActivity) context).startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }


    @Override
    protected void onFinishInflate() {
        ButterKnife.bind(this);
        this.setId(R.id.ViewOne);
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
        compositeSubscription.add(subscribeImageClick());
        super.onAttachedToWindow();
    }

    @Override
    protected void onDetachedFromWindow() {
        compositeSubscription.clear();
        super.onDetachedFromWindow();
    }

    @Override
    public void resultCallback(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            try {
                Uri uri = null;
                if (data != null) {
                    uri = data.getData();
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);
                    this.imageView.setImageBitmap(bitmap);

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
