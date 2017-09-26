package arch.carlos.pokecards.pokeApp.vo;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.TypeConverters;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import arch.carlos.pokecards.baseArch.cache.DomainCache;
import arch.carlos.pokecards.baseArch.db.RoomTypeConverters;
import arch.carlos.pokecards.pokeApp.vo.components.Ability;
import arch.carlos.pokecards.pokeApp.vo.components.Attack;
import arch.carlos.pokecards.pokeApp.vo.components.Resistance;
import arch.carlos.pokecards.pokeApp.vo.components.Weakness;

/**
 * Created by d.san.martin.torres on 6/7/17.
 */

@Entity(primaryKeys = "id")
@TypeConverters({RoomTypeConverters.class})
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
    private Ability ability;

    @SerializedName("hp")
    private String hp;

    @SerializedName("retreatCost")
    private ArrayList<String> retreatCost;

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
    private ArrayList<String> types;


    public PokeCard(String id, String name, String nationalPokedexNumber, String imageUrl, String subtype, String superType, Ability ability, String hp, ArrayList<String> retreatCost, String number, String artist, String rarity, String series, String set, String setCode, ArrayList<String> types) {
        this.id = id;
        this.name = name;
        this.nationalPokedexNumber = nationalPokedexNumber;
        this.imageUrl = imageUrl;
        this.subtype = subtype;
        this.superType = superType;
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

    public String getSuperType() {
        return superType;
    }

    public Ability getAbility() {
        return ability;
    }

    public ArrayList<String> getRetreatCost() {
        return retreatCost;
    }

    public ArrayList<String> getTypes() {
        return types;
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
        String result = "";
        if(getTypes()!=null){
            for (String type: getTypes()
                    ) {
                result = result + " " +type;
            }
        }

        return result;
    }


}
