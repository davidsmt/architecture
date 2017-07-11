package com.davidsmt.architecture.repository.pokecard.datasources.api.models;

import com.davidsmt.architecture.domain.BaseMapperAdapter;
import com.davidsmt.architecture.domain.model.Resistance;
import com.google.gson.annotations.SerializedName;

/**
 * Created by d.san.martin.torres on 6/7/17.
 */

public class ResistanceApi {

    @SerializedName("type")
    private String type;

    @SerializedName("value")
    private String value;

    public String getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    static class Mapper extends BaseMapperAdapter<ResistanceApi,Resistance> {

        @Override
        public Resistance map(ResistanceApi resistanceApi) {
            return new Resistance(
                    resistanceApi.getType(),
                    resistanceApi.getValue()
            );
        }

    }
}
