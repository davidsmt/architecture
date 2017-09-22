package arch.carlos.pokecards.pokeApp.api.intermediate;

import android.arch.lifecycle.LiveData;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import arch.carlos.pokecards.baseArch.api.ApiResponse;
import arch.carlos.pokecards.pokeApp.vo.PokeCard;

/**
 * Created by mobgen on 9/21/17.
 */

public class PokeCardListResponse {
    @SerializedName("cards")
    List<PokeCard> cards;

    public PokeCardListResponse(List<PokeCard> cards) {
        this.cards = cards;
    }

    public List<PokeCard> getCards() {
        return cards;
    }

    public void setCards(List<PokeCard> cards) {
        this.cards = cards;
    }
}
