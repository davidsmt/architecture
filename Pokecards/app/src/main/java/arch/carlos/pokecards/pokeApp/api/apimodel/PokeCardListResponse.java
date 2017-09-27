package arch.carlos.pokecards.pokeApp.api.apimodel;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import arch.carlos.pokecards.pokeApp.vo.PokeCard;

/**
 * Created by mobgen on 9/21/17.
 *
 * Class that represents the html response for reading cards
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
