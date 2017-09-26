package arch.carlos.pokecards.pokeApp.db.dao;

/**
 * Created by mobgen on 9/15/17.
 */

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;


import java.util.List;

import arch.carlos.pokecards.pokeApp.vo.PokeCard;

/**
 * Interface for database access on Repo related operations.
 */
@Dao
public interface PokeCardDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(PokeCard card);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<PokeCard> cards);

    @Query("SELECT * FROM PokeCard "
            + "ORDER BY nationalPokedexNumber DESC")
    LiveData<List<PokeCard>> getCards();

    @Query("SELECT * FROM PokeCard WHERE id = :pId")
    LiveData<PokeCard> getCard(String pId);

    @Query("DELETE FROM PokeCard")
    void deleteAll();

    @Query("DELETE FROM PokeCard WHERE id = :pId")
    void deleteCard(String pId);


}
