package arch.carlos.pokecards.baseArch.di;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import arch.carlos.pokecards.baseArch.viewmodel.ViewModelFactory;
import arch.carlos.pokecards.pokeApp.ui.main.PokeCardViewModel;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
abstract class ViewModelModule {
    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);

    @Binds
    @IntoMap
    @ViewModelKey(PokeCardViewModel.class)
    abstract ViewModel bindPokeCardViewModel(PokeCardViewModel viewModel);

}
