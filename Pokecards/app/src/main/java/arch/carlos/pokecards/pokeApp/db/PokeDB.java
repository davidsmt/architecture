package arch.carlos.pokecards.pokeApp.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import arch.carlos.pokecards.pokeApp.db.dao.PokeCardDao;
import arch.carlos.pokecards.pokeApp.vo.PokeCard;

/**
 * Main database description.
 */
@Database(entities = {PokeCard.class}, version = 2)
public abstract class PokeDB extends RoomDatabase {
    abstract public PokeCardDao pokeCardDao();
}