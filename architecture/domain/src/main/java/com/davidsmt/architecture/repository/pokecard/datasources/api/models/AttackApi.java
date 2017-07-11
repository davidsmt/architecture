package com.davidsmt.architecture.repository.pokecard.datasources.api.models;

import com.davidsmt.architecture.domain.BaseMapperAdapter;
import com.davidsmt.architecture.domain.model.Attack;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by d.san.martin.torres on 6/7/17.
 */

public class AttackApi {

    @SerializedName("cost")
    private List<String> cost;

    @SerializedName("name")
    private String name;

    @SerializedName("text")
    private String text;

    @SerializedName("damage")
    private String damage;

    @SerializedName("convertedEnergyCost")
    private int convertedEnergyCost;

    public List<String> getCost() {
        return cost;
    }

    public String getName() {
        return name;
    }

    public String getText() {
        return text;
    }

    public String getDamage() {
        return damage;
    }

    public int getConvertedEnergyCost() {
        return convertedEnergyCost;
    }

    static class Mapper extends BaseMapperAdapter<AttackApi,Attack> {

        @Override
        public Attack map(AttackApi attackApi) {
            return new Attack(
                    attackApi.getCost(),
                    attackApi.getName(),
                    attackApi.getText(),
                    attackApi.getDamage(),
                    attackApi.getConvertedEnergyCost()
            );
        }

    }
}
