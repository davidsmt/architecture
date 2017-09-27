package arch.carlos.pokecards.baseArch;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import javax.inject.Inject;

import arch.carlos.pokecards.baseArch.dataInjection.AppInjector;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

/**
 * Created by mobgen on 9/20/17.
 */

public class ArchApplication extends Application implements HasActivityInjector {
    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

    private static ArchApplication sInstance;

    public static synchronized ArchApplication getInstance() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        AppInjector.init(this);
        sInstance = this;
    }

    public static Context getContext(){
        return sInstance.getApplicationContext();
    }


    @Override
    public AndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidInjector;
    }
}
