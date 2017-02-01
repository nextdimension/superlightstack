package com.sls.superlight.slates;

import android.content.Intent;

public interface Listener {
    void resultCallback(int requestCode, int resultCode, Intent data);
}
