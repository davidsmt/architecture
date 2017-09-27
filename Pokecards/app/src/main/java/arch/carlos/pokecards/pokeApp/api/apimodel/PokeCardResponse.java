package arch.carlos.pokecards.pokeApp.api.apimodel;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import arch.carlos.pokecards.pokeApp.vo.PokeCard;

/**
 * Created by mobgen on 9/21/17.
 *
 * Class that represents the html response for reading a card
 */

public class PokeCardResponse {
    @SerializedName("card")
    PokeCard card;

    public PokeCardResponse(PokeCard card) {
        this.card = card;
    }

    public PokeCard getCard() {
        return card;
    }

    public void setCard(PokeCard card) {
        this.card = card;
    }
}
