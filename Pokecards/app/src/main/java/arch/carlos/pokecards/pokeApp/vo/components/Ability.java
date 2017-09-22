package arch.carlos.pokecards.pokeApp.vo.components;

import android.arch.persistence.room.Entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by d.san.martin.torres on 6/8/17.
 */

@Entity(primaryKeys = "name")
public class Ability {

    @SerializedName("name")
    private String name;
    @SerializedName("text")
    private String text;

    public Ability(String name, String text) {
        this.name = name;
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
