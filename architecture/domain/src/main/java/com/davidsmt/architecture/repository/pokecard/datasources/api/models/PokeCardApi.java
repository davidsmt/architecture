package com.davidsmt.architecture.repository.pokecard.datasources.api.models;

import com.davidsmt.architecture.domain.BaseMapperAdapter;
import com.davidsmt.architecture.domain.model.PokeCard;
import com.davidsmt.architecture.domain.BaseMapper;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by d.san.martin.torres on 6/7/17.
 */

public class PokeCardApi {

    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("nationalPokedexNumber")
    private String nationalPokedexNumber;

    @SerializedName("imageUrl")
    private String imageUrl;

    @SerializedName("subtype")
    private String subtype;

    @SerializedName("supertype")
    private String superType;

    @SerializedName("ability")
    private AbilityApi ability;

    @SerializedName("hp")
    private String hp;

    @SerializedName("retreatCost")
    private List<String> retreatCost;

    @SerializedName("number")
    private String number;

    @SerializedName("artist")
    private String artist;

    @SerializedName("rarity")
    private String rarity;

    @SerializedName("series")
    private String series;

    @SerializedName("set")
    private String set;

    @SerializedName("setCode")
    private String setCode;

    @SerializedName("types")
    private List<String> types;

    @SerializedName("attacks")
    private List<AttackApi> attacks;

    @SerializedName("weaknesses")
    private List<WeaknessApi> weaknesses;

    @SerializedName("resistances")
    private List<ResistanceApi> resistances;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getNationalPokedexNumber() {
        return nationalPokedexNumber;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getSubtype() {
        return subtype;
    }

    public String getSuperType() {
        return superType;
    }

    public AbilityApi getAbility() {
        return ability;
    }

    public String getHp() {
        return hp;
    }

    public List<String> getRetreatCost() {
        return retreatCost;
    }

    public String getNumber() {
        return number;
    }

    public String getArtist() {
        return artist;
    }

    public String getRarity() {
        return rarity;
    }

    public String getSeries() {
        return series;
    }

    public String getSet() {
        return set;
    }

    public String getSetCode() {
        return setCode;
    }

    public List<String> getTypes() {
        return types;
    }

    public List<AttackApi> getAttacks() {
        return attacks;
    }

    public List<WeaknessApi> getWeaknesses() {
        return weaknesses;
    }

    public List<ResistanceApi> getResistances() {
        return resistances;
    }

    public static class Mapper extends BaseMapperAdapter<PokeCardApi,PokeCard> {

        @Override
        public PokeCard map(PokeCardApi pokeCardApi) {
            return new PokeCard(
                    pokeCardApi.getId(),
                    pokeCardApi.getName(),
                    pokeCardApi.getNationalPokedexNumber(),
                    pokeCardApi.getImageUrl(),
                    pokeCardApi.getSubtype(),
                    pokeCardApi.getSuperType(),
                    pokeCardApi.getAbility() != null ? new AbilityApi.Mapper().map(pokeCardApi.getAbility()) : null,
                    pokeCardApi.getHp(),
                    pokeCardApi.getRetreatCost(),
                    pokeCardApi.getNumber(),
                    pokeCardApi.getArtist(),
                    pokeCardApi.getRarity(),
                    pokeCardApi.getSeries(),
                    pokeCardApi.getSet(),
                    pokeCardApi.getSetCode(),
                    pokeCardApi.getTypes(),
                    pokeCardApi.getAttacks() != null ? new AttackApi.Mapper().map(pokeCardApi.getAttacks()) : null,
                    pokeCardApi.getWeaknesses() != null ? new WeaknessApi.Mapper().map(pokeCardApi.getWeaknesses()) : null,
                    pokeCardApi.getResistances() != null ? new ResistanceApi.Mapper().map(pokeCardApi.getResistances()) : null
            );
        }

    }

}
