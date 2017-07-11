package com.davidsmt.architecture.repository.pokecard.datasources.api.models;

import com.davidsmt.architecture.domain.BaseMapper;
import com.davidsmt.architecture.domain.model.Ability;
import com.google.gson.annotations.SerializedName;

/**
 * Created by d.san.martin.torres on 6/7/17.
 */

public class AbilityApi {

    @SerializedName("name")
    private String name;

    @SerializedName("text")
    private String text;

    public String getName() {
        return name;
    }

    public String getText() {
        return text;
    }

    static class Mapper implements BaseMapper<AbilityApi,Ability> {

        @Override
        public Ability map(AbilityApi abilityApi) {
            return new Ability(
                    abilityApi.getName(),
                    abilityApi.getText()
            );
        }

    }
}
