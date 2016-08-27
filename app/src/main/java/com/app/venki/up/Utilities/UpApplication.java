package com.app.venki.up.Utilities;

import android.app.Application;

import com.app.venki.up.Utilities.DaggerNetComponent;

/**
 * Created by samsiu on 8/17/16.
 */
public class UpApplication extends Application {

    private NetComponent netComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        netComponent = DaggerNetComponent.create();

    }

    public NetComponent getNetComponent(){
        return netComponent;
    }


}
