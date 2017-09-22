package arch.carlos.pokecards.baseArch.di;

import arch.carlos.pokecards.pokeApp.ui.main.MainActivity;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by mobgen on 9/19/17.
 */

@Module
public abstract class ActivityBuildersModule {

//    @ContributesAndroidInjector
//    abstract ExampleActivity contributeExampleActivity();

    @ContributesAndroidInjector
    abstract MainActivity contributeMainActivity();

}
