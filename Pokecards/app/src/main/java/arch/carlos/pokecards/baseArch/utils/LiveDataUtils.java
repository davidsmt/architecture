package arch.carlos.pokecards.baseArch.utils;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import java.util.List;

import arch.carlos.pokecards.baseArch.api.ApiResponse;
import arch.carlos.pokecards.pokeApp.api.intermediate.PokeCardListResponse;
import arch.carlos.pokecards.pokeApp.vo.PokeCard;

/**
 * Created by mobgen on 9/21/17.
 */

public class LiveDataUtils {

    public static LiveData<List<PokeCard>> ListToPokeCards(LiveData<ApiResponse<PokeCardListResponse>> responseLiveData){
        MutableLiveData<List<PokeCard>> result = new MutableLiveData<>();
        PokeCardListResponse response = responseLiveData.getValue().body;
        //result.setValue(response.getCards());
        return null;
    }
}
