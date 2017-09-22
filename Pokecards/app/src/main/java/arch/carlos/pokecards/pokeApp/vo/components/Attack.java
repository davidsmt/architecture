package arch.carlos.pokecards.pokeApp.vo.components;

import android.arch.persistence.room.Entity;

import java.util.List;

/**
 * Created by d.san.martin.torres on 6/8/17.
 */

@Entity(primaryKeys = "name")
public class Attack {
    private List<String> cost;
    private String name;
    private String text;
    private String damage;
    private int convertedEnergyCost;

    public Attack(List<String> cost, String name, String text, String damage, int convertedEnergyCost) {
        this.cost = cost;
        this.name = name;
        this.text = text;
        this.damage = damage;
        this.convertedEnergyCost = convertedEnergyCost;
    }
}
