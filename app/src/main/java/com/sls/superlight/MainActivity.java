package com.sls.superlight;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;
import com.sls.superlight.superlightstack.AnimationHandler;
import com.sls.superlight.slates.Listener;
import com.sls.superlight.superlightstack.Transitioner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity {

    @BindView(R.id.view_container)
    FrameLayout viewContainer;


    public Transitioner transitioner;
    private List<Listener> listeners = new ArrayList<>();

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        //Create a new instance of Transitioner and provide the base view and view container;
        transitioner = new Transitioner(this, viewContainer, R.layout.view_one, R.id.ViewOne);
        transitioner.setup(bundle);
    }

    public void addListener(Listener listener) {
        listeners.add(listener);
    }

    @Override
    protected void onSaveInstanceState(Bundle bundle) {
        //pass the bundle to save the transitioner state
        Bundle state = transitioner.saveState(bundle);
        super.onSaveInstanceState(state);
    }

    @Override
    public void onBackPressed() {
        if(transitioner.getSize() > 1) {
            transitioner.goBack(AnimationHandler.TransitionTypes.CROSSFADE);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        for(Listener listener : listeners){
            listener.resultCallback(requestCode, resultCode, data);
        }

    }


}