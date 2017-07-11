package com.davidsmt.architecture.domain.model;

import java.util.List;

/**
 * Created by d.san.martin.torres on 6/7/17.
 */

public class PokeCard {

    private String id;
    private String name;
    private String nationalPokedexNumber;
    private String imageUrl;
    private String subtype;
    private String supertype;
    private Ability ability;
    private String hp;
    private List<String> retreatCost;
    private String number;
    private String artist;
    private String rarity;
    private String series;
    private String set;
    private String setCode;
    private List<String> types;
    private List<Attack> attacks;
    private List<Weakness> weaknesses;
    private List<Resistance> resistances;

    public PokeCard(String id, String name, String nationalPokedexNumber, String imageUrl, String subtype, String supertype, Ability ability, String hp, List<String> retreatCost, String number, String artist, String rarity, String series, String set, String setCode, List<String> types, List<Attack> attacks, List<Weakness> weaknesses, List<Resistance> resistances) {
        this.id = id;
        this.name = name;
        this.nationalPokedexNumber = nationalPokedexNumber;
        this.imageUrl = imageUrl;
        this.subtype = subtype;
        this.supertype = supertype;
        this.ability = ability;
        this.hp = hp;
        this.retreatCost = retreatCost;
        this.number = number;
        this.artist = artist;
        this.rarity = rarity;
        this.series = series;
        this.set = set;
        this.setCode = setCode;
        this.types = types;
        this.attacks = attacks;
        this.weaknesses = weaknesses;
        this.resistances = resistances;
    }

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

    public String getSupertype() {
        return supertype;
    }

    public Ability getAbility() {
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

    public List<Attack> getAttacks() {
        return attacks;
    }

    public List<Weakness> getWeaknesses() {
        return weaknesses;
    }

    public List<Resistance> getResistances() {
        return resistances;
    }
}
