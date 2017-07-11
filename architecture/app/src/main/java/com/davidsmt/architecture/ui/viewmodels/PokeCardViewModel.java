package com.davidsmt.architecture.ui.viewmodels;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.annotation.StringDef;

import com.davidsmt.architecture.BR;
import com.davidsmt.architecture.domain.BaseMapper;
import com.davidsmt.architecture.domain.BaseMapperAdapter;
import com.davidsmt.architecture.domain.model.PokeCard;

import java.lang.annotation.Retention;
import java.util.ArrayList;
import java.util.List;

import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * Created by d.san.martin.torres on 6/14/17.
 */

public class PokeCardViewModel extends BaseObservable {

    @Retention(SOURCE)
    @StringDef({
            POKEMON,
            TRAINER,
            ENERGY
    })
    public @interface SuperType {
    }
    static final String POKEMON = "Pokémon";
    static final String TRAINER = "Trainer";
    static final String ENERGY = "Energy";

    private String imageUrl;
    private String name;
    private List<String> types;
    private String cardId;
    private @SuperType String superType;

    public PokeCardViewModel(String imageUrl, String name, List<String> types, String cardId, String superType) {
        this.imageUrl = imageUrl;
        this.name = name;
        this.types = types;
        this.cardId = cardId;
        this.superType = superType;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.viewModel);
    }

    public void setImageUrl(String url) {
        this.imageUrl = url;
        notifyPropertyChanged(BR.viewModel);
    }

    public void setTypes(List<String> types) {
        this.types = types;
        notifyPropertyChanged(BR.viewModel);
    }

    public void setSuperType(@SuperType String superType) {
        this.superType = superType;
        notifyPropertyChanged(BR.viewModel);
    }

    @Bindable
    public String getType() {

        if (superType.equalsIgnoreCase(POKEMON)) {

            if (types == null || types.size() == 0) {
                return POKEMON;
            }
            StringBuilder typesBuilder = new StringBuilder(POKEMON);
            typesBuilder.append(": ");
            for (String type : types) {
                typesBuilder.append(type);
                typesBuilder.append(" | ");
            }
            return typesBuilder.substring(0, typesBuilder.length() - 3);
        } else {
            return superType;
        }
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    @Bindable
    public String getImageUrl() {
        return imageUrl;
    }

    @Bindable
    public String getName() {
        return name;
    }


    public static class Mapper extends BaseMapperAdapter<PokeCard,PokeCardViewModel> {

        @Override
        public PokeCardViewModel map(PokeCard pokeCard) {
            return new PokeCardViewModel(
                    pokeCard.getImageUrl(),
                    pokeCard.getName(),
                    pokeCard.getTypes(),
                    pokeCard.getId(),
                    pokeCard.getSupertype()
            );
        }

    }

    public static class MapperList implements BaseMapper<List<PokeCard>,List<PokeCardViewModel>> {

        @Override
        public List<PokeCardViewModel> map(List<PokeCard> pokeCardList) {
            PokeCardViewModel.Mapper pokeCardMapper = new Mapper();
            List<PokeCardViewModel> pokeCardViewModelList = new ArrayList<>();
            if (pokeCardList != null) {
                for (PokeCard pokeCard : pokeCardList) {
                    pokeCardViewModelList.add(pokeCardMapper.map(pokeCard));
                }
            }
            return pokeCardViewModelList;
        }
    }
}
