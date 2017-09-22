package arch.carlos.pokecards.baseArch;

import android.arch.lifecycle.LifecycleActivity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import javax.inject.Inject;

import arch.carlos.pokecards.baseArch.di.Injectable;
import arch.carlos.pokecards.baseArch.viewmodel.ViewModelFactory;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

/**
 * Created by mobgen on 9/20/17.
 */

public abstract class ArchActivityWithFragments extends ArchActivity implements HasSupportFragmentInjector {

    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;

    @Override
    public DispatchingAndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }
}
