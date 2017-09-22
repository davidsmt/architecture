package arch.carlos.pokecards.pokeApp.vo;

import android.arch.persistence.room.Entity;

import com.google.gson.annotations.SerializedName;

import arch.carlos.pokecards.baseArch.cache.DomainCache;

/**
 * Created by d.san.martin.torres on 6/7/17.
 */

@Entity(primaryKeys = "id")
public class PokeCard implements DomainCache.Cacheable {

    public static final String DOMAIN_KEY = "PokeCard_domain_key";

    @Override
    public String getDomainKey() {
        return DOMAIN_KEY;
    }

    @Override
    public String getPrimaryKey() {
        return getId();
    }

    @SerializedName("id;")
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
    private String supertype;
    @SerializedName("hp")
    private String hp;
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

    public PokeCard(String id, String name, String nationalPokedexNumber, String imageUrl, String subtype, String supertype, String hp, String number, String artist, String rarity, String series, String set, String setCode) {
        this.id = id;
        this.name = name;
        this.nationalPokedexNumber = nationalPokedexNumber;
        this.imageUrl = imageUrl;
        this.subtype = subtype;
        this.supertype = supertype;
        this.hp = hp;
        this.number = number;
        this.artist = artist;
        this.rarity = rarity;
        this.series = series;
        this.set = set;
        this.setCode = setCode;
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

    public String getHp() {
        return hp;
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


    public String getType() {
        return "POKEMON";
    }

}
