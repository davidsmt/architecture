package com.davidsmt.architecture.repository.pokecard.datasources.api.models;

import com.davidsmt.architecture.domain.BaseMapperAdapter;
import com.davidsmt.architecture.domain.model.Weakness;
import com.google.gson.annotations.SerializedName;

/**
 * Created by d.san.martin.torres on 6/7/17.
 */

public class WeaknessApi {

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

    static class Mapper extends BaseMapperAdapter<WeaknessApi,Weakness> {

        @Override
        public Weakness map(WeaknessApi weaknessApi) {
            return new Weakness(
                    weaknessApi.getType(),
                    weaknessApi.getValue()
            );
        }

    }
}
