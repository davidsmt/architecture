package arch.carlos.pokecards.pokeApp.ui.main;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import arch.carlos.pokecards.R;
import arch.carlos.pokecards.baseArch.ArchActivity;
import arch.carlos.pokecards.baseArch.ArchApplication;
import arch.carlos.pokecards.baseArch.vo.Status;
import arch.carlos.pokecards.databinding.FragmentCardsBinding;
import arch.carlos.pokecards.pokeApp.ui.main.adapter.PokeCardListAdapter;

public class MainActivity extends ArchActivity {

    FragmentCardsBinding mBinding;

    @Override
    protected void initActivity() {

    }

    @Override
    protected void initView() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.fragment_cards);
        LinearLayoutManager llm = new LinearLayoutManager(ArchApplication.getContext());
        mBinding.recyclerView.setLayoutManager(llm);
    }

    @Override
    protected void initViewModel() {
        PokeCardViewModel viewModel = ViewModelProviders.of(this,viewModelFactory).get(PokeCardViewModel.class);
        viewModel.getList().observe(this, listResource -> {
            if(listResource != null ){
                if(listResource.status == Status.LOADING && listResource.data == null){
                    mBinding.progressBar.setVisibility(View.VISIBLE);
                }
                if(listResource.status== Status.SUCCESS || listResource.status== Status.LOADING && listResource.data != null){
                    mBinding.recyclerView.setAdapter(new PokeCardListAdapter(listResource.data, (id) -> goToItem(id)));
                }
            }


        });
    }

    private void goToItem(String id){

    }
}
