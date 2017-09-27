package arch.carlos.pokecards.baseArch;

import android.arch.lifecycle.LifecycleActivity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import javax.inject.Inject;

import arch.carlos.pokecards.baseArch.dataInjection.Injectable;
import arch.carlos.pokecards.baseArch.viewmodel.ViewModelFactory;

/**
 * Created by mobgen on 9/20/17.
 */

public abstract class ArchActivity extends LifecycleActivity implements Injectable {

    @Inject
    protected ViewModelFactory viewModelFactory;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initActivity();
        initView();
        initViewModel();
    }

    abstract protected void initActivity();

    abstract protected void initView();

    abstract protected void initViewModel();
}
