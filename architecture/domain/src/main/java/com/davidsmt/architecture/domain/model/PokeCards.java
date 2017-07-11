package com.davidsmt.architecture.domain.model;

import java.util.List;

/**
 * Created by d.san.martin.torres on 6/12/17.
 */

public class PokeCards {

    private List<PokeCard> cards;

    public PokeCards(List<PokeCard> cards) {
        this.cards = cards;
    }

    public List<PokeCard> getCards() {
        return cards;
    }

}
